# OurHood
> ### 우리만의 공간, 특별한 추억


## 📸 새로운 사진 공유 플랫폼 OurHood를 소개해요!
- **OurHood는 가족, 친구, 팀, 동아리 등 개인적인 조직 단위로 추억과 사진을 공유할 수 있는 플랫폼입니다.**
- **우리만의 방에서만 소통하며, 안전하고 따뜻한 공간을 경험해보세요!**


## 🔥OurHood 핵심 기능


| 방 생성                                                                                                                                                                                                                                                    | 사진 조회                                                                                                                                                                                                                                                   | 
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| 
| <img src="https://ourhood-bucket.s3.ap-northeast-2.amazonaws.com/assets/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2024-11-30+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+7.04.16.png" width=300 height= 350/> | <img src="https://ourhood-bucket.s3.ap-northeast-2.amazonaws.com/assets/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2024-11-30+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+6.54.10.png" width=500 height=350 /> | 

| 사진 생성                                                                                                                                                                                                                                                  | 댓글 생성                                                                                                                                                                                                                                                    |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://ourhood-bucket.s3.ap-northeast-2.amazonaws.com/assets/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2024-11-30+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+7.04.50.png" width=300 height=350/> | <img src="https://ourhood-bucket.s3.ap-northeast-2.amazonaws.com/assets/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2024-11-30+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+6.55.46.png" width=300 height= 350 /> |



## 📂 Directory Structure
```
└── src
    └── main
        └── java
            └── server
               └── photo
                   ├── domain
                   │   ├── comment # Comment 도메인 폴더
                   │   ├── invitation # Invitation 도메인 폴더
                   │   ├── join # JoinRequest 도메인 폴더
                   │   ├── moment # Moment 도메인 폴더
                   │   ├── refresh # RefreshToken 도메인 폴더
                   │   ├── room # Room 도메인 폴더
                   │   └── user # User 도메인 폴더
                   └── global
                       ├── config # 설정 파일 폴더
                       ├── handler # 예외 처리 및 공통 응답 폴더
                       ├── jwt # JWT 폴더
                       ├── s3 # s3 service 폴더 
                       └── util # util service 폴더




```

## 🛠 Tech Stack


| 구분            | 기술 스택                                                                                                                                                                                                                                                                                                                                          |
|---------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Framework     | <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-social&logo=Spring Boot&logoColor=white"> <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-social&logo=Gradle&logoColor=white">                                                                                                                      
| ORM           | <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-social&logo=Databricks&logoColor=white">                                                                                                                                                                                                                           |
| Authorization | <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-social&logo=springsecurity&logoColor=white">  <img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-social&logo=JSON Web Tokens&logoColor=white">                                                                                            |
| Database      | <img src="https://img.shields.io/badge/MySQL-4479A1.svg?style=for-the-social&logo=MySQL&logoColor=white">                                                                                                                                                                                                                                      |
| AWS           | <img src="https://img.shields.io/badge/AWS RDS-527FFF?style=for-the-social&logo=amazonrds&logoColor=white"> <img src ="https://img.shields.io/badge/AWS S3-69A31?style=for-the-social&logo=amazons3&logoColor=white"> <img src ="https://img.shields.io/badge/AWS CodeDeploy-6DB33F?style=for-the-social&logo=awscodedeploy&logoColor=white"> <img src ="https://img.shields.io/badge/AWS EC2-FF9900?style=for-the-social&logo=amazonec2&logoColor=white"> |


## 📈 DataBase Schema
### MySQL Schema

<img src="https://ourhood-s3-bucket.s3.ap-northeast-2.amazonaws.com/assets/erd.png" width="500" height="314">

## 👥 Contributors

| FE                                                                                                                    |
|-----------------------------------------------------------------------------------------------------------------------|
| <a href="https://github.com/dongha-choi"><img src="https://github.com/dongha-choi.png" alt="profile" width="140"></a> | 
| [최동하](https://github.com/dongha-choi)                                                                                 |


| BE |
| --- |
| <a href="https://github.com/so1eeee"><img src="https://github.com/so1eeee.png" alt="profile" width="140"> </a> 
|[정 솔](https://github.com/so1eeee)

