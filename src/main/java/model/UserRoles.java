package model;

import java.sql.Timestamp;

/**
 * Represents a user role within the system, defining the responsibilities, access levels, and status of a role associated with a user.
 */
public class UserRoles {
    
    /**
     * Unique identifier for the user role.
     */
    private int id;
    
    /**
     * Name of the user role, such as 'Ontology Manager' or 'Data Analyst'.
     */
    private String roleName;
    
    /**
     * Description of the user role, outlining expectations, responsibilities, and limitations.
     */
    private String description;
    
    /**
     * Timestamp indicating when the user role was created.
     */
    private Timestamp createdAt;
    
    /**
     * Timestamp denoting the most recent modification to the user role.
     */
    private Timestamp modifiedAt;
    
    /**
     * Status of the user role, indicating whether it is active, expired, under review, pending approval, or archived.
     */
    private DocumentStatus status;
    
    /**
     * Permissions associated with the user role, facilitating dynamic access control.
     */
    private Permissions fkPermissionsId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public Permissions getFkPermissionsId() {
        return fkPermissionsId;
    }

    public void setFkPermissionsId(Permissions fkPermissionsId) {
        this.fkPermissionsId = fkPermissionsId;
    }

    // toString Method
    @Override
    public String toString() {
        return "UserRoles{" +
               "id=" + id +
               ", roleName='" + roleName + '\'' +
               ", description='" + description + '\'' +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               ", status=" + status +
               ", fkPermissionsId=" + fkPermissionsId +
               '}';
    }

    /**
     * Represents the various statuses a user role can have.
     */
    public enum DocumentStatus {
        ACTIVE, EXPIRED, UNDER_REVIEW, PENDING_APPROVAL, ARCHIVED
    }
}