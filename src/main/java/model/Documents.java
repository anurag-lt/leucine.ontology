package model;

import java.util.Date;

public class Documents {
    
    /**
     * This is the primary key for the documents object type. It uniquely identifies each document stored within the system, facilitating easy retrieval, reference, and management of document records.
     */
    private int id;
    
    /**
     * The name of the document uploaded to the system. This parameter helps in identifying the document and allows users to search and refer to it more conveniently. It is crucial for report generation and auditing purposes, providing clear identification of each file.
     */
    private String documentName;
    
    /**
     * Specifies the type of document, such as 'license', 'certification', or 'other'. Knowing the type of document is important for categorization, retrieval, and ensuring compliance with legal and operational requirements. It aids in filtering documents during searches and report generation.
     */
    private DocumentType documentType;
    
    /**
     * The date when the document was uploaded to the system. This parameter is fundamental for tracking document submission timeliness and auditing purposes. It helps in verifying that documents were submitted within required deadlines and allows for effective document lifecycle management.
     */
    private Date uploadDate;
    
    /**
     * Denotes the expiration date of the document if applicable. This is essential for compliance purposes, ensuring that all documents on file are current and valid. Automated reminders and reports on expiring documents can be generated using this parameter, thus aiding in proactive compliance management.
     */
    private Date expiryDate;
    
    /**
     * Indicates the current status of the document, such as 'active', 'expired', or 'under_review'. This allows for effective management and tracking of the document's lifecycle. It is process-level information helpful in operational reporting and ensuring documents are reviewed and updated as needed.
     */
    private DocumentStatus documentStatus;
    
    /**
     * The path or URL where the document is stored. This facilitates direct access to the document file for viewing or downloading purposes. It's a critical aspect for document management systems, allowing users to locate and access documents efficiently.
     */
    private String filePath;
    
    /**
     * Indicates the facility to which a document belongs. Essential for grouping documents by facility, aiding in compliance and operational documentation management.
     */
    private Facilities fkFacilityId;

    // Enum for DocumentType
    public enum DocumentType {
        LICENSE, CERTIFICATION, REGISTRATION, COMPLIANCE_REPORT, OPERATIONAL_MANUAL, SAFETY_PROTOCOL, OTHER
    }

    // Enum for DocumentStatus
    public enum DocumentStatus {
        ACTIVE, EXPIRED, UNDER_REVIEW, PENDING_APPROVAL, ARCHIVED
    }

    // Getters and Setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Facilities getFkFacilityId() {
        return fkFacilityId;
    }

    public void setFkFacilityId(Facilities fkFacilityId) {
        this.fkFacilityId = fkFacilityId;
    }

    // toString Method
    @Override
    public String toString() {
        return "Documents{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", documentType=" + documentType +
                ", uploadDate=" + uploadDate +
                ", expiryDate=" + expiryDate +
                ", documentStatus=" + documentStatus +
                ", filePath='" + filePath + '\'' +
                ", fkFacilityId=" + fkFacilityId +
                '}';
    }
}