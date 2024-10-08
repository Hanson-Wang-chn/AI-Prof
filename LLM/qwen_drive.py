import torch
from flask import Flask, request, jsonify
from transformers import AutoModelForCausalLM, AutoTokenizer, BitsAndBytesConfig

app = Flask(__name__)  # 创建 Flask 应用实例

# 模型名称
model_name = "/home/whs/ai-prof/LLM" # Qwen2-32B-Instruct

# 配置BitsAndBytes进行4位量化
bnb_config = BitsAndBytesConfig(
    load_in_4bit=True,                     # 启用4位量化
    bnb_4bit_quant_type="nf4",            # 量化类型，'nf4'是常用选项
    bnb_4bit_compute_dtype=torch.float16   # 计算时使用的dtype
)

# 加载模型和分词器
model = AutoModelForCausalLM.from_pretrained(
    model_name,
    quantization_config=bnb_config,
    device_map="auto",                      # 自动设备映射
    trust_remote_code=True                  # 如果模型需要自定义代码，设置为True
)
tokenizer = AutoTokenizer.from_pretrained(model_name)

# 定义系统角色内容
sys_content = "你的名字是王海生，一个华东师范大学软件工程学院的2023级本科生，是吴敏老师的学生。现在你成为了线性代数课程的助教，需要严格按照指定的格式，友善、逻辑清晰地回答问题，尽可能帮助同学们。"
# sys_content = "You are Qwen, created by Alibaba Cloud. You are a helpful assistant."

def setup_model_input(tokenizer, prompt):
    """
    设置模型的输入信息，包括系统消息和用户提示。

    参数:
        tokenizer: 分词器实例。
        prompt (str): 用户的提示语。
        
    返回:
        PyTorch张量，包含模型的输入。
    """
    # 判断是否有可用的CUDA设备
    if torch.cuda.is_available():
        device = torch.device("cuda")  
    else:
        device = torch.device("cpu")
    
    # 构建消息列表
    messages = [
        {"role": "system", "content": sys_content},
        {"role": "user", "content": prompt}
    ]
    
    # 使用分词器生成聊天模板文本
    text = tokenizer.apply_chat_template(
        messages,
        tokenize=False,
        add_generation_prompt=True
    ) 
    
    # 将文本标记化并转换为PyTorch张量
    return tokenizer([text], return_tensors="pt").to(device)

def msg_generate(prompt):
    """
    根据用户的提示生成模型回复。

    参数:
        prompt (str): 用户的提示语。
        
    返回:
        str: 模型生成的回复。
    """
    # 清理未使用的显存
    torch.cuda.empty_cache()  
    
    # 每次请求清空上下文
    model_inputs = setup_model_input(tokenizer, prompt)
    
    # 生成回复的token ID
    generated_ids = model.generate(
        **model_inputs,
        max_new_tokens=768,
        do_sample=True,                      # 启用采样以获得更具多样性的输出
        temperature=0.7,                     # 采样温度，控制生成文本的随机性
        top_p=0.95                           # 核采样，保留概率累积到top_p的token
    )
    
    # 去除输入部分，只保留生成的部分
    generated_ids = [
        output_ids[len(input_ids):] for input_ids, output_ids in zip(
            model_inputs.input_ids, generated_ids)
    ]
    
    # 解码生成的token ID为文本
    return tokenizer.batch_decode(generated_ids, skip_special_tokens=True)[0]

@app.route('/generate', methods=['POST'])
def generate_response():
    """
    处理生成请求的路由。

    接收 JSON 格式的数据，并返回模型生成的回答。
    """
    data = request.json  # 从请求中获取 JSON 数据
    prompt = data.get("prompt", "")  # 获取用户的提示
    response = msg_generate(prompt)  # 生成模型的回答
    return jsonify({"response": response})  # 返回 JSON 格式的响应

if __name__ == '__main__':
    # 启动 Flask 应用
    app.run(host='0.0.0.0', port=5000, debug=False)

