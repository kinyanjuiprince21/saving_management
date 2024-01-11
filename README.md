# Savings Management System

This Spring Boot project implements a simple API for managing savings transactions and products. The system allows tracking customers, savings transactions, and multiple savings products.

## Table of Contents

- [Requirements](#requirements)
- [Test Steps](#test-steps)
- [Project Structure](#project-structure)
- [Setup and Configuration](#setup-and-configuration)
- [API Endpoints](#api-endpoints)
- [Database Configuration](#database-configuration)
- [Error Handling and Validation](#error-handling-and-validation)
- [Unit Tests](#unit-tests)
- [API Documentation](#api-documentation)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

## Requirements

The project aims to fulfill the following requirements:

1. Implementing endpoints for managing customers, including their bio data.
2. Implementing endpoints for recording savings transactions per customer.
3. Implementing endpoints to track total savings amount for each customer.
4. Implementing endpoints to track total savings amount received across all users.
5. Implementing endpoints for creating and managing multiple savings products.

## Test Steps

1. Set up a Spring Boot project.
2. Define necessary data models for customers, transactions, and savings products.
3. Implement controllers and endpoints for each required functionality.
4. Configure the appropriate database for storing data.
5. Implement the necessary service layer to handle business logic.
6. Ensure proper error handling and validation of incoming requests.
7. Implement unit tests to validate API functionality.
8. Document the APIs using both Postman and Swagger, providing clear descriptions and examples for each endpoint.

## Project Structure

The project follows a standard Spring Boot project structure:

```plaintext
savings-management/
|-- src/
|   |-- main/
|       |-- java/
|           |-- com/
|               |-- presta/
|                   |-- saving_management/
|                       |-- controllers/
|                       |-- dto/
|                       |-- error/
|                       |-- 
|                       |-- models/
|                       |-- repositories/
|                       |-- services/
|                       |-- SavingsManagementApplication.java
|       |-- resources/
|           |-- apis/
|           |-- json/
|           |-- application.properties
|-- src/
|   |-- test/
|       |-- java/
|           |-- com/
|               |-- presta/
|                   |-- saving_management/
|                       |-- CustomeControllerTest.java
|                       |-- SavingControllerTest.java
|                       |-- SavingsManagementApplicationTests.java
|                       |-- TransactionControllerTest.java
|-- HELP.md
|-- LICENSE
|-- mvnw
|-- mvnw.cmd
|-- pom.xml
|-- README.md
```

## Setup and Configuration

1. Clone the repository:

   ```bash
   git clone https://github.com/kinyanjuiprince21/saving_management.git
   ```

2. Open the project in your preferred IDE.

3. Configure the database connection in `application.properties`.

4. Build and run the project.

## API Endpoints

### Customers

- `GET /v1/customers`: Get a list of all customers.
- `POST /v1/customer/save`: Create a new customer.


### Transactions

- `GET /v1/transactions?customerId`: Get a list of all transactions for a customer.
- `POST /v1/transactions/save`: Record a new savings transaction for a customer.

### Savings

- `POST /v1/saving/save`: Create a new savings product.
- `GET /v1/customer/savings?customerId`: Get total savings amount for a specific customer.
- `GET /v1/customers/savings`: Get total savings amount received across all users.

## Database Configuration

The project uses MySQL as the database. Configure the database connection in `application.properties`.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/{databaseName}
spring.datasource.username={yourUsername}
spring.datasource.password={yourPassword}
```

## Error Handling and Validation

The API handles errors gracefully and provides meaningful error messages in case of invalid requests or server errors. Validation is implemented to ensure data integrity.

## Unit Tests

The project includes comprehensive unit tests covering the functionality of each endpoint. Run tests using JUnit.

## API Documentation

API documentation is provided using both Postman and Swagger. Explore the API using the provided collections and Swagger UI.

- [Postman Collection](https://github.com/kinyanjuiprince21/saving_management/blob/30baa2b0b1ded8f297f4cf4c8495cf92cb42d6c9/src/main/resources/json)
- [Swagger](https://github.com/kinyanjuiprince21/saving_management/blob/30baa2b0b1ded8f297f4cf4c8495cf92cb42d6c9/src/main/resources/apis)

## Deployment

Follow standard Spring Boot deployment practices for deploying the application to production.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvement, please open an issue or submit a pull request.

## License

This project is licensed under my License - see the [LICENSE](LICENSE) file for details.