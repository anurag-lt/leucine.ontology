package model;

import java.sql.Timestamp;

public class EditHistory {

    /**
     * The unique identifier for each edit history record.
     */
    private int id;

    /**
     * The object type that is being edited.
     */
    private ObjectType objectType;

    /**
     * The property of the object type that was edited.
     */
    private Property property;

    /**
     * The user who made the edit.
     */
    private int userId;

    /**
     * A narrative description of what was changed during the edit.
     */
    private String changeDescription;

    /**
     * The exact time when the edit was made.
     */
    private Timestamp editTimestamp;

    /**
     * Categorizes the edit into specific action types.
     */
    private ActionTypes actionType;

    public EditHistory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getChangeDescription() {
        return changeDescription;
    }

    public void setChangeDescription(String changeDescription) {
        this.changeDescription = changeDescription;
    }

    public Timestamp getEditTimestamp() {
        return editTimestamp;
    }

    public void setEditTimestamp(Timestamp editTimestamp) {
        this.editTimestamp = editTimestamp;
    }

    public ActionTypes getActionType() {
        return actionType;
    }

    public void setActionType(ActionTypes actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return "EditHistory{" +
                "id=" + id +
                ", objectType=" + objectType +
                ", property=" + property +
                ", userId=" + userId +
                ", changeDescription='" + changeDescription + '\'' +
                ", editTimestamp=" + editTimestamp +
                ", actionType=" + actionType +
                '}';
    }

    public enum ActionTypes {
        LOGIN, DATA_MODIFICATION, SYSTEM_ERROR, USER_CREATION, USER_DELETION, ACCESS_GRANTED, ACCESS_DENIED, CONFIGURATION_CHANGE, DOCUMENT_UPLOAD, DOCUMENT_DELETION
    }
}