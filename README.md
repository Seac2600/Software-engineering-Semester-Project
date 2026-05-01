# Software-engineering-Semester-Project

## Project Overview

This project is a Dental Office Management System developed using Java and MySQL. 
It provides role-based access for administrators, receptionists, and dentists, allowing 
users to manage patient records, appointments, and staff accounts.

The system follows a layered architecture and was developed using an Agile approach 
with iterative sprints.

## Requirments 

   java JDK (version 17 or higher recommended )
   VS Code ( or any Java IDE)
   MySQL Server
   MySQL WorkBench

## Project Structure

 src/ → Contains all Java source code
 models/ → Data models (User, Patient, etc.)
 services/ → Business logic and system functionality
 dao/ → Database access (UserDAO, etc.)
 ui/ → User interface (Swing screens)
 lib/ → External libraries (MySQL connector)
 sql/ → Database setup files (schema.sql, sample_data.sql)

## Features

- Role-based login (Admin, Dentist, Receptionist)
- Patient record management
- Appointment scheduling
- Forgot password functionality
- Database persistence using MySQL

 ## How to Run

1. Complete VS Code setup (if needed)
2. Set up MySQL database using schema.sql and sample_data.sql
3. Update database credentials in DataConnection.java
4. Run main.java



# VSCODE set up if needed

if the code is not running or this if your first time using vscode 
follow theses instructions 

# make sure you have these extenstions downloaded 

1. open VS Code and click on the 4 sqaures tab on the left 

2. search for theses extentions in the search bar 

   Debugger for java
   Extension Pack for Java
   Maven For Java
   Project Manager for Java
   Test Runner for Java

3. click on each extension and press download 

### 1. open VsCode and project 

1. open VS code, Click the explore on the left 

2. scroll down till you see open folder and click on it 

3. find the project folder and select it and press open 
   make sure its unzipped. 

### 2. adding the library to the project in case it doesn't work 
1. go to this website https://dev.mysql.com/downloads/connector/j/

2. click on platform independent on the select operating system button

3. download the zip archive file 

4. unzip that file and open it till you see the .jar file

5. paste the .jar file into the lib section of the project in vs code 

6. on VS code press crl + shift + p 

7. type >java: configure classpath  in the search bar 

8. click on libraries in the project settings 

9. copy and paste jar file there as well 


# Once you have done that make sure you do the MySQL set up then run the code 
   


## Database Setup Instructions (MySQL Workbench)

Follow these steps to set up the database for the Dental Office System.

### 1. Open MySQL Workbench

* Launch **MySQL Workbench**
* Click on your connection (typically **Local instance MySQL80**)

---

### 2. Run the Database Schema

1. In the top menu, click:

   ```
   File → Open SQL Script
   ```

2. Navigate to the project folder 

   ```
   Software-engineering-Semester-Project\sql
   ```

3. Select:

   ```
   schema.sql
   ```

4. Click **Open**

5. Once the file opens, click the **lightning bolt (⚡) icon** at the top toolbar:

   * Select **Execute (All or Selection)**
   * Or use shortcut:

     ```
     Ctrl + Shift + Enter
     ```

6. Make sure you do steps 3-4 with all SQL files 
---

### 3. Verify the Database Was Created

1. On the left panel (Navigator):

   * Right click **Schemas**
   * Click **Refresh All**

2. You should now see:

   ```
   dental_office_db
   ```

3. Expand it:

   ```
   dental_office_db → Tables
   ```

4. Confirm the following tables exist:

   * roles
   * users

---

### 4. Insert Sample Data

1. Again, click:

   ```
   File → Open SQL Script
   ```

2. Open:

   ```
   sample_data.sql
   ```

3. Click the **⚡ Execute button** (or press Ctrl + Shift + Enter)

---

### 5. Verify Data Was Inserted

Open a new SQL tab and run:

```sql
USE dental_office_db;

SELECT * FROM roles;
SELECT * FROM users;
```

Click ⚡ to execute.

You should see:

* Roles such as ADMIN, DENTIST, RECEPTIONIST
* A test user account

---

### 6. Update Database Connection in Java

Open `DataConnection.java` and update your MySQL credentials:

```java
String url = "jdbc:mysql://localhost:3306/dental_office_db";
String user = "your_mysql_username";
String password = "your_mysql_password";
```

---

### 7. Run the Application

Run:

```
main.java
```

---

### Test Login

Use the following credentials:

```
Email: Edward@mail.com
Password: Edward123
```

---

### Common Issues

* **Database not showing**

  * Right click **Schemas → Refresh All**

* **SQL did not run**

  * Make sure you clicked the correct ⚡ button (**Execute All**)

* **Connection errors in Java**

  * Check username/password in `DatabaseConnection.java`
  * Make sure MySQL server is running

---

### Notes

* You only need to run `schema.sql` and `sample_data.sql` **once**
* This setup allows all team members to recreate the same database environment locally
