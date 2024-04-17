package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class RelationTypesDAO {

	
	/**
	 * Fetches a list of all relation types from the database without any filtering.
	 * This method is crucial for populating the 'Relation Types Table' in the 'Relation Type Overview'
	 * page with all available relation types, providing a comprehensive view for managing relation definitions.
	 *
	 * @return A list of all relation types.
	 */
	public List<RelationTypes> findAllRelationTypes() {
	    List<RelationTypes> relationTypesList = new ArrayList<>();
	    Connection connection = null;
	    Statement stmt = null;
	    try {
	        connection = DatabaseUtility.connect();
	        stmt = connection.createStatement();
	        String sql = "SELECT * FROM relation_types";
	        ResultSet rs = stmt.executeQuery(sql);
	        while (rs.next()) {
	            RelationTypes relationType = new RelationTypes();
	            relationType.setId(rs.getInt("id"));
	            relationType.setName(rs.getString("name"));
	            relationType.setDescription(rs.getString("description"));
	            relationType.setCreatedAt(rs.getTimestamp("created_at"));
	            relationType.setUpdatedAt(rs.getTimestamp("updated_at"));
	            // Assume ENUM values are stored as String
	            relationType.setVisibilityState(RelationTypes.VisibilityStates.valueOf(rs.getString("visibility_state").toUpperCase()));
	            relationType.setRelationConstraints(rs.getString("relation_constraints"));
	            // Skipping fk_source_object_id, fk_target_object_id, fk_relation_constraints_id for simplicity
	            relationTypesList.add(relationType);
	        }
	        rs.close();
	        stmt.close();
	    } catch (SQLException se) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, se);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return relationTypesList;
	}
	
	/**
	 * Creates a new relation type in the database with the specified name, description, source and target object types, and relation constraints.
	 * @param name The name of the relation type being defined.
	 * @param description The description of the relation type, explaining its purpose and application context.
	 * @param fkSourceObjectId The source object type involved in the relation.
	 * @param fkTargetObjectId The target object type involved in the relation.
	 * @param fkRelationConstraintsId The identifier for a set of constraints governing the relation type.
	 * @return The newly created RelationTypes object, or null if the creation was unsuccessful.
	 */
	public RelationTypes createRelationType(String name, String description, ObjectTypes fkSourceObjectId, ObjectTypes fkTargetObjectId, RelationConstraints fkRelationConstraintsId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "INSERT INTO relation_types (name, description, fk_source_object_id, fk_target_object_id, fk_relation_constraints_id) VALUES (?, ?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, name);
	        preparedStatement.setString(2, description);
	        preparedStatement.setInt(3, fkSourceObjectId.getId());
	        preparedStatement.setInt(4, fkTargetObjectId.getId());
	        preparedStatement.setInt(5, fkRelationConstraintsId.getId());
	
	        int affectedRows = preparedStatement.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creating relation type failed, no rows affected.");
	        }
	
	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                RelationTypes relationType = new RelationTypes();
	                relationType.setId(generatedKeys.getInt(1));
	                relationType.setName(name);
	                relationType.setDescription(description);
	                relationType.setFkSourceObjectId(fkSourceObjectId);
	                relationType.setFkTargetObjectId(fkTargetObjectId);
	                relationType.setFkRelationConstraintsId(fkRelationConstraintsId);
	                return relationType;
	            } else {
	                throw new SQLException("Creating relation type failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        return null;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException logOrIgnore) {}
	    }
	}
	
	/**
	 * Deletes a relation type identified by its unique ID from the database.
	 * @param relationTypeId The unique identifier of the relation type to be deleted.
	 */
	public boolean deleteRelationTypeById(int relationTypeId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    String sql = "DELETE FROM relation_types WHERE id = ?;";
	    try {
	        connection = DatabaseUtility.connect();
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, relationTypeId);
	
	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "RelationType with ID " + relationTypeId + " was successfully deleted.");
	            return true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting RelationType with ID " + relationTypeId, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing resources", e);
	        }
	    }
	    return false;
	}
	
	
	/**
	 * Fetches a single relation type by its ID to populate the form fields on the 'Define Relation Type' page for editing.
	 * This method is also used in the 'Relation Type Overview' page when editing a relation type,
	 * allowing users to view and modify existing relation type definitions.
	 *
	 * @param relationTypeId The unique identifier of the relation type to fetch.
	 * @return RelationTypes object containing relation type details
	 */
	public RelationTypes findRelationTypeById(String relationTypeId) {
	    RelationTypes relationType = null;
	    String query = "SELECT * FROM relation_types WHERE id = ?;";
	    try (Connection connection = DatabaseUtility.connect()) {
	        PreparedStatement pstmt = connection.prepareStatement(query);
	        pstmt.setInt(1, Integer.parseInt(relationTypeId));
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            relationType = new RelationTypes();
	            relationType.setId(rs.getInt("id"));
	            relationType.setName(rs.getString("name"));
	            relationType.setDescription(rs.getString("description"));
	            relationType.setCreatedAt(rs.getTimestamp("created_at"));
	            relationType.setUpdatedAt(rs.getTimestamp("updated_at"));
	            // Assuming visibilityState is stored as String in the database
	            relationType.setVisibilityState(RelationTypes.VisibilityStates.valueOf(rs.getString("visibility_state").toUpperCase()));
	            relationType.setRelationConstraints(rs.getString("relation_constraints"));
	            // Note: Set foreign key object references as needed
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(RelationTypesDAO.class.getName()).log(Level.SEVERE, "Error fetching relation type by ID", e);
	    } finally {
	        DatabaseUtility.disconnect();
	    }
	    return relationType;
	}
	
	/**
	 * Updates an existing relation type in the database with new details.
	 * @param id The unique identifier of the relation type being updated.
	 * @param name The updated name of the relation type.
	 * @param description The updated description of the relation type.
	 * @param visibilityState The updated visibility state of the relation type.
	 * @param relationConstraints The updated text containing relation constraints.
	 * @param fkSourceObjectId The updated source object type involved in the relation.
	 * @param fkTargetObjectId The updated target object type involved in the relation.
	 * @param fkRelationConstraintsId The updated identifier for the set of constraints governing the relation type.
	 * @return boolean Returns true if update was successful, false otherwise.
	 */
	public boolean updateRelationType(int id, String name, String description, RelationTypes.VisibilityStates visibilityState, String relationConstraints, ObjectTypes fkSourceObjectId, ObjectTypes fkTargetObjectId, RelationConstraints fkRelationConstraintsId) {
	    Connection connection = DatabaseUtility.connect();
	    try {
	        String query = "UPDATE relation_types SET name = ?, description = ?, visibility_state = ?, relation_constraints = ?, fk_source_object_id = ?, fk_target_object_id = ?, fk_relation_constraints_id = ? WHERE id = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, name);
	        preparedStatement.setString(2, description);
	        preparedStatement.setString(3, visibilityState.toString());
	        preparedStatement.setString(4, relationConstraints);
	        preparedStatement.setInt(5, fkSourceObjectId.getId());
	        preparedStatement.setInt(6, fkTargetObjectId.getId());
	        preparedStatement.setInt(7, fkRelationConstraintsId.getId());
	        preparedStatement.setInt(8, id);
	
	        int affectedRows = preparedStatement.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating relation type", e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	}
}
