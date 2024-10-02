# AI-Professor for Linear Algebra

## What is the project?

**An AI chatbot specializing in linear algebra. Powered by Qwen2.5 Model and RAG with knowledge base prepared by an expert in Linear Algebra.**

Since its launch in 2022, ChatGPT has brought large language models into everyday use, helping university faculty and students with questions and problems. Despite the continuous enhancement of general-purpose pre-trained language models, their professional capabilities in research, teaching, and learning still show some limitations. To make large language models more effective for university education, our team has developed an "AI Teaching Assistant" using the MindSpore framework, the RAG knowledge base, and the Qwen2.5 pre-trained model. This assistant is equipped with strong professional skills and a comprehensive interactive interface, which not only alleviates the teaching workload of educators but also significantly improves student learning outcomes.

Currently, the knowledge base of our project consists of a dataset of over 200,000 words, crafted by our team. When integrated with the large model, it has demonstrated exceptional results in the field of linear algebra. Moving forward, our team will focus on enhancing the AI Teaching Assistant's generalization capabilities by incorporating academic papers, textbooks, reference materials, exam questions, and other resources directly into the RAG knowledge base, enabling students from different majors and grade levels to use it effectively.

This project was launched by Professor Wu Min (吴敏) at the Software Engineering Institute, East China Normal University, and three of her undergraduate students, Wang Haisheng (王海生), Guo Yaying (郭雅颖), and Yi Fangxin (易方馨) in June 2024. A pre-release ([repository here](https://github.com/Lzzzzy4/Mind-Overflow)) was made by Wang Haisheng, Lai Zeyuan (来泽远, USTC) and Mao Hongzhi (茆弘之, NJU) in August 2024, thanks to the all-round support from Huawei.

## How to run it?

### Requirements

1. The `Qwen2.5-32B-Instruct` Model with nf4 quant type requires a NVIDIA GPU (or GPUs) with no less than 24GB graphics memory. You may adjust the quant type to take advantage of larger memory, or change the model to fit for a larger range of memory size.
2. Access to the Internet is recommended when the project is deployed. If the server is located in China mainland where internet is occasionally blocked, try using `export HF_ENDPOINT=https://hf-mirror.com` demand to setup a mirror for `huggingface.co`.
3. A virtual environment such as `Anaconda` is suggested.
4. The code has successfully passed a test on such a machine:

| Item | Description |
|----------------------|---------------------------------|
| **Operating System** | Ubuntu 22.04 LTS |
| **CPU**            | Intel Core i9-14900K |
| **GPU**            | NVIDIA GeForce RTX 4090 D (24G) |
| **Memory**         | 64GB RAM |
| **Disk**           | 4TB SSD |


### Simple Tryout

```bash
$ pip install -r requirements.txt
$ cd path/to/ai-prof
$ cd backend
$ python qwen_drive.py
$ python RAG.py
$ cd ..
$ cd frontend
$ python app.py
```

### Official Deployment

```bash
$ pip install -r requirements.txt
$ cd path/to/ai-prof
$ chmod +x run_service.sh
$ ./run_service.sh
```

## API Details

### http://example.com:5000/generate

POST:

```json
{
    "prompt":"The direct instruction to the LLM"
}
```

Response:

```json
{
    "response":"The LLM's answer to the prompt"
}
```

### http://example.com:5001/rag

POST:

```json
{
    "user_question":"The original question raised by a user"
}
```

Response:

```json
{
    "prompt":"A processed question with RAG"
}
```

## Release History and Plans

### History

- V1: Specialized in linear algebra, with shabby frontend, and suitable for tens or hundreds of users.

### Plans

- V2: This version features a more elaborate and sophisticated frontend to be developed by one of my friends. Additionally, user login and personal conversation history (loaded from the server rather than cookies) will be available.
- V3: This version will be a major upgrade. The RAG part will support reading documents in PDF (and maybe more) format so that theoretically the model will be proficient in any realm of knowledge. The frontend will be upgraded accordingly.

## Contact the Author

The developers of this project are primarily undergraduate students with limited development experience, so there may be bugs or areas that are not fully optimized. We kindly ask for your understanding. If you have any suggestions, feel free to submit a pull request or contact the authors via email (hs.wang.chn@outlook.com). Your feedback is greatly appreciated.
