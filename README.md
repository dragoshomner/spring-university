# Spring Ticketing Project for University 

This project is a REST API built in String Boot using Controller-Service-Repository layers and providing a way to clients to view travels, find routes between two cities, check trains disponibility and buy tickets. Being mainly built for educational purpuses, during the implementation I mostly focused on scalable design patterns, testing, code readability and learning / using the newest technologies.

During this project I used:
- Authentication and authorization - two main roles (client and admin) using String Security JWT
- Controllers, Services and Repositories - using Dependency Injection
- Entities, DTOs and Mappers - using MapStruct library
- CRUD implementations - for main entities
- Pagination - for getting more data in a structured way
- Custom Exceptions - for a better understanding of the error
- Database initializers - using factory pattern for adding data on startup to database (algorithmically, reading from CSV, using SQL procedures, Bing requests)
- MySQL - with the below schema structure and procedures for initializing travel table
- OpenCsv - for initializing cities table
- Bing API - for getting distances between cities and initializing route table
- jUnit and Mockito - for unit-testing
- Swagger - for a better and professional API documentation
- Logging - for debugging 

## Database Diagram

![Database Diagram](/readme-resources/spring_project_diagram.png)
