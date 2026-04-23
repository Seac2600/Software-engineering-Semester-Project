package models;

public class PatientChartEntry {
    private int id;
    private int patientId;
    private String entryType;   // NOTE, IMAGE, FILE
    private String title;
    private String description;
    private String filePath;
    private String createdAt;

    public PatientChartEntry() {}

    public PatientChartEntry(int id, int patientId, String entryType, String title,
                             String description, String filePath, String createdAt) {
        this.id = id;
        this.patientId = patientId;
        this.entryType = entryType;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getEntryType() { return entryType; }
    public void setEntryType(String entryType) { this.entryType = entryType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
