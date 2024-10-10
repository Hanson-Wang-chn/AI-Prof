from langchain_community.document_loaders import JSONLoader
from langchain_community.embeddings import HuggingFaceBgeEmbeddings
from langchain_community.vectorstores import Chroma
import torch
import json
from flask import Flask, request, jsonify
import requests
from summarizer import TextSummarizer


# 定义服务
app = Flask(__name__)


# ----------class RAG----------
class RAG:
    def __init__(self, database_path:str = "/home/whs/ai-prof/LLM/database_linear_algebra.json"):
        with open(database_path) as f:
            db = json.load(f)
        self.dict = {item['question']: item.get('output', '') for item in db}
        loader = JSONLoader(database_path, jq_schema='.[].question')
        documents = loader.load()

        # 使用本地模型进行word2vec处理
        local_model_path = "/home/whs/ai-prof/LLM/m3e-base"
        embedding = HuggingFaceBgeEmbeddings(
            model_name=local_model_path,
            model_kwargs={'device': 'cpu'},
            encode_kwargs={'normalize_embeddings': True},
            query_instruction="为文本生成向量表示用于文本检索"
        )
        self.db = Chroma.from_documents(documents, embedding)
        
        # 限制最大token数
        self.summarizer = TextSummarizer()
        self.max_tokens_reference = 1024
        self.max_tokens_history = 1024

    def get_rag_database_linear_algebra(self, user_question:str):
        """
        结合用户的问题和基于线性代数知识库RAG结果，生成优化的prompt。

        参数:
            user_question (str): 用户的问题。
        
        返回:
            str: RAG优化结果。
        """
        knowledge = self.db.similarity_search(user_question)
        reference = ''
        
        if not knowledge:
            reference += "没有找到相关信息。"
        
        else:
            reference += f"参考提问：\n{knowledge[0].page_content}\n\n"
            reference += f"参考回答：\n{self.dict[knowledge[0].page_content]}\n\n"
        
        reference = self.summarizer.summarize(reference, self.max_tokens_reference)
        
        prompt = (
            f"我将向你提问，你回答的规则是：若参考信息不为空且存在和我的问题相关的有效信息，"
            f"则必须优先使用参考信息中的内容回复，并结合你的知识稍作润色；"
            f"否则直接用你的知识回答。\n"
            f"如果存在数学计算请给出每一步的具体过程。"
            f"务必注意，如果有代码，请在代码首尾空行。"
            f"参考信息：\n{reference}\n"
            f"我的问题是：\n{user_question}\n"
        )
        
        return prompt
    
    def get_rag_tf_questions(self, knowledge_point:str, examples:str, number:int, history:str):
        """
        结合用户的问题，生成prompt，让大模型生成判断题。
        """
        knowledge = self.db.similarity_search(knowledge_point)
        reference = ''
        
        if not knowledge:
            reference += "没有找到相关信息。"
        
        else:
            reference += f"参考提问：\n{knowledge[0].page_content}\n\n"
            reference += f"参考回答：\n{self.dict[knowledge[0].page_content]}\n\n"
        
        reference = self.summarizer.summarize(reference, self.max_tokens_reference)
        
        if history == '':
            prompt = (
                f"请围绕“核心知识点”，结合“参考信息”，生成与“例子”类似的{number}道判断题。\n"
                f"核心知识点：\n{knowledge_point}\n"
                f"参考信息：\n{reference}\n"
                f"例子：\n{examples}\n"
            )
        
        else:
            history = self.summarizer.summarize(history, self.max_tokens_history)
            prompt = (
                f"请结合“参考信息”，评析“用户回复”，尤其是内容的正确性。\n"
                f"参考信息：\n{reference}\n"
                f"用户回复：\n{history}\n"
            )
        
        return prompt
    
    def get_rag_probing(self, knowledge_point:str, user_demand:str, history:str):
        """
        结合用户的问题，生成优化的prompt，让大模型追问用户。
        """
        knowledge = self.db.similarity_search(knowledge_point)
        reference = ''
        
        if not knowledge:
            reference += "没有找到相关信息。"
        
        else:
            reference += f"参考提问：\n{knowledge[0].page_content}\n\n"
            reference += f"参考回答：\n{self.dict[knowledge[0].page_content]}\n\n"
        
        reference = self.summarizer.summarize(reference, self.max_tokens_reference)
        
        if history != "":
            history = self.summarizer.summarize(history, self.max_tokens_history)
        
        prompt = (
            f"请结合“历史记录”和“参考信息”，不断追问学生与“核心知识点”相关的问题，帮助学生检测学习成果，解决“学生的需求”。\n"
            f"学生的需求：\n{user_demand}\n"
            f"核心知识点：\n{knowledge_point}\n"
            f"历史记录：\n{history}\n"
            f"参考信息：\n{reference}\n"
        )
        
        return prompt
    
    def get_rag_quiz(self, knowledge_point:str, examples:str, number:int):
        """
        结合用户的问题，生成优化的prompt，让大模型生成试卷。
        """
        knowledge = self.db.similarity_search(knowledge_point)
        reference = ''
        
        if not knowledge:
            reference += "没有找到相关信息。"
        
        else:
            reference += f"参考提问：\n{knowledge[0].page_content}\n\n"
            reference += f"参考回答：\n{self.dict[knowledge[0].page_content]}\n\n"
        
        reference = self.summarizer.summarize(reference, self.max_tokens_reference)
        
        prompt = (
            f"请围绕“核心知识点”，结合“参考信息”，出一份试卷，题型和“例题”相似。"
            f"若“例题”为空，则不限制题型。\n"
            f"试卷中需要包含{number}道题目。\n"
            f"核心知识点：\n{knowledge_point}\n"
            f"参考信息：\n{reference}\n"
            f"例题：\n{examples}\n"
        )
        
        return prompt
    
    def get_rag_query_library(self, knowledge_point:str, \
        description:str, answer:str, user_question:str, history:str):
        """
        结合用户的问题，生成优化的prompt，让大模型解答用户对题库中问题的疑惑。
        """
        knowledge = self.db.similarity_search(user_question)
        reference = ''
        
        if not (knowledge_point and description and answer and user_question):
            reference += "没有找到相关信息。"
        
        else:
            reference += f"参考提问：\n{knowledge[0].page_content}\n\n"
            reference += f"参考回答：\n{self.dict[knowledge[0].page_content]}\n\n"
        
        reference = self.summarizer.summarize(reference, self.max_tokens_reference)
        
        if history != "":
            history = self.summarizer.summarize(history, self.max_tokens_history)
            
        prompt = (
            f"核心知识点：\n{knowledge_point}\n"
            f"题目描述：\n{description}\n"
            f"题目答案：\n{answer}\n"
            f"上述内容是一道数学题，请参考“历史消息”，解答同学的疑问\n"
            f"历史消息：\n{history}\n"
            f"同学的疑问：\n{user_question}\n"
        )
        
        return prompt

    
# 在应用启动时创建 RAG 实例
rag_instance = RAG()


#----------function of api----------
def send_to_generate_service(prompt):
    """
    发送生成请求到外部服务并处理响应。
    
    参数：
    - prompt: 要发送的提示文本
    
    返回值：
    - 生成服务返回的 JSON 响应或错误信息
    """
    try:
        # 发送请求到生成服务
        response = requests.post("http://49.52.18.227:5000/generate", json={"prompt": prompt})
        response.raise_for_status()  # 检查请求是否成功
        response_data = response.json()
        
        # 释放显存
        torch.cuda.empty_cache()
        
        return jsonify(response_data)  # 返回生成的响应
    except requests.exceptions.RequestException as e:
        return jsonify({"error": str(e)}), 500  # 返回请求错误信息


# ----------Routes----------
@app.route('/api/model/rag/chat', methods=['POST'])
def rag_chat():
    """
    利用 RAG 知识库优化大模型的回复。
    
    接收用户输入的 JSON 格式的数据，返回经过 RAG 处理后大模型的回复（JSON 格式）。
    """
    data = request.get_json()
    user_question = data.get("user_question")
    if not user_question:
        return jsonify({"error": 'No user question provided'}), 400
    
    prompt = rag_instance.get_rag_database_linear_algebra(user_question)
    
    return send_to_generate_service(prompt)


@app.route('/api/model/rag/tf', methods=['POST'])
def rag_tf():
    data = request.get_json()
    knowledge_point = data.get("knowledge_point")
    examples = data.get("examples")
    number = data.get("number")
    history = data.get("history")

    if not knowledge_point:
        return jsonify({"error": 'No user question provided'}), 400
    
    prompt = rag_instance.get_rag_tf_questions(knowledge_point, examples, number, history)
    
    return send_to_generate_service(prompt)


@app.route('/api/model/rag/probe', methods=['POST'])
def rag_probe():
    data = request.get_json()
    knowledge_point = data.get("knowledge_point")
    user_demand = data.get("user_demand")
    history = data.get("history")
    
    if not user_demand:
        return jsonify({"error": 'No user question provided'}), 400
    
    prompt = rag_instance.get_rag_probing(knowledge_point, user_demand, history)
    
    return send_to_generate_service(prompt)


@app.route('/api/model/rag/quiz', methods=['POST'])
def rag_quiz():
    data = request.get_json()
    knowledge_point = data.get("knowledge_point")
    examples = data.get("examples")
    number = data.get("number")
    
    if not (knowledge_point and number):
        return jsonify({"error": 'No user question provided'}), 400
    
    prompt = rag_instance.get_rag_quiz(knowledge_point, examples, number)
    
    return send_to_generate_service(prompt)


@app.route('/api/model/rag/library', methods=['POST'])
def rag_library():
    data = request.get_json()
    knowledge_point = data.get("knowledge_point")
    description = data.get("description")
    answer = data.get("answer")
    user_question = data.get("user_question")
    history = data.get("history")
    
    if not user_question:
        return jsonify({"error": 'No user question provided'}), 400
    
    prompt = rag_instance.get_rag_query_library(knowledge_point, \
        description, answer, user_question, history)
    
    return send_to_generate_service(prompt)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5004, debug=False)
