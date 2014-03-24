#!/bin/bash

# Windows
#curl -i --noproxy * -X -v -d "username=user1&amp;password=user1&amp;client_id=client1&amp;client_secret=client1&amp;grant_type=password"   -X POST"http://localhost:8080/rst/oauth/token"


# LINUX
curl --noproxy localhost -i -X POST -v -d "username=user1&password=user1&client_id=client1&client_secret=client1&grant_type=password" http://localhost:8080/rest-oauth/oauth/token