from flask import Flask, request, jsonify, render_template
import requests
from concurrent.futures import ThreadPoolExecutor
import redis
from redis.exceptions import LockError

app = Flask(__name__)
executor = ThreadPoolExecutor(max_workers=112)  # 设置线程池大小

# 配置 Redis 连接
redis_client = redis.StrictRedis(host='localhost', port=6379, db=0)

@app.route('/', methods=['GET', 'POST'])
def index():
    return render_template('index.html')

def fetch_answer(user_question):
    lock = redis_client.lock("api_lock", timeout=120)  # 创建 Redis 锁
    try:
        if lock.acquire(blocking=True):  # 阻塞直到获取锁
            response = requests.post('http://49.52.18.227:5001/rag', json={"user_question": user_question})
            response_data = response.json()
            return response_data.get('response', '发生错误，大模型未提供回答。')
        else:
            return '请求超时，请稍后再试。'
    except Exception as e:
        print("Error occurred:", e)
        return '网络错误，请稍后再试。'
    finally:
        if lock.locked():
            lock.release()  # 确保释放锁

@app.route('/ask', methods=['POST'])
def ask():
    data = request.json
    user_question = data.get('query')

    # 提交任务到线程池，并等待结果
    future = executor.submit(fetch_answer, user_question)
    answer = future.result()

    return jsonify({'answer': answer})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5002)
