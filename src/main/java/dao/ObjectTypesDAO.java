package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class ObjectTypesDAO {

	
	
	/**
	 * Inserts a new object type into the database.
	 * @param name The human-readable name for the object type.
	 * @param description Detailed explanation of the object type and its purpose.
	 * @param visibilityStates The visibility state of the object type.
	 * @param lastEditedBy User who last modified the object type.
	 * @return The created ObjectTypes object with its generated id.
	 */
	public ObjectTypes createObjectType(String name, String description, ObjectTypes.VisibilityStates visibilityStates, String lastEditedBy) {
	    Connection connection = DatabaseUtility.connect();
	    String query = "INSERT INTO object_types (name, description, visibility_states, last_edited_by) VALUES (?, ?, ?::visibility_states, ?) RETURNING id;";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, name);
	        preparedStatement.setString(2, description);
	        preparedStatement.setString(3, visibilityStates.toString());
	        preparedStatement.setString(4, lastEditedBy);
	
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            ObjectTypes objectType = new ObjectTypes();
	            objectType.setId(resultSet.getInt("id"));
	            objectType.setName(name);
	            objectType.setDescription(description);
	            objectType.setVisibilityStates(visibilityStates);
	            objectType.setLastEditedBy(lastEditedBy);
	            return objectType;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error creating object type", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return null;
	}
	
	/**
	 * Updates an existing object type's database record.
	 * @param id Unique identifier of the object type to be updated.
	 * @param name New name of the object type.
	 * @param description New detailed explanation of the object type.
	 * @param visibilityStates New visibility state of the object type.
	 * @param lastEditedBy User who last modified the object type.
	 * @return boolean indicating success or failure of the update operation.
	 */
	public boolean updateObjectType(int id, String name, String description, ObjectTypes.VisibilityStates visibilityStates, String lastEditedBy) {
	  Connection connection = DatabaseUtility.connect();
	  try {
	    String query = "UPDATE object_types SET name = ?, description = ?, visibility_states = ::visibility_states, last_modified = NOW(), last_edited_by = ? WHERE id = ?;";
	    PreparedStatement pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, name);
	    pstmt.setString(2, description);
	    pstmt.setString(3, visibilityStates.name());
	    pstmt.setString(4, lastEditedBy);
	    pstmt.setInt(5, id);
	
	    int affectedRows = pstmt.executeUpdate();
	    return affectedRows > 0;
	  } catch (SQLException e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error updating object type with ID: " + id, e);
	    return false;
	  } finally {
	    DatabaseUtility.disconnect(connection);
	  }
	}
	
	
	/**
	 * Fetches a single object type by its ID to validate existence before editing or to display data in relation types.
	 * @param id The unique identifier of the object type to be retrieved.
	 * @return An ObjectTypes instance representing the object type.
	 */
	public ObjectTypes findObjectTypeById(int id) {
	    ObjectTypes objectType = null;
	    String sql = "SELECT * FROM object_types WHERE id = ?;";
	    try (Connection connection = DatabaseUtility.connect();
	         PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            objectType = new ObjectTypes();
	            objectType.setId(rs.getInt("id"));
	            objectType.setName(rs.getString("name"));
	            objectType.setDescription(rs.getString("description"));
	            objectType.setVisibilityStates(ObjectTypes.VisibilityStates.valueOf(rs.getString("visibility_states")));
	            objectType.setDateCreated(rs.getTimestamp("date_created"));
	            objectType.setLastModified(rs.getTimestamp("last_modified"));
	            objectType.setLastEditedBy(rs.getString("last_edited_by"));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(null);
	    }
	    return objectType;
	}
	
	/*
	 Used in 'Object Type Overview' to remove an object type from the database.
	 @param id The unique identifier of the object type to be deleted.
	 @return boolean indicating if the delete operation was successful
	*/
	public boolean deleteObjectType(int id) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    boolean isDeleted = false;
	    String deleteSQL = "DELETE FROM object_types WHERE id = ?;";
	    try {
	        connection = DatabaseUtility.connect();
	        preparedStatement = connection.prepareStatement(deleteSQL);
	        preparedStatement.setInt(1, id);
	        int rowsAffected = preparedStatement.executeUpdate();
	        isDeleted = rowsAffected > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting object type with ID: " + id, e);
	    } finally {
	        DatabaseUtility.closePreparedStatement(preparedStatement);
	        DatabaseUtility.disconnect(connection);
	    }
	    return isDeleted;
	}
	
	/**
	 * Retrieves all object types from the database for selection in various forms across the application.
	 * @return a list of all ObjectTypes.
	 */
	public List<ObjectTypes> fetchAllObjectTypes() {
	    List<ObjectTypes> objectTypesList = new ArrayList<>();
	    Connection connection = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	        connection = DatabaseUtility.connect();
	        stmt = connection.createStatement();
	        String query = "SELECT * FROM object_types";
	        rs = stmt.executeQuery(query);
	
	        while (rs.next()) {
	            ObjectTypes objectType = new ObjectTypes();
	            objectType.setId(rs.getInt("id"));
	            objectType.setName(rs.getString("name"));
	            objectType.setDescription(rs.getString("description"));
	            objectType.setVisibilityStates(ObjectTypes.VisibilityStates.valueOf(rs.getString("visibility_states")));
	            objectType.setDateCreated(rs.getTimestamp("date_created"));
	            objectType.setLastModified(rs.getTimestamp("last_modified"));
	            objectType.setLastEditedBy(rs.getString("last_edited_by"));
	            objectTypesList.add(objectType);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ObjectTypesDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(ObjectTypesDAO.class.getName()).log(Level.SEVERE, null, e);
	            }
	        }
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                Logger.getLogger(ObjectTypesDAO.class.getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	    return objectTypesList;
	}
}
