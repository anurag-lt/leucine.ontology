package model;

import java.sql.Timestamp;
import java.util.Objects;

public class ObjectTypes {
    
    private int id;
    private String name;
    private String description;
    private VisibilityStates visibilityStates;
    private Timestamp dateCreated;
    private Timestamp lastModified;
    private String lastEditedBy;
    private Properties fkObjectType;
    private RelationTypes fkSourceObject;
    private RelationTypes fkTargetObject;
    
    public ObjectTypes() {
    }

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

    public VisibilityStates getVisibilityStates() {
        return visibilityStates;
    }

    public void setVisibilityStates(VisibilityStates visibilityStates) {
        this.visibilityStates = visibilityStates;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Properties getFkObjectType() {
        return fkObjectType;
    }

    public void setFkObjectType(Properties fkObjectType) {
        this.fkObjectType = fkObjectType;
    }

    public RelationTypes getFkSourceObject() {
        return fkSourceObject;
    }

    public void setFkSourceObject(RelationTypes fkSourceObject) {
        this.fkSourceObject = fkSourceObject;
    }

    public RelationTypes getFkTargetObject() {
        return fkTargetObject;
    }

    public void setFkTargetObject(RelationTypes fkTargetObject) {
        this.fkTargetObject = fkTargetObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectTypes)) return false;
        ObjectTypes that = (ObjectTypes) o;
        return getId() == that.getId() && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && getVisibilityStates() == that.getVisibilityStates() && Objects.equals(getDateCreated(), that.getDateCreated()) && Objects.equals(getLastModified(), that.getLastModified()) && Objects.equals(getLastEditedBy(), that.getLastEditedBy()) && Objects.equals(getFkObjectType(), that.getFkObjectType()) && Objects.equals(getFkSourceObject(), that.getFkSourceObject()) && Objects.equals(getFkTargetObject(), that.getFkTargetObject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getVisibilityStates(), getDateCreated(), getLastModified(), getLastEditedBy(), getFkObjectType(), getFkSourceObject(), getFkTargetObject());
    }

    @Override
    public String toString() {
        return "ObjectTypes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", visibilityStates=" + visibilityStates +
                ", dateCreated=" + dateCreated +
                ", lastModified=" + lastModified +
                ", lastEditedBy='" + lastEditedBy + '\'' +
                ", fkObjectType=" + fkObjectType +
                ", fkSourceObject=" + fkSourceObject +
                ", fkTargetObject=" + fkTargetObject +
                '}';
    }
    
    public enum VisibilityStates {
        PUBLIC, PRIVATE, ARCHIVED
    }
}