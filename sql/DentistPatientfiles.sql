use dental_office_db;

CREATE TABLE IF NOT EXISTS patient_chart_entries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    entry_type VARCHAR(30) NOT NULL,   -- NOTE, IMAGE, FILE
    title VARCHAR(100),
    description TEXT,
    file_path VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);