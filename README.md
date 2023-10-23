# bookProject
## 프로젝트용 DB생성


    create database db_bookproject;

    create user 'user_bookproject'@'localhost' identified by '1234';

    grant all privileges on db_bookproject.* to 'user_bookproject' @'localhost';

