# Auth Module

1. 기능
- OAuth Login에 대한 멤버 인증
- Native Login에 대한 멤버 인증 
- jwt token 기반 사용자 정보 획득

2. API URI

|            Endpoint             |             용도             |  Method |
|:-----------------------------:|:---------------------------:|:---------------------------:|
|  {{host}}:8000/auth/oauth    |   OAuth Login에 대한 멤버 인증   | POST |
| {{host}}:8000/auth/normal |       Native Login에 대한 멤버 인증       | POST|
| {{host}}:8000/members | jwt token 기반 사용자 정보 획득 |GET|
