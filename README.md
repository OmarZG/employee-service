<p float="left">
  <img src="src/main/resources/images/springboot.png" width="100" />
  <img src="src/main/resources/images/jwt.png" width="100" />
  <img src="src/main/resources/images/mysql.png" width="100" />
  <img src="src/main/resources/images/docker.png" width="100" />
</p>

# 🧑‍💼 EmployeeService - CRUD API

### 🛠️ Technologies
- **Spring Boot 3**
- **MySQL**
- **Docker** (for MySQL container)
- **JWT (Future implementation)**
- **Hexagonal Architecture**
- **JDK 21**
- **JUnit & Integration Testing**
- **Postman**

---

## 📋 Project Overview

This project is a RESTful API for managing employee data, built with **Spring Boot** and **MySQL**. It follows clean architecture principles (hexagonal architecture) and supports standard CRUD operations.

---
### ✨ Features

- ✅ Create new employees
- 📄 Retrieve a list of employees
- ✏️ Update existing employee records
- ❌ Delete employees by ID
- 🧪 Tested with JUnit and Postman
- 🚀 Ready for future enhancements (e.g., JWT security, Swagger UI)


---

## 🛳️ Dockerized MySQL Database

The application uses a **MySQL database running inside a Docker container**, defined in the `docker-compose.yml` file for easy setup and portability.

### ⚙️ Docker Configuration

```yaml
services:
  mysql:
    image: mysql:latest
    container_name: my_mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: sasa1234
      MYSQL_DATABASE: employee_db
    ports:
      - "3306:3306"
    volumes:
      - ./db/mysql_data:/var/lib/mysql
```
### ▶️ How to Start the Container

Make sure [Docker](https://www.docker.com/) is installed and running. Then launch the MySQL container with:

```bash
docker-compose up -d
```

---
## 🔗 API Endpoints

| Method   | Endpoint                | Description                  |
|----------|-------------------------|------------------------------|
| `GET`    | `/api/employees`        | Get all employees            |
| `POST`   | `/api/employees`        | Create a new employee        |
| `POST`   | `/api/employees/bulk`   | Create multiple employees    |
| `PUT`    | `/api/employees/{id}`   | Update an existing employee  |
| `DELETE` | `/api/employees/{id}`   | Delete employee by ID        |

### 📨 Sample `POST` Request (Single Employee)

```json
{
  "firstName": "Jose",
  "secondName": "Julian",
  "paternalLastName": "Trejo",
  "maternalLastName": "Torrez",
  "age": 36,
  "sex": "M",
  "dateOfBirth": "1985-10-20",
  "position": "Developer"
}
```
### Swagger summary 
- http://localhost:8080/swagger-ui/index.html

![Phot](src/main/resources/screenShotProject/swagger_summary.png)


### 📋 Postman Tests by Operation

**GET all employees**  
![GET all](src/main/resources/imagesPostman/get_all.png)

**POST create employee**  
![POST create](src/main/resources/imagesPostman/post.png)

**POST bulk create employees**  
![POST bulk create](src/main/resources/imagesPostman/post_bulk.png)

**PUT update employee**  
![PUT update](src/main/resources/imagesPostman/put.png)

**DELETE employee**  
![DELETE](src/main/resources/imagesPostman/delete.png)

### 🗄️ Database Query Evidence with DBeaver

**SELECT all employees from the database**  
![DBeaver SELECT](src/main/resources/screenShotProject/dbeaver_select_employees.png)

This screenshot shows the result of a `SELECT * FROM employee_db.employees` query executed in DBeaver, verifying the data stored in the MySQL database after API operations.

### 🧪 Testing Evidence

<table>
   <tr>
      <td><p>Service Test</p></td>
      <td><p>Controller Test</p></td>
   </tr>
   <tr>
      <td>
         <img src="src/main/resources/imagesTest/employeeServiceImplTest.png" />
      </td>
      <td>
         <img src="src/main/resources/imagesTest/employeeControllerTest.png" />
      </td>
   </tr>
   <tr>
      <td colspan="2"><p>All Test</p></td>
   </tr>
   <tr>
      <td colspan="2">
         <img src="src/main/resources/imagesTest/allTests.png" />
      </td>
   </tr>
</table>

