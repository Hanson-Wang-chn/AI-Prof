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
        # self.dict = {item['question']: item['output'] for item in db}
        self.dict = {item['question']: item.get('output', '') for item in db}
        loader = JSONLoader(database_path, jq_schema='.[].question')
        documents = loader.load()

        # word2vec
        model_name = "moka-ai/m3e-base"
        model_kwargs = {'device': 'cpu'}
        # model_kwargs = {'device': 'cuda:0'}
        encode_kwargs = {'normalize_embeddings': True}
        embedding = HuggingFaceBgeEmbeddings(
            model_name=model_name,
            model_kwargs=model_kwargs,
            encode_kwargs=encode_kwargs,
            query_instruction="为文本生成向量表示用于文本检索"
        )
        self.db = Chroma.from_documents(documents, embedding)


    def get_rag(self, user_question:str):
        """
        结合用户的问题和RAG结果，生成优化的prompt。

        参数:
            user_question (str): 用户的问题。
        
        返回:
            str: RAG优化结果。
        """
        context = self.db.similarity_search(user_question)
        reference = ''
        
        # for i,ans in enumerate(context):
        #     reference += f"参考提问{i}：\n{ans.page_content}\n\n"
        #     reference += f"参考回答{i}：\n{self.dic[ans.page_content]}\n\n"
        
        if not context:
            return "没有找到相关信息。"
        
        reference += f"参考提问：\n{context[0].page_content}\n\n"
        reference += f"参考回答：\n{self.dict[context[0].page_content]}\n\n"
        
        prompt = (
            f"我将向你提问，你回答的规则是：若参考信息不为空且存在和我的问题相关的有效信息，"
            f"则必须优先使用参考信息中的内容回复，并结合你的知识稍作润色；"
            f"否则直接用你的知识回答。\n"
            f"如果存在数学计算请给出每一步的具体过程。"
            f"参考信息：\n{reference}\n"
            f"我的问题是：\n{user_question}\n"
            f"务必注意，所有的回答必须使用Markdown格式，不能含有其他格式。"            
            f"嵌入数学公式时必须使用\(...\)、\[...\]或$$...$$格式，公式前后必须空行或空格。"
            f"如果输出的内容含有代码，代码的首尾需要空行。"
            f"如果输出的内容含有其他数学公式，数学公式的首尾需要空行或空格。"
        )
        
        return prompt

    
# 在应用启动时创建 RAG 实例
rag_instance = RAG()


@app.route('/rag', methods=['POST'])
def rag():
    """
    利用 RAG 知识库优化大模型的回复。

    接收用户输入的 JSON 格式的数据，返回经过 RAG 处理后大模型的回复（JSON 格式）。
    """
    data = request.get_json()
    user_question = data.get("user_question")
    if not user_question:
        return jsonify({"error": 'No user question provided'}), 400
    
    prompt = rag_instance.get_rag(user_question)
    # return jsonify({"prompt": prompt})
    
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
    app.run(host='0.0.0.0', port=5001, debug=False)

