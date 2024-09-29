from flask import Flask, request, jsonify, render_template
import requests
from concurrent.futures import ThreadPoolExecutor


app = Flask(__name__)
executor = ThreadPoolExecutor(max_workers=100)  # 设置线程池大小


@app.route('/', methods=['GET', 'POST'])
def index():
    return render_template('index.html')

def fetch_answer(user_question):
    try:
        response = requests.post('http://49.52.18.227:5001/rag', json={"user_question": user_question})
        response_data = response.json()
        return response_data.get('response', '发生错误，大模型未提供回答。')
    except Exception as e:
        print("Error occurred:", e)
        return '网络错误，请稍后再试。'


@app.route('/ask', methods=['POST'])
def ask():
    data = request.json
    user_question = data.get('query')

    future = executor.submit(fetch_answer, user_question)
    answer = future.result()

    return jsonify({'answer': answer})


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5002)
