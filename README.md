rest-oauth
==========

rest-oauth

Мы используем для аутентификации и авторизации протокол OAuth 2.0

Одну из четырех схем авторизации - Resource Owner Password Credentials
http://tools.ietf.org/html/rfc6749#section-1.3.3
http://tools.ietf.org/html/draft-ietf-oauth-v2-31#section-4.3


4.3.2.  Access Token Request

   The client makes a request to the token endpoint by adding the
   following parameters using the "application/x-www-form-urlencoded"
   format per Appendix B with a character encoding of UTF-8 in the HTTP
   request entity-body:

```bash
   grant_type
         REQUIRED.  Value MUST be set to "password".
   username
         REQUIRED.  The resource owner username.
   password
         REQUIRED.  The resource owner password.
```



 4.3.3.  Access Token Response

    If the access token request is valid and authorized, the
    authorization server issues an access token and optional refresh
    token as described in Section 5.1.  If the request failed client
    authentication or is invalid, the authorization server returns an
    error response as described in Section 5.2.

    An example successful response:

```bash
      HTTP/1.1 200 OK
      Content-Type: application/json;charset=UTF-8
      Cache-Control: no-store
      Pragma: no-cache
```

```json
      {
        "access_token":"2YotnFZFEjr1zCsicMWpAA",
        "token_type":"example",
        "expires_in":3600,
        "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
        "example_parameter":"example_value"
      }
```