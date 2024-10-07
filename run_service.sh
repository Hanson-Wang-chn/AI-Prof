#!/bin/bash

# 定义后台服务启动的函数
start_service() {
    local command=$1
    echo "Starting service: $command"
    nohup $command > /dev/null 2>&1 & # 将输出重定向到 /dev/null
    echo "Service started with PID: $!"
}

# 进入 LLM 文件夹
cd LLM || { echo "Failed to change directory to backend"; exit 1; }

# 启动第一个服务，qwen_drive
start_service "gunicorn --workers 1 --threads 1 --bind 0.0.0.0:5000 --max-requests 100 qwen_drive:app"

# 启动第二个服务，RAG
start_service "gunicorn --workers 1 --threads 1 --bind 0.0.0.0:5001 --max-requests 100 RAG_prev:app"

# 返回到主文件夹
cd .. || { echo "Failed to change directory to parent"; exit 1; }

# 进入 frontend_prev 文件夹
cd frontend_prev || { echo "Failed to change directory to frontend"; exit 1; }

# 启动第三个服务，app
start_service "gunicorn --workers 56 --threads 1 --bind 0.0.0.0:5002 --max-requests 100 app:app"

echo "All services have been started."
