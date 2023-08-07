# Web-application for librarians to book management tasks

## What is it?

This is a REST API designed to simplify the book management tasks of librarians. This system streamlines a variety of
librarian tasks, including creating, editing, and deleting book records, registering new customers, modifying customer
information, and removing customer profiles. Additionally, librarians can lend books to customers and track the return
of books.

## URIs

| HTTP Method | URI                    | Description                |
|-------------|------------------------|----------------------------|
| GET         | api/books              | Get all books              |
| GET         | api/books/{id}         | Get a book by id           |
| POST        | api/books              | Create a new book          |
| PATCH       | api/books/{id}         | Update a book by id        |
| PATCH       | api/books/{id}/assign  | Assign a book to person    |
| PATCH       | api/books/{id}/release | Release a book from person |
| DELETE      | api/books/{id}         | Delete a book by id        |
| GET         | api/people             | Get all people             |
| GET         | api/people/{id}        | Get a person by id         |
| POST        | api/people             | Create a new person        |
| PATCH       | api/people/{id}        | Update a person by id      |
| DELETE      | api/people/{id}        | Delete a person by id      |

## How to run?

**Requirements**

- Docker
- Git

If you don't have Docker, you can download it [here](https://www.docker.com/products/docker-desktop).

<details>
  <summary>In case you don't want to use Docker, check out instructions under this spoiler.
</summary>

**Requirements**

- JDK 17 or newer
- Maven
- PostgreSQL
- Git

### Steps

**1. Clone the repository and navigate to the project directory**

```bash
$ git clone https://github.com/Kidchai/library-management-system.git
$ cd library-management-system
```

**2. Create a database in PostgreSQL**

```sql
CREATE DATABASE your_database_name;
```

**3. Set up the connection to the PostgreSQL database in the *application.properties* file.**

Navigate to the *application.properties* file located in the *src/main/resources* directory and update the following
properties:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library-db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Replace *library-db*, *postgres*, and *postgres* with the database name, username, and password.

**4. Install dependencies and build the project**

```bash
$ mvn clean install
```

**5. Run the application**

```bash
$ mvn spring-boot:run
```

Now you need just open your web browser and navigate to http://localhost:8080/api/books or http://localhost:8080/api/people.

If you want to run unit tests, run the:

```bash
$ mvn test
```

</details>

**1. Clone the repository and navigate to the project directory**

```bash
$ git clone https://github.com/Kidchai/library-management-system.git
$ cd library-management-system
```

**2. Start the application and database using Docker**

```bash
$ docker-compose up
```

## What about other versions?

If you're interested in seeing how my application has evolved over time, you can check out the other branches, which
include:

- [Spring MVC + JDBC](https://github.com/Kidchai/LibraryManagementSystem/tree/Spring_MVC+JDBC)
- [Spring MVC + Hibernate](https://github.com/Kidchai/LibraryManagementSystem/tree/Spring_MVC+Hibernate)
- [Spring MVC + Hibernate + Spring Data JPA](https://github.com/Kidchai/LibraryManagementSystem/tree/Spring_MVC+Hibernate+Spring_Data_Jpa)
- [Spring Boot](https://github.com/Kidchai/library-management-system/tree/SpringBoot)