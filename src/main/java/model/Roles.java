package model;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents a role within the system, including details such as role name, description,
 * creation and modification timestamps, and visibility state.
 */
public class Roles {
    
    private int id;
    private String name;
    private String description;
    private Timestamp createdAt;
    private Timestamp lastModifiedAt;
    private VisibilityState visibilityState;

    /**
     * Enum for Visibility State, mirroring visibility_states in the database.
     */
    public enum VisibilityState {
        PUBLIC, PRIVATE, ARCHIVED
    }

    // Getters
    /**
     * Gets the unique identifier of the role.
     * @return the role's unique ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the role.
     * @return the role's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the role.
     * @return the role's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the timestamp when the role was created.
     * @return the role's creation timestamp
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the timestamp for the last modification of the role.
     * @return the timestamp of the last modification
     */
    public Timestamp getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Gets the visibility state of the role.
     * @return the role's visibility state
     */
    public VisibilityState getVisibilityState() {
        return visibilityState;
    }

    // Setters
    /**
     * Sets the unique identifier of the role.
     * @param id the unique identifier to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the role.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of the role.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the timestamp when the role was created.
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Sets the timestamp for the last modification of the role.
     * @param lastModifiedAt the last modification timestamp to set
     */
    public void setLastModifiedAt(Timestamp lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Sets the visibility state of the role.
     * @param visibilityState the visibility state to set
     */
    public void setVisibilityState(VisibilityState visibilityState) {
        this.visibilityState = visibilityState;
    }
    
    // toString
    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", visibilityState=" + visibilityState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Roles)) return false;
        Roles roles = (Roles) o;
        return getId() == roles.getId() &&
                Objects.equals(getName(), roles.getName()) &&
                Objects.equals(getDescription(), roles.getDescription()) &&
                Objects.equals(getCreatedAt(), roles.getCreatedAt()) &&
                Objects.equals(getLastModifiedAt(), roles.getLastModifiedAt()) &&
                getVisibilityState() == roles.getVisibilityState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getCreatedAt(), getLastModifiedAt(), getVisibilityState());
    }
}