package model;

import java.sql.Timestamp;

/**
 * Represents a facility within the system, encapsulating details about facilities such as hospitals, clinics, etc.
 */
public class Facilities {
    /**
     * The unique identifier for the facility.
     */
    private int id;

    /**
     * The name of the facility.
     */
    private String name;

    /**
     * The location or address of the facility.
     */
    private String location;

    /**
     * The type of facility, categorized by predefined types such as hospital, clinic, etc.
     */
    private FacilityTypes facilityType;

    /**
     * The operating hours of the facility.
     */
    private String operatingHours;

    /**
     * The timestamp when the facility was created in the system.
     */
    private Timestamp createdAt;

    /**
     * The timestamp of the last update made to the facility's record.
     */
    private Timestamp updatedAt;

    /**
     * The visibility state of the facility, determining how it is accessed within the application.
     */
    private VisibilityStates visibilityState;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public FacilityTypes getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(FacilityTypes facilityType) {
        this.facilityType = facilityType;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
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

    public VisibilityStates getVisibilityState() {
        return visibilityState;
    }

    public void setVisibilityState(VisibilityStates visibilityState) {
        this.visibilityState = visibilityState;
    }

    @Override
    public String toString() {
        return "Facilities{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", facilityType=" + facilityType +
                ", operatingHours='" + operatingHours + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", visibilityState=" + visibilityState +
                '}';
    }

    /**
     * Enumeration for the facility types like Hospital, Clinic, etc.
     */
    public enum FacilityTypes {
        HOSPITAL, CLINIC, RESEARCH_LAB, EMERGENCY_CARE, DIAGNOSTIC_CENTER
    }

    /**
     * Enumeration for visibility states of a facility such as Public, Private, Archived.
     */
    public enum VisibilityStates {
        PUBLIC, PRIVATE, ARCHIVED
    }
}