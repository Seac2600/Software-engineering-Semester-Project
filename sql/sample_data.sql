USE dental_office_db;

-- Insert roles
INSERT INTO roles (role_name) VALUES 
('ADMIN'),
('DENTIST'),
('RECEPTIONIST');

-- Insert test admin user
INSERT INTO users (first_name, last_name, email, password, role_id)
VALUES ('Edward', 'Hernandez', 'Edward@mail.com', 'Edward123', 1);