# EPIC ENERGY SERVICES

## Introduction
This is an application to manage a Customer with Invoice, Adress, Municipality and Province.

## Tech stack
- Java + Spring Boot
- Thymeleaf
- JUnit
- Eclipse
- Postman
- Swagger
- Postgres
- Gihub.

## Remote API
I chose `Heroku` for the CI/CD service directly connected to the main branch of my repository.
https://epicenergyservicesanaclerio.herokuapp.com/

## Postman
JSON link of postman:	https://www.getpostman.com/collections/0a14b41dbcf260bd368b

## Back-end
Authentication: method POST, PUT and DELETE are allowed only for role ADMIN
				method GET are allowed for role ADMIN and USER;
				
When you add a Customer you can add an Invoice on cascade, while the Adress must exist;
when you add a Invoice, the Customer must exist.

Dynamic calculation of annual turnover.

## Front-end
- Front-end in thymeleaf
- Authentication flow

## Main Features implemented
- Index with login/registration
- HomePage 
- View Custumers with delete, update, insert, sort and filter
- View Invoice with delete, update, insert, sort and filter

## Testing
For the test I used Junit, testing all the critical modules of the system

## License
MIT ©
