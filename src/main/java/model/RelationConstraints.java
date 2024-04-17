package model;

import java.util.Objects;

/**
 * This class models the relation_constraints entity from the database.
 * It encapsulates the various fields and constraints applied to relationships between object types within the system.
 */
public class RelationConstraints {
    
    private int id;
    private Cardinality cardinality;
    private Optionality optionality;
    private boolean updateCascade;
    private String constraintName;
    private String description;
    private RelationTypes fkRelationTypeId;

    /**
     * Gets the unique identifier for the RelationConstraints.
     * @return the id of the RelationConstraint.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the RelationConstraints.
     * @param id the unique identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the cardinality of the RelationConstraints.
     * @return the cardinality.
     */
    public Cardinality getCardinality() {
        return cardinality;
    }

    /**
     * Sets the cardinality of the RelationConstraints.
     * @param cardinality the cardinality to set.
     */
    public void setCardinality(Cardinality cardinality) {
        this.cardinality = cardinality;
    }

    /**
     * Gets the optionality of the RelationConstraints.
     * @return the optionality.
     */
    public Optionality getOptionality() {
        return optionality;
    }

    /**
     * Sets the optionality of the RelationConstraints.
     * @param optionality the optionality to set.
     */
    public void setOptionality(Optionality optionality) {
        this.optionality = optionality;
    }

    /**
     * Checks if is update cascade.
     * @return true if updates on one object type should cascade to related objects, otherwise false.
     */
    public boolean isUpdateCascade() {
        return updateCascade;
    }

    /**
     * Sets the update cascade behavior.
     * @param updateCascade the updateCascade to set.
     */
    public void setUpdateCascade(boolean updateCascade) {
        this.updateCascade = updateCascade;
    }

    /**
     * Gets the name of the constraint.
     * @return the constraintName.
     */
    public String getConstraintName() {
        return constraintName;
    }

    /**
     * Sets the name of the constraint.
     * @param constraintName the constraintName to set.
     */
    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    /**
     * Gets the description of the RelationConstraints.
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the RelationConstraints.
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the foreign key relation type id.
     * @return the fkRelationTypeId.
     */
    public RelationTypes getFkRelationTypeId() {
        return fkRelationTypeId;
    }

    /**
     * Sets the foreign key relation type id.
     * @param fkRelationTypeId the fkRelationTypeId to set.
     */
    public void setFkRelationTypeId(RelationTypes fkRelationTypeId) {
        this.fkRelationTypeId = fkRelationTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelationConstraints)) return false;
        RelationConstraints that = (RelationConstraints) o;
        return id == that.id &&
                updateCascade == that.updateCascade &&
                cardinality == that.cardinality &&
                optionality == that.optionality &&
                Objects.equals(constraintName, that.constraintName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(fkRelationTypeId, that.fkRelationTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardinality, optionality, updateCascade, constraintName, description, fkRelationTypeId);
    }

    @Override
    public String toString() {
        return "RelationConstraints{" +
                "id=" + id +
                ", cardinality=" + cardinality +
                ", optionality=" + optionality +
                ", updateCascade=" + updateCascade +
                ", constraintName='" + constraintName + '\'' +
                ", description='" + description + '\'' +
                ", fkRelationTypeId=" + fkRelationTypeId +
                '}';
    }

    /**
     * Enumeration for the cardinality attribute of RelationConstraints.
     */
    public enum Cardinality {
        ONE_TO_ONE, ONE_TO_MANY, MANY_TO_ONE, MANY_TO_MANY
    }

    /**
     * Enumeration for the optionality attribute of RelationConstraints.
     */
    public enum Optionality {
        MANDATORY, OPTIONAL
    }
}