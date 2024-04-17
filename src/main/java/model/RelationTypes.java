package model;

import java.sql.Timestamp;

/**
 * The RelationTypes class represents the types of relationships that can exist within the system.
 * It encompasses the details necessary to understand and manage these relation types effectively.
 */
public class RelationTypes {
    
    private int id;
    private String name;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private VisibilityStates visibilityState;
    private String relationConstraints;
    private ObjectTypes fkSourceObjectId;
    private ObjectTypes fkTargetObjectId;
    private RelationConstraints fkRelationConstraintsId;

    // Enum for visibility states
    public enum VisibilityStates {
        PUBLIC, PRIVATE, ARCHIVED
    }

    /**
     * Gets the unique identifier for the relation type.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the relation type.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the relation type.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the relation type.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the relation type.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the relation type.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the creation timestamp of the relation type.
     * @return the createdAt
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the relation type.
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the last updated timestamp of the relation type.
     * @return the updatedAt
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the last updated timestamp of the relation type.
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the visibility state of the relation type.
     * @return the visibilityState
     */
    public VisibilityStates getVisibilityState() {
        return visibilityState;
    }

    /**
     * Sets the visibility state of the relation type.
     * @param visibilityState the visibilityState to set
     */
    public void setVisibilityState(VisibilityStates visibilityState) {
        this.visibilityState = visibilityState;
    }

    /**
     * Gets the constraints of the relation.
     * @return the relationConstraints
     */
    public String getRelationConstraints() {
        return relationConstraints;
    }

    /**
     * Sets the constraints of the relation.
     * @param relationConstraints the relationConstraints to set
     */
    public void setRelationConstraints(String relationConstraints) {
        this.relationConstraints = relationConstraints;
    }

    /**
     * Gets the source object type involved in the relation.
     * @return the fkSourceObjectId
     */
    public ObjectTypes getFkSourceObjectId() {
        return fkSourceObjectId;
    }

    /**
     * Sets the source object type involved in the relation.
     * @param fkSourceObjectId the fkSourceObjectId to set
     */
    public void setFkSourceObjectId(ObjectTypes fkSourceObjectId) {
        this.fkSourceObjectId = fkSourceObjectId;
    }

    /**
     * Gets the target object type involved in the relation.
     * @return the fkTargetObjectId
     */
    public ObjectTypes getFkTargetObjectId() {
        return fkTargetObjectId;
    }

    /**
     * Sets the target object type involved in the relation.
     * @param fkTargetObjectId the fkTargetObjectId to set
     */
    public void setFkTargetObjectId(ObjectTypes fkTargetObjectId) {
        this.fkTargetObjectId = fkTargetObjectId;
    }

    /**
     * Gets the relation constraints id.
     * @return the fkRelationConstraintsId
     */
    public RelationConstraints getFkRelationConstraintsId() {
        return fkRelationConstraintsId;
    }

    /**
     * Sets the relation constraints id.
     * @param fkRelationConstraintsId the fkRelationConstraintsId to set
     */
    public void setFkRelationConstraintsId(RelationConstraints fkRelationConstraintsId) {
        this.fkRelationConstraintsId = fkRelationConstraintsId;
    }

    @Override
    public String toString() {
        return "RelationTypes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", visibilityState=" + visibilityState +
                ", relationConstraints='" + relationConstraints + '\'' +
                ", fkSourceObjectId=" + fkSourceObjectId +
                ", fkTargetObjectId=" + fkTargetObjectId +
                ", fkRelationConstraintsId=" + fkRelationConstraintsId +
                '}';
    }
}