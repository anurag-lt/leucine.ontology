package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class RelationInstancesDAO {

	
	/*/ Creates a new relation instance in the database.
	@param timestamp The exact time when the relation instance was created.
	@param status The current status of the relation instance.
	@param description A textual description of the relationship instance.
	@param fkRelationTypeId Foreign key identifier for the relationship type.
	@param fkSourceObjectId Foreign key identifier for the source object.
	@param fkTargetObjectId Foreign key identifier for the target object.
	@return The newly created relation instance or null if creation fails. */
	public RelationInstances createRelationInstance(Timestamp timestamp, RelationInstances.ObjectTypeEnum status, String description, int fkRelationTypeId, int fkSourceObjectId, int fkTargetObjectId) {
	    Connection connection = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String insertSQL = "INSERT INTO relation_instances(timestamp, status, description, fk_relation_type_id, fk_source_object_id, fk_target_object_id) VALUES(?,?,?,?,?,?) RETURNING id";
	    try {
	        pstmt = connection.prepareStatement(insertSQL);
	        pstmt.setTimestamp(1, timestamp);
	        pstmt.setString(2, status.name());
	        pstmt.setString(3, description);
	        pstmt.setInt(4, fkRelationTypeId);
	        pstmt.setInt(5, fkSourceObjectId);
	        pstmt.setInt(6, fkTargetObjectId);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            RelationInstances newInstance = new RelationInstances();
	            newInstance.setId(rs.getInt("id"));
	            newInstance.setTimestamp(timestamp);
	            newInstance.setStatus(status);
	            newInstance.setDescription(description);
	            newInstance.setFkRelationTypeID(new RelationTypes(fkRelationTypeId)); // Assuming RelationTypes object initialization
	            newInstance.setFkSourceObjectID(new ObjectTypes(fkSourceObjectId)); // Assuming ObjectTypes object initialization
	            newInstance.setFkTargetObjectID(new ObjectTypes(fkTargetObjectId)); // Assuming ObjectTypes object initialization
	            return newInstance;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error creating relation instance", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        try {
	            if (pstmt != null) pstmt.close();
	            if (rs != null) rs.close();
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing resources", e);
	        }
	    }
	    return null;
	}
	
	/**
	 * Utilized in the 'Relation Type Overview' section to display all instances of a specified relationship type, aiding in comprehensive overview and management.
	 * @param relationTypeId The unique identifier of the relation type for which instances are being fetched.
	 * @return A list of RelationInstances corresponding to the specified relation type.
	 */
	public List<RelationInstances> findAllRelationInstancesByTypeId(String relationTypeId) {
	    List<RelationInstances> instances = new ArrayList<>();
	    String query = "SELECT * FROM relation_instances WHERE fk_relation_type_id = ?;";
	    try (Connection connection = DatabaseUtility.connect();
	         PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, relationTypeId);
	
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                RelationInstances instance = new RelationInstances();
	                instance.setId(rs.getInt("id"));
	                instance.setTimestamp(rs.getTimestamp("timestamp"));
	                instance.setStatus(RelationInstances.ObjectTypeEnum.valueOf(rs.getString("status")));
	                instance.setDescription(rs.getString("description"));
	                instance.setFkRelationTypeID(new RelationTypes(rs.getInt("fk_relation_type_id")));
	                instance.setFkSourceObjectID(new ObjectTypes(rs.getInt("fk_source_object_id")));
	                instance.setFkTargetObjectID(new ObjectTypes(rs.getInt("fk_target_object_id")));
	                instances.add(instance);
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return instances;
	}
	
	/*
	 Deletes a specific relation instance identified by the instanceId from the relation_instances table.
	 @param instanceId The unique identifier of the relation instance to be deleted.
	*/
	public boolean deleteRelationInstanceById(String instanceId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    boolean isDeleted = false;
	    String sql = "DELETE FROM relation_instances WHERE id = ?;";
	    try {
	        connection = DatabaseUtility.connect();
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, instanceId);
	
	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            isDeleted = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error deleting relation instance by ID", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	        }
	    }
	    return isDeleted;
	}
	
	
	/**
	 * Fetches details of a specific relation instance by its unique identifier.
	 * Used in the 'Relation Type Overview' section for data transparency and auditability.
	 *
	 * @param instanceId The unique identifier of the relation instance.
	 * @return A RelationInstances object containing details of the specified relation instance.
	 */
	public RelationInstances findRelationInstanceById(String instanceId) {
	    Connection connection = DatabaseUtility.connect();
	    RelationInstances relationInstance = null;
	    try {
	        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM relation_instances WHERE id = ?");
	        pstmt.setInt(1, Integer.parseInt(instanceId));
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            relationInstance = new RelationInstances();
	            relationInstance.setId(rs.getInt("id"));
	            relationInstance.setTimestamp(rs.getTimestamp("timestamp"));
	            relationInstance.setStatus(RelationInstances.ObjectTypeEnum.valueOf(rs.getString("status")));
	            relationInstance.setDescription(rs.getString("description"));
	            // Assuming the setting of fkRelationTypeID, fkSourceObjectID, and fkTargetObjectID is through their respective IDs
	            relationInstance.setFkRelationTypeID(new RelationTypes(rs.getInt("fk_relation_type_id")));
	            relationInstance.setFkSourceObjectID(new ObjectTypes(rs.getInt("fk_source_object_id")));
	            relationInstance.setFkTargetObjectID(new ObjectTypes(rs.getInt("fk_target_object_id")));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error retrieving relation instance by ID: " + instanceId, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return relationInstance;
	}
	
	/**
	 * Updates an existing relation instance in the database with new information.
	 * @param id The unique identifier of the relation instance to update.
	 * @param timestamp New timestamp reflecting the update time of the relation instance.
	 * @param status Updated status of the relation instance.
	 * @param description Updated textual description of the relation instance.
	 * @param fkRelationTypeId Updated foreign key linking to the relationship type.
	 * @param fkSourceObjectId Updated foreign key specifying the source object.
	 * @param fkTargetObjectId Updated foreign key defining the target object.
	 * @return boolean indicating if the update was successful.
	 */
	public boolean updateRelationInstance(int id, Timestamp timestamp, ObjectTypeEnum status, String description, int fkRelationTypeId, int fkSourceObjectId, int fkTargetObjectId) {
	    Connection connection = DatabaseUtility.connect();
	    try {
	        String query = "UPDATE relation_instances SET timestamp = ?, status = ?, description = ?, fk_relation_type_id = ?, fk_source_object_id = ?, fk_target_object_id = ? WHERE id = ?;";
	        PreparedStatement pstmt = connection.prepareStatement(query);
	        pstmt.setTimestamp(1, timestamp);
	        pstmt.setString(2, status.toString());
	        pstmt.setString(3, description);
	        pstmt.setInt(4, fkRelationTypeId);
	        pstmt.setInt(5, fkSourceObjectId);
	        pstmt.setInt(6, fkTargetObjectId);
	        pstmt.setInt(7, id);
	
	        int affectedRows = pstmt.executeUpdate();
	        DatabaseUtility.disconnect(connection);
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating relation instance: ", e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	}
}
