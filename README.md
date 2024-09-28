# AI-Professor for Linear Algebra

## What is the project?

## How to run it?

### Requirements

1. The Qwen2.5-32B-Instruct Model with nf4 quant type requires a NVIDIA GPU (or GPUs) with no less than 24GB graphics memory. You may adjust the quant type to take advantage of larger memory, or change the model to fit for a larger range of memory size.
2. Access to the Internet is recommended when the project is deployed. If the server is located in China mainland where internet is occasionally blocked, try using `export HF_ENDPOINT=https://hf-mirror.com` demand to setup a mirror for `huggingface.co`.

### Start Backend

```bash
$ cd path/to/ai-prof
$ cd backend
$ python qwen_drive.py
$ python RAG.py
```

### Start Frontend

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

## FAQs