package model;

import java.sql.Timestamp;

/**
 * Represents an edit history record within the system, capturing details about edits made to various object types.
 */
public class EditHistories {

    /**
     * Unique identifier for the edit history record.
     */
    private int id;

    /**
     * The object type that the edit history pertains to.
     */
    private ObjectTypes objectType;

    /**
     * The ID of the user who made the edit.
     */
    private int userId;

    /**
     * A textual description of the edit made.
     */
    private String description;

    /**
     * The timestamp when the edit was made.
     */
    private Timestamp timestamp;

    // Getters

    public int getId() {
        return id;
    }

    public ObjectTypes getObjectType() {
        return objectType;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setObjectType(ObjectTypes objectType) {
        this.objectType = objectType;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    // toString method

    @Override
    public String toString() {
        return "EditHistories{" +
                "id=" + id +
                ", objectType=" + objectType +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}