package model;

import java.sql.Timestamp;

/**
 * Represents an instance of a relationship between objects within the system.
 */
public class RelationInstances {

    private int id;
    private Timestamp timestamp;
    private ObjectTypeEnum status; // Assuming the creation of the enum based on your object_type_enum
    private String description;
    private RelationTypes fkRelationTypeID;
    private ObjectTypes fkSourceObjectID;
    private ObjectTypes fkTargetObjectID;

    public RelationInstances() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public ObjectTypeEnum getStatus() {
        return status;
    }

    public void setStatus(ObjectTypeEnum status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RelationTypes getFkRelationTypeID() {
        return fkRelationTypeID;
    }

    public void setFkRelationTypeID(RelationTypes fkRelationTypeID) {
        this.fkRelationTypeID = fkRelationTypeID;
    }

    public ObjectTypes getFkSourceObjectID() {
        return fkSourceObjectID;
    }

    public void setFkSourceObjectID(ObjectTypes fkSourceObjectID) {
        this.fkSourceObjectID = fkSourceObjectID;
    }

    public ObjectTypes getFkTargetObjectID() {
        return fkTargetObjectID;
    }

    public void setFkTargetObjectID(ObjectTypes fkTargetObjectID) {
        this.fkTargetObjectID = fkTargetObjectID;
    }

    @Override
    public String toString() {
        return "RelationInstances{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", fkRelationTypeID=" + fkRelationTypeID +
                ", fkSourceObjectID=" + fkSourceObjectID +
                ", fkTargetObjectID=" + fkTargetObjectID +
                '}';
    }

    /**
     * Enumeration for object type statuses.
     */
    public enum ObjectTypeEnum {
        ACTIONS, BUSINESS_RULES, DOCUMENTS, EDIT_HISTORIES, FACILITIES, OBJECT_TYPES, PERMISSIONS, PROPERTIES, RELATION_CONSTRAINTS, RELATION_INSTANCES, RELATION_TYPES, ROLES, USER_ROLES
    }
}