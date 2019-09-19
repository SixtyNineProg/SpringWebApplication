# User service

## Description
Microservice for working with user

## REST-services:
        
### POST [http://localhost:9000/webapplication/user](http://localhost:9000/webapplication/user)
##### user registration
    
        Example of request body:
        {
          "id": "1",
          "name": "Dima",
          "email": "dima@mail.ru",
          "password": "123456789",
          "age": "12"
        }
        
---

### PUT [http://localhost:9000/webapplication/user/updateUser](http://localhost:9000/webapplication/user)
##### User update

        Example of request body:
        {
          "id": "{userId}",  
          "name": "Dimasik",
          "email": "dima@mail.ru",
          "password": "123456789",
          "age": "12"
        }
        
        where:
        {userId} - identity number
---
      
### DELETE [http://localhost:9000/webapplication/user/{userId}](http://localhost:9000/webapplication/user/{userId})
##### deleting user by id

        where:
        {userId} - identity number

---

### GET [http://localhost:9000/webapplication/user/{userId}](http://localhost:9000/webapplication/user/{userId})
##### getting user by id

        where:
        {userId} - identity number

---
        
### GET [http://localhost:9000/webapplication/user/getAll](http://localhost:9000/webapplication/user/getAll)
##### getting all users

---

### GET [http://localhost:9000/webapplication/user?pagesize={pagesize}&pagenumber={pagenumber}](http://localhost:9000//webapplication/user?pagesize={pagesize}&pagenumber={pagenumber})
##### receiving history

        where:
        {pagesize} - number of elements per page (optional parameter, default 5)
        {pagenumber} - page number (optional parameter, default 1)


###Build tool        
This projec uses such build tool as gradle. 
Use "gradlew build" to build project.
Use "gradlew bootRun" to run project.