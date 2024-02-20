# Wishlist Management Spring Boot Application

This Spring Boot application is designed to manage wishlists for users. It provides functionality for user authentication, registration, and allows logged-in users to view, add, and delete items from their own wishlist database.

## Features

- User Authentication: Users can securely log in to the application.
- User Registration: New users can create an account with a unique username and password.
- Wishlist Management: Logged-in users can view, add, and delete items from their own wishlist.

## Requirements

- Java Version 17.0+
- Java IDE with Maven Support (eg. IntelliJ, Eclipse)
- PgAdmin (Postgresql) - Database
- PostMan ( Testing API)

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- Postgresql

## Setup Instructions

1. Exract the Zip file and Open the folder in Java IDE(IntelliJ).
2. **Database Configuration:**

- Install and configure Postgresql.
- Update `application.properties` with your database credentials.
- spring.datasource.url = jdbc:postgresql://localhost:5432/--Mention Your Database Name here--
- spring.datasource.username = Your userName
- spring.datasource.password = Your Password

3. **Build and Run the Application:**
4. **Access the Application:**
   Open your web browser and go to `http://localhost:8081/api/___`.
5. Testing codes provided in the application test folder.
6. Import (Wishlist Management.postman_collection.json) to get API end point collection requests. (optional)

## Endpoints

1. Register User : `http://localhost:8081/api/auth/register` (POST)
   - {"name" : "vishnu", "email" : "vishnu@xindus.com, "gender" : "male", "password" : "vishnu%990"} - RequestBody
2. Login Existing User : `http://localhost:8081/api/auth/authenticate` (POST)
   - {"email" : "vishnu@xindus.com, "password" : "vishnu%990"} - RequestBody
3. Show Wishlist : `http://localhost:8081/api/Wishlist/` (GET, BearerToken required which is generated on successful login)
4. Add Wishlist Item: `http://localhost:8081/api/Wishlist/add` (POST, BearerToken required which is generated on successful login)
5. Delete Wishlist Item : `http://localhost:8081/api/Wishlist/{id of wishlist item}` (DELETE, BearerToken required which is generated on successful login)

## Usage

1. **User Registration:**

- Navigate to the registration endpoint.
- Send the required details (name, email, gender, password) as JSON Object.

2. **User Login:**

- Navigate to the login endpoint.
- Send the required details as JSON Object.

3. **View Wishlist:**

- Once logged in, Jwt Token will be generated.
- Use the Jwt-Token to access the current user Wishlist.

4. **Add Item to Wishlist:**

- Send the the details of the item (name, description) as JSON Object.
- Authentication Required (Beared Token)

5. **Delete Item from Wishlist:**

- Send the Id of the wishlist item of the current user in to delete the Wishlist item in DELETE API.
- Authentication Required (Beared Token)
