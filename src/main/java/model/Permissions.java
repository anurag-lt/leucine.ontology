package model;

import java.sql.Timestamp;

public class Permissions {

    /**
     * Primary key for the permissions table. It uniquely identifies each permission record, facilitating easy indexing and retrieval, essential for managing access controls and ensuring efficient data operations.
     */
    private Integer id;

    /**
     * Defines the name of the permission, such as 'edit_type' or 'view_document'. Clear naming helps in understanding the scope and application of the permission within the system, aiding in role-based access control and security auditing.
     * This is a task-level parameter essential for granular access control.
     */
    private String permissionName;

    /**
     * Categorizes the permission based on predefined action types. This enum-based approach standardizes the types of actions controlled by permissions, facilitating systematic implementation and auditing of security
     * policies across the application, ensuring consistency and clarity in permission management.
     */
    private ActionTypes permissionType;

    /**
     * Associates the permission with a specific application module, such as 'property_management' or 'customer_onboarding'. This categorization aids in contextualizing permissions within specific functionality areas,
     * enhancing system administration by allowing for module-specific access control settings and reporting.
     */
    private Modules module;

    /**
     * Provides detailed information about what the permission allows a role to perform. Descriptions support admins and managers in understanding the implications of granting a permission, essential for informed security
     * management and role configuration. This is a process-level parameter, crucial for operational transparency.
     */
    private String description;

    /**
     * Timestamp for when the permission record was created. This field is vital for tracking the evolution of access controls within the app, supporting audits, and providing temporal insights into permission changes,
     * facilitating compliance and historical analysis.
     */
    private Timestamp createdAt;

    /**
     * Indicates the most recent timestamp the permission was updated. Tracking modifications is crucial for audit trails, version control, and understanding the dynamic nature of permission configurations over time,
     * contributing to effective security governance.
     */
    private Timestamp updatedAt;

    // Enums for ActionTypes and Modules as assumed based on provided DDL and instructions.
    public enum ActionTypes {
        LOGIN, DATA_MODIFICATION, SYSTEM_ERROR, USER_CREATION, USER_DELETION, ACCESS_GRANTED, ACCESS_DENIED, CONFIGURATION_CHANGE, DOCUMENT_UPLOAD, DOCUMENT_DELETION
    }

    public enum Modules {
        OBJECT_TYPE_MANAGEMENT, PROPERTY_MANAGEMENT, RELATION_TYPE_MANAGEMENT, ACTION_MANAGEMENT_MODULE, AUDIT_LOGS_MODULE, CUSTOMER_ONBOARDING
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public ActionTypes getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(ActionTypes permissionType) {
        this.permissionType = permissionType;
    }

    public Modules getModule() {
        return module;
    }

    public void setModule(Modules module) {
        this.module = module;
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // toString method for debugging and logging purposes
    @Override
    public String toString() {
        return "Permissions{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                ", permissionType=" + permissionType +
                ", module=" + module +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}