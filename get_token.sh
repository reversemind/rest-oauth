#!/bin/bash

# LINUX
curl --noproxy localhost -i -X POST -v -d "username=_sample_user_&password=zMMeaQnMWDsDpGLh&client_id=_sample_user_&client_secret=zMMeaQnMWDsDpGLh&grant_type=password" http://localhost:8080/rest-oauth/oauth/token