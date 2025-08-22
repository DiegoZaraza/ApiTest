# Pet Store API Test Automation

A comprehensive test automation framework for the Pet Store API using REST Assured, TestNG, and Allure reporting.

## 🚀 Features

- **Complete API Coverage**: Tests for Pet, Store, and User management endpoints
- **REST Assured Integration**: Powerful HTTP client for API testing
- **TestNG Framework**: Robust test execution with dependencies and priorities
- **Allure Reporting**: Beautiful and detailed test reports
- **Model-Based Testing**: POJO models for clean test data management
- **Lombok Integration**: Reduced boilerplate code with auto-generated getters/setters
- **Maven Build System**: Easy dependency management and test execution

## 📋 Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Git

## 🛠 Tech Stack

- **Java 11**: Programming language
- **Maven**: Build and dependency management
- **REST Assured 5.5.6**: API testing library
- **TestNG 7.11.0**: Testing framework
- **Allure 2.29.1**: Test reporting
- **Jackson 2.19.2**: JSON processing
- **Gson 2.13.1**: JSON serialization/deserialization
- **Lombok 1.18.38**: Code generation

## 📁 Project Structure

```
petstore-api-tests/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/apitest/
│       │       ├── base/
│       │       │   └── BaseTest.java           # Base test configuration
│       │       ├── models/
│       │       │   ├── ApiResponse.java        # API response model
│       │       │   ├── Category.java           # Pet category model
│       │       │   ├── Order.java              # Store order model
│       │       │   ├── Pet.java                # Pet model
│       │       │   ├── Tag.java                # Pet tag model
│       │       │   └── User.java               # User model
│       │       └── tets/
│       │           ├── PetApiTests.java        # Pet API test cases
│       │           ├── StoreApiTests.java      # Store API test cases
│       │           └── UserApiTests.java       # User API test cases
│       └── resources/
│           ├── allure.properties              # Allure configuration
│           └── testng.xml                     # TestNG suite configuration
├── .gitignore                                 # Git ignore rules
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

## 🎯 Test Coverage

### Pet API Tests
- ✅ Add new pet to the store
- ✅ Retrieve pet by ID
- ✅ Update existing pet
- ✅ Find pets by status
- ✅ Handle invalid pet ID requests
- ✅ Delete pet from store
- ✅ Validate error handling for invalid data
- ✅ Update pet using form data

### Store API Tests
- ✅ Place pet order
- ✅ Retrieve order by ID
- ✅ Get store inventory
- ✅ Handle invalid order ID requests
- ✅ Handle non-existent orders
- ✅ Validate invalid order data
- ✅ Test order with missing data
- ✅ Test negative quantity orders
- ✅ Verify inventory data structure
- ✅ Delete order

### User API Tests
- ✅ Create new user
- ✅ User login functionality
- ✅ User logout functionality
- ✅ Create multiple users with array
- ✅ Create multiple users with list
- ✅ Handle invalid user requests
- ✅ Delete user

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd petstore-api-tests
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Run Tests
```bash
# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=PetApiTests

# Run tests with specific TestNG suite
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### 4. Generate Allure Report
```bash
# Generate and serve Allure report
mvn allure:serve

# Generate Allure report only
mvn allure:report
```

## 📊 Test Execution Flow

Tests are executed with priorities to ensure proper dependencies:

1. **Pet API Tests**: Create → Read → Update → Delete operations
2. **Store API Tests**: Place order → Retrieve → Validate → Delete
3. **User API Tests**: Create → Login → Operations → Delete

## 🔧 Configuration

### Base Configuration
- **Base URL**: `https://petstore.swagger.io/v2`
- **Content Type**: `application/json`
- **Timeout**: 10 seconds (connection and socket)

### Test Data
- Predefined test IDs for consistent testing
- Reusable test data constants
- Model-based request/response handling

## 📈 Reporting

The project uses Allure for comprehensive test reporting:

- **Test Results**: Pass/Fail status with detailed steps
- **Request/Response Logging**: Complete API interaction details
- **Test Categories**: Organized by Epic, Feature, and Story
- **Severity Levels**: Critical, Normal, and Minor classifications
- **Execution Timeline**: Test execution duration and dependencies

## 🛡 Error Handling

The framework includes robust error handling for:
- Invalid API responses (4xx, 5xx status codes)
- Network timeouts and connection issues
- Malformed JSON data
- Missing required fields
- Invalid data types

## 📝 Best Practices

- **Page Object Model**: Clean separation of test logic and data
- **Test Independence**: Each test can run independently
- **Data Management**: Centralized test data configuration
- **Logging**: Comprehensive request/response logging
- **Assertions**: Meaningful and descriptive assertions
- **Error Handling**: Graceful handling of API failures

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🔗 API Documentation

For detailed API documentation, visit: [Petstore Swagger Documentation](https://petstore.swagger.io/)