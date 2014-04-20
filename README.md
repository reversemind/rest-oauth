rest-oauth
==========

rest-oauth



Общие положения

${PREFIX_SERVER_URL} - префикс URL для функций и точек входа (выдается приватно)

${LOGIN} - логин (выдается приватно)

${PASSWORD} - пароль (выдается приватно)




### Аутентификация

Для аутентификации используется протокол OAuth 2.0

Схема [Resource Owner Password Credentials] - http://tools.ietf.org/html/rfc6749#section-1.3.3 http://tools.ietf.org/html/draft-ietf-oauth-v2-31#section-4.3


     +----------+
     | Resource |
     |  Owner   |
     |          |
     +----------+
          v
          |    Resource Owner
         (A) Password Credentials
          |
          v
     +---------+                                  +---------------+
     |         |>--(B)---- Resource Owner ------->|               |
     |         |         Password Credentials     | Authorization |
     | Client  |                                  |     Server    |
     |         |<--(C)---- Access Token ---------<|               |
     |         |    (w/ Optional Refresh Token)   |               |
     +---------+                                  +---------------+

            Resource Owner Password Credentials



#### Запрос для получения [Access Token]

Клиент делает запрос на входную точку:
```bash
https://${PREFIX_SERVER_URL}/oauth/token
```

Добавляет следующие параметры (все обязательны), используя "application/x-www-form-urlencoded", кодировка UTF-8 в HTTP request entity-body:

```bash
   grant_type
           Значение всегда "password".
   username
         ${LOGIN}
   password
         ${PASSWORD}
   client_id
         ${LOGIN}                  
   client_secret
         ${PASSWORD}         
```

В данной реализации значения username = client_id и password = client_secret


#### Успешный ответ с [Access Token]

```bash
      HTTP/1.1 200 OK
      Content-Type: application/json;charset=UTF-8
      Cache-Control: no-store
      Pragma: no-cache
```

```json
   {
       "access_token": "8b0f866b-30fa-4426-8122-af8f4097942c", 
       "token_type": "bearer", 
       "refresh_token": "a1cf4020-5336-45c1-be98-ca4018b76b29", 
       "expires_in": 3599
   }
```
"expires_in" - значение в секундах, 3600 - выдается на один час в этой версии

"token_type" - в этой версии всегда "bearer"

Полученное значение "access_token": "8b0f866b-30fa-4426-8122-af8f4097942c" - необходимо использовать для доступа к функциям


При отказе в Аутентификации вернется HTTP-401
```json
Если такого Клиента нет
{"error":"unauthorized","error_description":"An Authentication object was not found in the SecurityContext"}

Если не правильный пароль
{"error":"invalid_client","error_description":"Bad client credentials"}
```

При отказе в Авторизации вернется HTTP-403
