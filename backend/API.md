API Documentation

User Authentication Endpoints

1. User Registration

Endpoint:

      POST http://example.com:5003/api/user/register
Description:

      This endpoint is used for user registration.

Request:

      {
         "userName": "testuser",
         “classNum”:“1”,
         "email": "test@example.com",
         "password": "password123"
      }

Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’"
      }

2. E-mail Verification for Registration
   
   (1)

Endpoint:

      POST http://example.com:5003/api/user/verification_register
Description:

      This endpoint is used for email verification for registration.
Request:

      {
         "email": "test@example.com"
      }

Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’"
      }

   (2)

Endpoint:

      POST http://example.com:5003/api/user/verify/{email}
Description:

      This endpoint is used for verifying code
Request:

      {
         "email": "test@example.com",
         “code”:”verification code”
      }
Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’"
      }


3. User Login

Endpoint:

      POST http://example.com:5003/api/user/login
Description:

      This endpoint is used for user login. If failed, ask the user to try again or reset the password.
Request:

      {
         "password": "password123"
         // The JWT should be saved in the local memory of the browser.
      }
Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’"
         “userInfoVO”:”’userId’,’userName’,’classNum’,’email’”  
      }

4. Reset Password
   
Endpoint:

      POST http://example.com:5003/api/user/reset_pwd
Description:

      This endpoint is used to reset password.
Request:

      {
         "newPassword": "password321"
      }
Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’"
      }

5. E-mail Verification for Resetting Password

Endpoint:

      POST http://example.com:5003/api/user/verification_reset_pwd
Description:

      This endpoint is used to verify email account when resetting password.
Request:

      {
         "email": "test@example.com",
      }
Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’"
      }

6. Query User Information

Endpoint:

      GET http://example.com:5003/api/user/{user_id}
Description:

      This endpoint allows querying user information based on their user ID.
Request:

      {
         "password": "password123"
      }
Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’",
         "user_id": "f5669feb-0065-466e-b51c-8bbbeab4c801",
         "username": "testuser",
         “classNum”:”1/2/3/4”,
         "email": "test@example.com"
      }

User Interface Endpoints

1. Homepage

Endpoint:

      GET http://example.com:5003/api/ui/home
Description: 

      This endpoint is used for the homepage of the website.

2. Homepage for Study（Data Analysis）

Endpoint:

      GET http://example.com:5003/api/ui/study/{user_id}
Description:

      This endpoint is used for the homepage for study.
Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’”,
         “TotalExeNum”:”total exercise number”,
         “DailyExeNum Map<LocalDate,Long>”: [
            {
            "LocalDate": "YYYY-MM-DD",//like 2024-10-12
            “number(Long)”:”number of exercise on the day”
            },
            // ...
        ]
      }

3. Page for Chat

Endpoint:

      GET http://example.com:5003/api/ui/study/chat/{user_id}
Description:

      This endpoint is used for the page for chat.
getHistories

Request:

      {
         “groupId”:”group_id of the history group”
      }
Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’",
         "Conversations List<ConversationVO>": [
            {
            "conversation_text": "A",
            “type”:”1/0”,//1 for query, 0 for response
            "created_at": "create_time"//like 2024-10-06, database will generate it automatically
            },
            // ...
         ]
      }

insertHistory

Request:

      {
         “groupId”:”group_id of the history group”,
         “text”:”history content”,
         “type”:”Query(1) or response(0)”
      }
Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’"
      }

deleteGroup

Request:

      {
         “groupId”:”group_id of the history group”
      }
Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’",
         "Groups List<HistoryGroupVO>": [
            {
            "group_name": "新建对话",
            “group_id”:”id”
            },
            // ...
        ]
      }

getAllGroup

Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’"
      }


4. Page for Guidance

Endpoint:

      GET http://example.com:5003/api/ui/study/guidance/{user_id}
Description:

      This endpoint is used for the page for guidance.

5. Page for Library

Endpoint:

      GET http://example.com:5003/api/ui/study/library/{user_id}
Description:

      This endpoint is used for the page for library.
Response:

      {
         "baseResponse_code": "'Success' ,'Biz_exception', ‘System_exception’",
         "baseResponse_message":"’成功’,’业务异常’,’系统异常’",
         "page (ExercisePage)": [
            {
            "exeId": "id",
            “exeGroup”:”学科”,
            "question",
            "answer",
            "knlgPoint",
            “updateTime”
            }
         ],
         “Total_page_number”,
         “page_number”
      }

