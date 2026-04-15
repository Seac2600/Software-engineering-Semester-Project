# Software-engineering-Semester-Project

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

2. Navigate to the project folder:

   ```
   project-root/sql/
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
