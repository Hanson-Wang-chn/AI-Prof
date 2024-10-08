from langchain_community.document_loaders import JSONLoader
from langchain_community.embeddings import HuggingFaceBgeEmbeddings
from langchain_community.vectorstores import Chroma
import torch
import json
from flask import Flask, request, jsonify
import requests
import os

# 设置国内镜像
os.environ['HF_ENDPOINT'] = 'https://hf-mirror.com'


app = Flask(__name__)


class RAG:
    def __init__(self, database_path:str = "database_linear_algebra.json"):
        with open(database_path) as f:
            db = json.load(f)
        self.dict = {item['question']: item.get('output', '') for item in db}
        loader = JSONLoader(database_path, jq_schema='.[].question')
        documents = loader.load()

        # word2vec
        model_name = "moka-ai/m3e-base"
        model_kwargs = {'device': 'cpu'}
        encode_kwargs = {'normalize_embeddings': True}
        embedding = HuggingFaceBgeEmbeddings(
            model_name=model_name,
            model_kwargs=model_kwargs,
            encode_kwargs=encode_kwargs,
            query_instruction="为文本生成向量表示用于文本检索"
        )
        self.db = Chroma.from_documents(documents, embedding)


    def get_rag_database_linear_algebra(self, user_question:str):
        """
        结合用户的问题和基于线性代数知识库RAG结果，生成优化的prompt。

        参数:
            user_question (str): 用户的问题。
        
        返回:
            str: RAG优化结果。
        """
        context = self.db.similarity_search(user_question)
        reference = ''
        
        if not context:
            return "没有找到相关信息。"
        
        reference += f"参考提问：\n{context[0].page_content}\n\n"
        reference += f"参考回答：\n{self.dict[context[0].page_content]}\n\n"
        
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
    
    def get_rag_tf_questions(self, user_question:str):
        """
        结合用户的问题，生成prompt，让大模型生成判断题。

        参数:
            user_question (str): 用户的问题。
        
        返回:
            str: RAG优化结果。
        """
        context = self.db.similarity_search(user_question)
        reference = ''
        
        if not context:
            return "没有找到相关信息。"
        
        reference += f"参考提问：\n{context[0].page_content}\n\n"
        reference += f"参考回答：\n{self.dict[context[0].page_content]}\n\n"
        
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
    
    def get_rag_probing(self, user_question:str):
        """
        结合用户的问题，生成优化的prompt，让大模型追问用户。

        参数:
            user_question (str): 用户的问题。
        
        返回:
            str: RAG优化结果。
        """
        context = self.db.similarity_search(user_question)
        reference = ''
        
        if not context:
            return "没有找到相关信息。"
        
        reference += f"参考提问：\n{context[0].page_content}\n\n"
        reference += f"参考回答：\n{self.dict[context[0].page_content]}\n\n"
        
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
    
    def get_rag_quiz(self, user_question:str):
        """
        结合用户的问题，生成优化的prompt，让大模型生成试卷。

        参数:
            user_question (str): 用户的问题。
        
        返回:
            str: RAG优化结果。
        """
        context = self.db.similarity_search(user_question)
        reference = ''
        
        if not context:
            return "没有找到相关信息。"
        
        reference += f"参考提问：\n{context[0].page_content}\n\n"
        reference += f"参考回答：\n{self.dict[context[0].page_content]}\n\n"
        
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
    
    def get_rag_query_library(self, user_question:str):
        """
        结合用户的问题，生成优化的prompt，让大模型解答用户对题库中问题的疑惑。

        参数:
            user_question (str): 用户的问题。
        
        返回:
            str: RAG优化结果。
        """
        context = self.db.similarity_search(user_question)
        reference = ''
        
        if not context:
            return "没有找到相关信息。"
        
        reference += f"参考提问：\n{context[0].page_content}\n\n"
        reference += f"参考回答：\n{self.dict[context[0].page_content]}\n\n"
        
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
















    
# 在应用启动时创建 RAG 实例
rag_instance = RAG()


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
    
    # 发送请求到生成服务
    try:
        response = requests.post("http://49.52.18.227:5000/generate", json={"prompt": prompt})
        response.raise_for_status()  # 检查请求是否成功
        response_data = response.json()
        
        # 释放显存
        torch.cuda.empty_cache()
        
        return jsonify(response_data)  # 返回生成的响应
    except requests.exceptions.RequestException as e:
        return jsonify({"error": str(e)}), 500  # 返回请求错误信息


@app.route('/api/model/rag/tf', methods=['POST'])
def rag_tf():
    """
    利用 RAG 知识库优化大模型的回复。

    接收用户输入的 JSON 格式的数据，返回经过 RAG 处理后大模型的回复（JSON 格式）。
    """
    data = request.get_json()
    user_question = data.get("user_question")
    if not user_question:
        return jsonify({"error": 'No user question provided'}), 400
    
    prompt = rag_instance.get_rag_database_linear_algebra(user_question)
    
    # 发送请求到生成服务
    try:
        response = requests.post("http://49.52.18.227:5000/generate", json={"prompt": prompt})
        response.raise_for_status()  # 检查请求是否成功
        response_data = response.json()
        
        # 释放显存
        torch.cuda.empty_cache()
        
        return jsonify(response_data)  # 返回生成的响应
    except requests.exceptions.RequestException as e:
        return jsonify({"error": str(e)}), 500  # 返回请求错误信息


@app.route('/api/model/rag/probe', methods=['POST'])
def rag_probe():
    """
    利用 RAG 知识库优化大模型的回复。

    接收用户输入的 JSON 格式的数据，返回经过 RAG 处理后大模型的回复（JSON 格式）。
    """
    data = request.get_json()
    user_question = data.get("user_question")
    if not user_question:
        return jsonify({"error": 'No user question provided'}), 400
    
    prompt = rag_instance.get_rag_database_linear_algebra(user_question)
    
    # 发送请求到生成服务
    try:
        response = requests.post("http://49.52.18.227:5000/generate", json={"prompt": prompt})
        response.raise_for_status()  # 检查请求是否成功
        response_data = response.json()
        
        # 释放显存
        torch.cuda.empty_cache()
        
        return jsonify(response_data)  # 返回生成的响应
    except requests.exceptions.RequestException as e:
        return jsonify({"error": str(e)}), 500  # 返回请求错误信息


@app.route('/api/model/rag/quiz', methods=['POST'])
def rag_quiz():
    """
    利用 RAG 知识库优化大模型的回复。

    接收用户输入的 JSON 格式的数据，返回经过 RAG 处理后大模型的回复（JSON 格式）。
    """
    data = request.get_json()
    user_question = data.get("user_question")
    if not user_question:
        return jsonify({"error": 'No user question provided'}), 400
    
    prompt = rag_instance.get_rag_database_linear_algebra(user_question)
    
    # 发送请求到生成服务
    try:
        response = requests.post("http://49.52.18.227:5000/generate", json={"prompt": prompt})
        response.raise_for_status()  # 检查请求是否成功
        response_data = response.json()
        
        # 释放显存
        torch.cuda.empty_cache()
        
        return jsonify(response_data)  # 返回生成的响应
    except requests.exceptions.RequestException as e:
        return jsonify({"error": str(e)}), 500  # 返回请求错误信息


@app.route('/api/model/rag/library', methods=['POST'])
def rag_library():
    """
    利用 RAG 知识库优化大模型的回复。

    接收用户输入的 JSON 格式的数据，返回经过 RAG 处理后大模型的回复（JSON 格式）。
    """
    data = request.get_json()
    user_question = data.get("user_question")
    if not user_question:
        return jsonify({"error": 'No user question provided'}), 400
    
    prompt = rag_instance.get_rag_database_linear_algebra(user_question)
    
    # 发送请求到生成服务
    try:
        response = requests.post("http://49.52.18.227:5000/generate", json={"prompt": prompt})
        response.raise_for_status()  # 检查请求是否成功
        response_data = response.json()
        
        # 释放显存
        torch.cuda.empty_cache()
        
        return jsonify(response_data)  # 返回生成的响应
    except requests.exceptions.RequestException as e:
        return jsonify({"error": str(e)}), 500  # 返回请求错误信息


















if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5004, debug=False)

