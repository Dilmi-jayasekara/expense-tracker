# Expense Tracker API

A secure, RESTful backend API for tracking personal expenses, built with Java and Spring Boot. 

## Live Demo
The API is deployed live at: **https://expense-tracker-backend.hostless.app**

*Note: To test the live API, you can download the Postman collection included in this repository. Opening the URL directly in a browser will return a `403 Forbidden` as the API requires JWT authentication.*

## Tech Stack
- **Framework:** Java 17, Spring Boot 3
- **Database:** PostgreSQL (hosted on Neon)
- **Security:** Spring Security, JWT (JSON Web Tokens), BCrypt password hashing
- **Deployment:** Docker, Hostless

## Features
- **User Authentication:** Stateless JWT-based register and login endpoints.
- **Expense Management:** Protected CRUD endpoints to add and view expenses.
- **Analytics:** Custom JPQL aggregation query to group expenses by category and month.

## How to Test
1. Import the included `Expense_Tracker.postman_collection.json` into Postman.
2. Run the **Register** or **Login** request to receive your JWT.
3. Paste the JWT into the Bearer Token field of the other requests.
4. Test the live endpoints!
