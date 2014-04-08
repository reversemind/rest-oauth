#!/bin/bash

# LINUX
curl --noproxy localhost -i -X POST -v -d "username=_sample_user_&password=zMMeaQnMWDsDpGLh&client_id=_sample_user_&client_secret=zMMeaQnMWDsDpGLh&grant_type=password" http://localhost:8080/rest-oauth/oauth/token

#wrong Basic token
curl --noproxy localhost -i -X POST -v -d "username=_sample_user_&password=zMMeaQnMWDsDpGLh&grant_type=password" -H "Authorization:Basic X3NhbXBsZV91c2VyXzp6TU1lYVFuTVdEc0RwR0xo" http://localhost:18181/rest-oauth/oauth/token

#wrong PASSWORD
curl --noproxy localhost -i -X POST -v -d "username=_sample_user_&password=WRONG_PASSWORD&grant_type=password" -H "Authorization:Basic X3NhbXBsZV91c2VyXzp6TU1lYVFuTVdEc0RwR0xo" http://localhost:18181/rest-oauth/oauth/token