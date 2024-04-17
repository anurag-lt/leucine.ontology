package model;

import java.sql.Timestamp;

/**
 * This class represents the 'actions' table as an object within the system. It encapsulates all the fields of an action, along with its related object type, to provide a straightforward way to manipulate and track automated actions.
 */
public class Actions {
    // Primary key for the 'actions' table.
    private int id;

    // Stores the name of the action.
    private String name;

    // A brief summary of what the action does.
    private String description;

    // Defines the category of the action performed.
    private ActionTypes actionType;

    // Specifies the event that triggers the action.
    private String triggerEvent;

    // Records the exact time an action was executed.
    private Timestamp executionTime;

    // The date and time when the action was created in the system.
    private Timestamp createdAt;

    // Indicates the last time the action definition was modified.
    private Timestamp lastModifiedAt;

    // This relation is utilized to recognize the specific object types that an action can apply to.
    private ObjectTypes fkObjectType;

    /**
     * Enum for the action types corresponding to the 'action_types' enum in the database.
     */
    public enum ActionTypes {
        LOGIN, DATA_MODIFICATION, SYSTEM_ERROR, USER_CREATION, USER_DELETION, ACCESS_GRANTED, ACCESS_DENIED, CONFIGURATION_CHANGE, DOCUMENT_UPLOAD, DOCUMENT_DELETION
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActionTypes getActionType() {
        return actionType;
    }

    public void setActionType(ActionTypes actionType) {
        this.actionType = actionType;
    }

    public String getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(String triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    public Timestamp getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Timestamp executionTime) {
        this.executionTime = executionTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Timestamp lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public ObjectTypes getFkObjectType() {
        return fkObjectType;
    }

    public void setFkObjectType(ObjectTypes fkObjectType) {
        this.fkObjectType = fkObjectType;
    }

    @Override
    public String toString() {
        return "Actions{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", actionType=" + actionType +
                ", triggerEvent='" + triggerEvent + '\'' +
                ", executionTime=" + executionTime +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", fkObjectType=" + fkObjectType +
                '}';
    }
}