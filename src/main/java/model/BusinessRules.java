package model;

public class BusinessRules {

    private int id;
    private String ruleName;
    private String ruleDescription;
    private ObjectTypeEnum objectType;
    private String validationCriteria;
    private RuleTypeEnum ruleType;
    private int priority;
    private boolean active;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;

    public BusinessRules() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public ObjectTypeEnum getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectTypeEnum objectType) {
        this.objectType = objectType;
    }

    public String getValidationCriteria() {
        return validationCriteria;
    }

    public void setValidationCriteria(String validationCriteria) {
        this.validationCriteria = validationCriteria;
    }

    public RuleTypeEnum getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleTypeEnum ruleType) {
        this.ruleType = ruleType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public java.sql.Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BusinessRules{" +
                "id=" + id +
                ", ruleName='" + ruleName + '\'' +
                ", ruleDescription='" + ruleDescription + '\'' +
                ", objectType=" + objectType +
                ", validationCriteria='" + validationCriteria + '\'' +
                ", ruleType=" + ruleType +
                ", priority=" + priority +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public enum ObjectTypeEnum {
        ACTIONS,
        BUSINESS_RULES,
        DOCUMENTS,
        EDIT_HISTORIES,
        FACILITIES,
        OBJECT_TYPES,
        PERMISSIONS,
        PROPERTIES,
        RELATION_CONSTRAINTS,
        RELATION_INSTANCES,
        RELATION_TYPES,
        ROLES,
        USER_ROLES
    }

    public enum RuleTypeEnum {
        VALIDATION,
        TRANSFORMATION,
        RELATIONSHIP_ENFORCEMENT,
        DATA_INTEGRITY,
        LOGIC_APPLICATION
    }
}