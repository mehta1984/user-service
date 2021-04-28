# user-service
User service API

### How to build and run application
- Download the source code from github and run following commands 

`mvn clean package`
`java jar users-0.0.1-SNAPSHOT.jar`

### Postman project is added into test resource folder
Readonly username - guest/guest1234 (Only access to GET API)
Admin user - admin/admin1234 (Can Perform Create and Update API)

## End points 
- Get User -GET - /users/{id}
- Create User - POST - /users
- Update User - PUT - /users/{id}

## Feature implemented
- H2 database been used 
- Spring-security for basic authentication 
- AOP logging exit and entry log
- Circuit breaker
- Pact testing 
- Unit testing and Integration Testing 