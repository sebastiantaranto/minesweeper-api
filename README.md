# Minesweeper REST API

A Java based Restful API for the classic Minesweeper Game. [Minesweeper](https://en.wikipedia.org/wiki/Minesweeper_(video_game))

## Contents
- [Project](#project)
- [Deployment](#Deployment)
- [Build](#build)
- [Run](#Run)
- [Notes](#Notes)

## Project
The project was develop using Java, [Spring boot](https://spring.io/projects), [Hibernate](http://hibernate.org/orm) and [Apache Maven](https://maven.apache.org).

## Deployment
The code is deployed in AWS for testing purpose. 
- Base API URL: http://minesweeper.us-east-2.elasticbeanstalk.com
- API definitions: http://minesweeper.us-east-2.elasticbeanstalk.com/api/v1/
 
## Build & Tests
run 'mvn clean install' inside the api folder to run all the tests and generate the jar file.

## Run
A JAR file will be generated inside the 'api\web-app\target' folder. Then run:
java -Dspring.datasource.url=jdbc:mysql://{{database_endpoint}}:{{database_port}}/{{schema}} -Dspring.datasource.username={{username}} -Dspring.datasource.password={{password}} -jar web-app-1.0.0.jar

### Prerequisites
 - [git](https://git-scm.com/downloads): **version >= 2** 
-  [Java](https://www.oracle.com/technetwork/es/java/javase/downloads/index.html): **version >= 1.8** 
-  [MySQL](https://www.mysql.com/): **version >= 8**

## Notes

The project was coded for a specific task so it was developed in just a few hours. The following list were the required task prioritized from most important to least important.

* [x] Design and implement  a documented RESTful API for the game (think of a mobile app for your API)
* [x] Implement an API client library for the API designed above. Ideally, in a different language, of your preference, to the one used for the API
* [x] When a cell with no adjacent mines is revealed, all adjacent squares will be revealed (and repeat)
* [x] Ability to 'flag' a cell with a question mark or red flag
* [x] Detect when game is over
* [x] Persistence
* [x] Time tracking
* [x] Ability to start a new game and preserve/resume the old ones
* [x] Ability to select the game parameters: number of rows, columns, and mines
* [] Ability to support multiple users/accounts

The ability to support multiple user/accounts could not be developed in the given time but the idea was to create a account service which allows people to register and it will return a [JWT](https://jwt.io) that has to be send as a 'Authorization' header in every request. Then a filter will check it in every call and it will only give you access to the resources that you own.
