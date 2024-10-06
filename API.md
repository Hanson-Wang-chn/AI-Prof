# API Documentation

## User Authentication Endpoints

### 1. User Registration

**Endpoint:**
- `POST http://example.com:5003/api/user/register`

**Description:**
- This endpoint is used for user registration.

**Request:**

```json
{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
}
```

**Response:**

```json
{
    "message": "'successful' or 'failed'",
    // 'successful' stands for the email account is available, while 'failed' means the account has been used.
    "user_id": "f5669feb-0065-466e-b51c-8bbbeab4c801" // a random UUID
}
```

---

### 2. E-mail Verification for Registration

**Endpoint:**
- `POST http://example.com:5003/api/user/verification_register`

**Description:**
- This endpoint is used for email verification for registration.

**Request:**

```json
{
    "email": "test@example.com",
    "user_id": "f5669feb-0065-466e-b51c-8bbbeab4c801",
    "code": "Verification code sent to the user's email"
}
```

**Response:**

```json
{
    "message": "'True' or 'False'"
    // 'True' indicates the code is correct, while 'False' indecates not.
}
```

---

### 3. User Login

**Endpoint:**
- `POST http://example.com:5003/api/user/login`

**Description:**
- This endpoint is used for user login. If failed, ask the user to try again or reset the password.

**Request:**

```json
{
    "email": "test@example.com",
    "password": "password123"
}
```

**Response:**

```json
{
    "message": "'successful' or 'failed'",
    "user_id": "f5669feb-0065-466e-b51c-8bbbeab4c801",
    "token": "token" // JSON Web Token
    // The JWT should be saved in the local memory of the browser.
}
```

---

### 4. Reset Password

**Endpoint:**
- `POST http://example.com:5003/api/user/reset_pwd`

**Description:**
- This endpoint is used to reset password.

**Request:**

```json
{
    "email": "test@example.com"
}
```

**Response:**

```json
{
    "message": "'successful' or 'failed'",
    // 'successful' stands for the email account exists, while 'failed' means the account has not been registered.
}
```

---

### 5. E-mail Verification for Resetting Password

**Endpoint:**
- `POST http://example.com:5003/api/user/verification_reset_pwd`

**Description:**
- This endpoint is used to verify email account when resetting password.

**Request:**

```json
{
    "email": "test@example.com",
    "new_pwd": "The new password set by the user"
}
```

**Response:**

```json
{
    "message": "'successful' or 'failed'"
    // 'successful' means a new password has been set successfully, while 'failed' means the previous password remains.
}
```

---

### 6. Query User Information

**Endpoint:**
- `GET http://example.com:5003/api/user/{user_id}`

**Description:**
- This endpoint allows querying user information based on their user ID.

**Response:**

```json
{
    "message": "'successful' or 'failed'",
    "username": "testuser",
    "email": "test@example.com",
    "user_id": "f5669feb-0065-466e-b51c-8bbbeab4c801"
}
```

---

## User Interface Endpoints

### 1. Homepage

**Endpoint:**
- `GET http://example.com:5003/api/ui/home`

**Description:**
- This endpoint is used for the homepage of the website.

**Response:**

```json
{
    "message": "Welcome!"
}
```

---

### 2. Homepage for Study

**Endpoint:**
- `GET http://example.com:5003/api/ui/study/{user_id}`

**Description:**
- This endpoint is used for the homepage for study.

**Response:**

```json
{
    "message": "Welcome!",
    "data": "Data for the Data Analysis Section" // 此处待定
}
```

---

### 3. Page for Chat

**Endpoint:**
- `GET http://example.com:5003/api/ui/study/chat/{user_id}`

**Description:**
- This endpoint is used for the page for chat.

**Response:**

```json
{
    "message": "Welcome!",
    "history": [
        {
            "conversation_id": "uuid1",
            "conversation_name": "A",
            "created_at": "2024-10-06"
        },
        {
            "conversation_id": "uuid2",
            "conversation_name": "B",
            "created_at": "2024-10-05"
        }
        // ...
    ]
}
```

---

### 4. Page for Guidance

**Endpoint:**
- `GET http://example.com:5003/api/ui/study/guidance/{user_id}`

**Description:**
- This endpoint is used for the page for guidance.

**Response:**

```json
{
    "message": "Welcome!",
    "data": [
        {
            "image_id": "image_id1",
            "image_name": "image_name1"
        },
        // ...
        {
            "mindmap_id": "mindmap_id1",
            "mindmap_name": "mindmap_name1"
        }
        // ...
    ]
}
```

---

### 5. Page for Library

**Endpoint:**
- `GET http://example.com:5003/api/ui/study/library/{user_id}`

**Description:**
- This endpoint is used for the page for library.

**Response:**

```json
{
    "message": "Welcome!",
    "data": [
        {
            "question_id": "question_id1",
            "knowledge_point": "knowledge_point1",
            "description": "description1",
            "answer": "answer1"
        },
        {
            "question_id": "question_id2",
            "knowledge_point": "knowledge_point2",
            "description": "description2",
            "answer": "answer2"
        }
        // ...
    ]
}
```

---

## Large Language Model Endpoints

### 1. Basic Chat

**Endpoint:**
- `POST http://example.com:5000/api/model/generate`

**Description:**
- This endpoint is used for basic chat with the LLM.
- **WARNING: This API should be treated carefully. The frontend ought not to use it while the backend is not suggested to use it.**

**Request:**

```json
{
    "prompt": "Nice to meet you!"
}
```

**Response:**

```json
{
    "response": "Nice to meet you too. How can I help you?"
}
```

---

### 2. Chat with RAG Database

**Endpoint:**
- `POST http://example.com:5001/api/model/rag/chat`

**Description:**
- This endpoint is used for basic chat with RAG database.

**Request:**

```json
{
    "user_question": "The original question raised by a user"
}
```

**Response:**

```json
{
    "response": "The LLM's answer to the question raised by the user and processed by RAG"
}
```

---

### 3. TF Questions

**Endpoint:**
- `POST http://example.com:5001/api/model/rag/tf`

**Description:**
- This endpoint is used for generating TF questions.

**Request:**

```json
{
    "knowledge_point": "Knowledge points surrounding the TF questions to be generated by the large model",
    "reference": "Some specific examples or related information on how the TF questions are supposed to be generated",
    "number": "How many TF questions are required"
}
```

**Response:**

```json
{
    "response": "The TF questions generated by the LLM"
}
```

---

### 4. Probing

**Endpoint:**
- `POST http://example.com:5001/api/model/rag/probe`

**Description:**
- This endpoint is used for generating sequential questions.

**Request:**

```json
{
    "knowledge_point": "Knowledge points surrounding the sequential questions to be generated by the large model",
    "reference": "Some specific examples or related information on how the sequential questions are supposed to be generated",
    "number": "How many sequential questions are required"
}
```

**Response:**

```json
{
    "response": "The sequential questions generated by the LLM"
}
```

---

### 5. Quiz

**Endpoint:**
- `POST http://example.com:5001/api/model/rag/quiz`

**Description:**
- This endpoint is used for making quiz.

**Request:**

```json
{
    "knowledge_point": "Knowledge points surrounding the quiz to be generated by the large model",
    "reference": "Some specific examples or related information on how the quiz is supposed to be generated",
    "number": "Number of questions in the quiz"
}
```

**Response:**

```json
{
    "response": "The quiz made by the LLM"
}
```

---

### 6. Query Library

**Endpoint:**
- `POST http://example.com:5001/api/model/rag/library`

**Description:**
- This endpoint is used for user's doubts around questions in the library.

**Request:**

```json
{
    "knowledge_point": "The knowledge point of the question in the library",
    "description": "The description of the question",
    "answer": "The right answer to the question",
    "user_question": "The question raised by the user concerning his or her doubts"
}

```

**Response:**

```json
{
    "response": "The answer to the user's question"
}
```

---