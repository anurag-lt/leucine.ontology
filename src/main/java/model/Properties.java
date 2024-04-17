package model;

import java.util.Objects;

/**
 * Represents a property within the system, which defines specific aspects of an object type.
 */
public class Properties {

    private int id;
    private String name;
    private String description;
    private boolean uniqueIdentifier;
    private DataTypes dataType;
    private String defaultValue;
    private boolean required;
    private PropertyTypes propertyType;
    private VisibilityStates visibilityState;
    private ObjectTypes fkObjectType;

    /**
     * Enumeration for data types that a property can hold.
     */
    public enum DataTypes {
        TEXT, NUMBER, BOOLEAN, DATE
    }

    /**
     * Enumeration for the types of properties.
     */
    public enum PropertyTypes {
        TEXT, NUMBER, BOOLEAN, DATE
    }

    /**
     * Enumeration for visibility states of a property.
     */
    public enum VisibilityStates {
        PUBLIC, PRIVATE, ARCHIVED
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

    public boolean isUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(boolean uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public DataTypes getDataType() {
        return dataType;
    }

    public void setDataType(DataTypes dataType) {
        this.dataType = dataType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public PropertyTypes getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyTypes propertyType) {
        this.propertyType = propertyType;
    }

    public VisibilityStates getVisibilityState() {
        return visibilityState;
    }

    public void setVisibilityState(VisibilityStates visibilityState) {
        this.visibilityState = visibilityState;
    }

    public ObjectTypes getFkObjectType() {
        return fkObjectType;
    }

    public void setFkObjectType(ObjectTypes fkObjectType) {
        this.fkObjectType = fkObjectType;
    }

    // toString method
    @Override
    public String toString() {
        return "Properties{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", uniqueIdentifier=" + uniqueIdentifier +
                ", dataType=" + dataType +
                ", defaultValue='" + defaultValue + '\'' +
                ", required=" + required +
                ", propertyType=" + propertyType +
                ", visibilityState=" + visibilityState +
                ", fkObjectType=" + fkObjectType +
                '}';
    }

    // hashCode and equals for object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Properties that = (Properties) o;
        return id == that.id &&
                uniqueIdentifier == that.uniqueIdentifier &&
                required == that.required &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                dataType == that.dataType &&
                Objects.equals(defaultValue, that.defaultValue) &&
                propertyType == that.propertyType &&
                visibilityState == that.visibilityState &&
                Objects.equals(fkObjectType, that.fkObjectType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, uniqueIdentifier, dataType, defaultValue, required, propertyType, visibilityState, fkObjectType);
    }
}