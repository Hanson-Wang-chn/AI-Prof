# import torch
# from transformers import AutoTokenizer, AutoModelForSeq2SeqLM

# def summarize_text(text, max_tokens):
#     """
#     使用本地下载的 BART-base-chinese 模型生成摘要，并确保摘要不超过指定的 token 上限。

#     参数:
#     - text (str): 输入的文本
#     - max_tokens (int): token 上限

#     返回:
#     - str: 生成的摘要
#     """
#     # 指定本地模型路径
#     local_model_path = "/home/whs/ai-prof/utility/bart-base-chinese"

#     # 加载本地的 BART 模型和分词器
#     tokenizer = AutoTokenizer.from_pretrained(local_model_path)
#     model = AutoModelForSeq2SeqLM.from_pretrained(local_model_path)

#     # 将文本编码为 token 
#     inputs = tokenizer(text, return_tensors="pt", max_length=512, truncation=True)

#     # 使用 CPU 进行推理
#     with torch.no_grad():
#         # 生成摘要
#         summary_ids = model.generate(
#             inputs['input_ids'],
#             max_length=max_tokens,  # 生成的最大 token 数量
#             num_beams=4,            # 增加 beam search 提高质量
#             length_penalty=2.0,     # 防止生成过长文本
#             early_stopping=True,    # 提前停止
#             no_repeat_ngram_size=3, # 防止重复
#             repetition_penalty=1.2, # 增加生成多样性
#         )


#     # 解码生成的 token 为中文文本
#     summary = tokenizer.decode(summary_ids[0], skip_special_tokens=True)

#     return summary

# # 示例使用方法
# if __name__ == "__main__":
#     # 输入文本
#     text = "今天万里无云，晴空万里，艳阳高照，风和景明。"
    
#     # 设置生成的最大 token 数
#     max_tokens = 50

#     # 生成摘要
#     summary = summarize_text(text, max_tokens)
#     print("生成的摘要：", summary)








import torch
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM

class TextSummarizer:
    """
    使用本地下载的 BART-base-chinese 模型生成摘要。
    """

    def __init__(self):
        """
        初始化分词器和模型。

        参数:
        - model_path (str): 本地模型的路径
        """
        model_path = "/home/whs/ai-prof/LLM/bart-base-chinese"
        self.tokenizer = AutoTokenizer.from_pretrained(model_path)
        self.model = AutoModelForSeq2SeqLM.from_pretrained(model_path)

    def summarize(self, text, max_tokens):
        """
        生成文本摘要。

        参数:
        - text (str): 输入的文本
        - max_tokens (int): token 上限

        返回:
        - str: 生成的摘要
        """
        # 将文本编码为 token
        inputs = self.tokenizer(text, return_tensors="pt", max_length=1024, truncation=True)

        # 使用 CPU 进行推理
        with torch.no_grad():
            # 生成摘要
            summary_ids = self.model.generate(
                inputs['input_ids'],
                max_length=max_tokens,  # 生成的最大 token 数量
                num_beams=4,            # 增加 beam search 提高质量
                length_penalty=2.0,     # 防止生成过长文本
                early_stopping=True,    # 提前停止
                no_repeat_ngram_size=3, # 防止重复
                repetition_penalty=1.2, # 增加生成多样性
            )

        # 解码生成的 token 为中文文本
        summary = self.tokenizer.decode(summary_ids[0], skip_special_tokens=True)

        return summary

