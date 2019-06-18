# User service

## Description
Microservice for working with user

## REST-services:
        
#### POST [http://localhost:9000/webapplication/user](http://localhost:9000/WebApplication/user)
#### user registration
    
        Example of request body:
        {
          "name": "Dima",
          "email": "dima@mail.ru",
          "password": "123456789",
          "age": "12"
        }
#### POST [http://localhost:9000/webapplication/user/{userId]
#### deleting user by id

        where:
        {userId} - identity number

#### GET [http://localhost:9000/webapplication/user/{userId]
#### getting user by id

        where:
        {userId} - identity number

###Build tool        
This projec uses such build tool as gradle. Use "gradlew build" to build project