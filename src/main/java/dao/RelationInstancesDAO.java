package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class RelationInstancesDAO {

	
	/**
	 * Creates a new relation instance in the database.
	 * @param timestamp The exact time when the relation instance is created or modified.
	 * @param status The current status of the relation instance.
	 * @param description A textual description of the relationship instance.
	 * @param fkRelationTypeID The type of relationship this instance represents.
	 * @param fkSourceObjectID Specifies the source object involved in the relationship.
	 * @param fkTargetObjectID Defines the target object of the relationship.
	 * @return The id of the newly created relation instance or -1 if the operation fails.
	 */
	public int createRelationInstance(Timestamp timestamp, RelationInstances.ObjectTypeEnum status, String description, RelationTypes fkRelationTypeID, ObjectTypes fkSourceObjectID, ObjectTypes fkTargetObjectID) {
	  Connection connection = null;
	  PreparedStatement preparedStatement = null;
	  int generatedId = -1;
	  try {
	    connection = DatabaseUtility.connect();
	    String sql = "INSERT INTO relation_instances (timestamp, status, description, fk_relation_type_id, fk_source_object_id, fk_target_object_id) VALUES (?, ?, ?, ?, ?, ?)";
	    preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    preparedStatement.setTimestamp(1, timestamp);
	    preparedStatement.setString(2, status.name());
	    preparedStatement.setString(3, description);
	    preparedStatement.setInt(4, fkRelationTypeID.getId());
	    preparedStatement.setInt(5, fkSourceObjectID.getId());
	    preparedStatement.setInt(6, fkTargetObjectID.getId());
	    preparedStatement.executeUpdate();
	    ResultSet rs = preparedStatement.getGeneratedKeys();
	    if (rs.next()) {
	      generatedId = rs.getInt(1);
	    }
	  } catch (SQLException e) {
	    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error creating relation instance", e);
	  } finally {
	    DatabaseUtility.disconnect(connection);
	    if (preparedStatement != null) {
	      try {
	        preparedStatement.close();
	      } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	      }
	    }
	  }
	  return generatedId;
	}
	
	/**
	 * Retrieves relation instances for a specific relation type.
	 * It's used in the 'Relation Type Overview' page for managing instances associated with each relation type.
	 *
	 * @param relationTypeId The unique identifier of the relation type to fetch instances for.
	 * @return List<RelationInstances> A list of RelationInstances.
	 */
	public List<RelationInstances> fetchRelationInstancesByTypeId(String relationTypeId) {
	    List<RelationInstances> relationInstancesList = new ArrayList<>();
	    Connection connection = DatabaseUtility.connect();
	    try {
	        String sql = "SELECT * FROM relation_instances WHERE fk_relation_type_id = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, relationTypeId);
	
	        ResultSet resultSet = preparedStatement.executeQuery();
	
	        while (resultSet.next()) {
	            RelationInstances relationInstance = new RelationInstances();
	            relationInstance.setId(resultSet.getInt("id"));
	            relationInstance.setTimestamp(resultSet.getTimestamp("timestamp"));
	            relationInstance.setStatus(RelationInstances.ObjectTypeEnum.valueOf(resultSet.getString("status")));
	            relationInstance.setDescription(resultSet.getString("description"));
	            // Assuming setters for setting FK objects just from IDs, you might need to fetch the full objects based on these IDs
	            relationInstance.setFkRelationTypeID(new RelationTypes()); // Set relation type
	            relationInstance.getFkRelationTypeID().setId(resultSet.getInt("fk_relation_type_id"));
	            relationInstance.setFkSourceObjectID(new ObjectTypes()); // Set source object type
	            relationInstance.getFkSourceObjectID().setId(resultSet.getInt("fk_source_object_id"));
	            relationInstance.setFkTargetObjectID(new ObjectTypes()); // Set target object type
	            relationInstance.getFkTargetObjectID().setId(resultSet.getInt("fk_target_object_id"));
	            relationInstancesList.add(relationInstance);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	
	    return relationInstancesList;
	}
	
	
	/**
	 * Deletes a specific relation instance identified by its ID. Supports data management operations in the 'Relation Type Overview' section.
	 * @param instanceId The unique identifier of the instance to be deleted.
	 * @return boolean indicating the success of the deletion operation.
	 */
	public boolean deleteRelationInstanceById(String instanceId) {
	    Connection connection = DatabaseUtility.connect();
	    String sql = "DELETE FROM relation_instances WHERE id = ?;";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, instanceId);
	        int rowsAffected = preparedStatement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	}
	
	/**
	 * Retrieves detailed information for a specific relation instance.
	 * @param instanceId The unique identifier of the relation instance to retrieve detailed information for.
	 * @return RelationInstances The detailed information of the specified relation instance, or null if not found.
	 */
	public RelationInstances findRelationInstanceById(String instanceId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    RelationInstances relationInstance = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "SELECT * FROM relation_instances WHERE id = ?;";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, Integer.parseInt(instanceId));
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            relationInstance = new RelationInstances();
	            relationInstance.setId(resultSet.getInt("id"));
	            relationInstance.setTimestamp(resultSet.getTimestamp("timestamp"));
	            // Setting ENUM based on the string value from DB
	            relationInstance.setStatus(RelationInstances.ObjectTypeEnum.valueOf(resultSet.getString("status")));
	            relationInstance.setDescription(resultSet.getString("description"));
	            // Assuming setFkRelationTypeID, setFkSourceObjectID, setFkTargetObjectID would
	            // internally handle object initialization from ID, else additional queries needed.
	        }
	    } catch (Exception e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching relation instance by ID", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return relationInstance;
	}
	
	
	/**
	 * Updates source and target objects of a relation instance.
	 * @param instanceId The unique identifier of the relation instance to update.
	 * @param newSourceObjectId The updated identifier for the source object involved in the relationship.
	 * @param newTargetObjectId The updated identifier for the target object of the relationship.
	 * @return boolean indicating the success of the update operation.
	 */
	public boolean updateRelationInstance(String instanceId, String newSourceObjectId, String newTargetObjectId) {
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    boolean isSuccess = false;
	    String updateQuery = "UPDATE relation_instances SET fk_source_object_id = ?, fk_target_object_id = ? WHERE id = ?";
	    try {
	        connection = DatabaseUtility.connect();
	        pstmt = connection.prepareStatement(updateQuery);
	        pstmt.setInt(1, Integer.parseInt(newSourceObjectId));
	        pstmt.setInt(2, Integer.parseInt(newTargetObjectId));
	        pstmt.setInt(3, Integer.parseInt(instanceId));
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            isSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error updating relation instance", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        try {
	            if (pstmt != null) pstmt.close();
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	        }
	    }
	    return isSuccess;
	}
}
