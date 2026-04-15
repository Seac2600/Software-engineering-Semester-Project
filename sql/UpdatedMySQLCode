CREATE DATABASE IF NOT EXISTS dental_office_db;
USE dental_office_db;

CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    date_of_birth DATE,
    address VARCHAR(150),
    notes TEXT
);

CREATE TABLE IF NOT EXISTS appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    staff_user_id INT,
    appointment_date DATE,
    appointment_time TIME,
    reason VARCHAR(150),
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (staff_user_id) REFERENCES users(id)
);

INSERT INTO roles (role_name)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'ADMIN');

INSERT INTO roles (role_name)
SELECT 'DENTIST'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'DENTIST');

INSERT INTO roles (role_name)
SELECT 'RECEPTIONIST'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'RECEPTIONIST');

INSERT INTO users (first_name, last_name, email, password, role_id)
SELECT 'Edward', 'Hernandez', 'Edward@mail.com', 'Edward123',
       (SELECT id FROM roles WHERE role_name = 'ADMIN')
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'Edward@mail.com'
);

INSERT INTO users (first_name, last_name, email, password, role_id)
SELECT 'Adam', 'Canedo', 'adam@mail.com', 'Adam456',
       (SELECT id FROM roles WHERE role_name = 'DENTIST')
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'adam@mail.com'
);

INSERT INTO users (first_name, last_name, email, password, role_id)
SELECT 'Elisha', 'Talavera', 'elisha@mail.com', 'Elisha789',
       (SELECT id FROM roles WHERE role_name = 'RECEPTIONIST')
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'elisha@mail.com'
);

INSERT INTO patients (first_name, last_name, phone, date_of_birth, address, notes)
SELECT 'Vladimir', 'Vuckovic', '432-555-1111', '1998-06-12', '123 Main St', 'Initial patient record'
WHERE NOT EXISTS (
    SELECT 1 FROM patients WHERE first_name = 'Vladimir' AND last_name = 'Vuckovic'
);

INSERT INTO patients (first_name, last_name, phone, date_of_birth, address, notes)
SELECT 'Yuan', 'Quan', '432-555-2222', '2001-03-22', '456 Oak Ave', 'Follow-up needed'
WHERE NOT EXISTS (
    SELECT 1 FROM patients WHERE first_name = 'Yuan' AND last_name = 'Quan'
);

INSERT INTO appointments (patient_id, staff_user_id, appointment_date, appointment_time, reason)
SELECT
    (SELECT id FROM patients WHERE first_name = 'Vladimir' AND last_name = 'Vuckovic' LIMIT 1),
    (SELECT id FROM users WHERE email = 'adam@mail.com' LIMIT 1),
    '2026-04-20',
    '09:00:00',
    'Dental Cleaning'
WHERE NOT EXISTS (
    SELECT 1
    FROM appointments
    WHERE appointment_date = '2026-04-20'
      AND appointment_time = '09:00:00'
      AND reason = 'Dental Cleaning'
);

INSERT INTO appointments (patient_id, staff_user_id, appointment_date, appointment_time, reason)
SELECT
    (SELECT id FROM patients WHERE first_name = 'Yuan' AND last_name = 'Quan' LIMIT 1),
    (SELECT id FROM users WHERE email = 'adam@mail.com' LIMIT 1),
    '2026-04-21',
    '11:30:00',
    'Cavity Check'
WHERE NOT EXISTS (
    SELECT 1
    FROM appointments
    WHERE appointment_date = '2026-04-21'
      AND appointment_time = '11:30:00'
      AND reason = 'Cavity Check'
);