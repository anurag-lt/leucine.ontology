package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class PermissionsDAO {

	
	/**
	 * Retrieves a list of all available object types from the database to populate the 'Object Type Selection' dropdown in various sections.
	 * This supports the creation and editing of object types and properties by providing a comprehensive list of existing object types.
	 */
	public List<ObjectType> fetchAllObjectTypes() {
	    List<ObjectType> objectTypes = new ArrayList<>();
	    Connection connection = null;
	    Statement statement = null;
	    ResultSet resultSet = null;
	    try {
	        connection = DatabaseUtility.connect();
	        statement = connection.createStatement();
	        String query = "SELECT * FROM object_types";
	        resultSet = statement.executeQuery(query);
	        while (resultSet.next()) {
	            ObjectType objectType = new ObjectType();
	            objectType.setId(resultSet.getInt("id"));
	            objectType.setName(resultSet.getString("name"));
	            objectType.setDescription(resultSet.getString("description"));
	            objectType.setVisibilityStates(ObjectType.VisibilityStates.valueOf(resultSet.getString("visibility_states")));
	            objectTypes.add(objectType);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching object types", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { /* Ignored */ }
	        if (statement != null) try { statement.close(); } catch (SQLException e) { /* Ignored */ }
	    }
	    return objectTypes;
	}
	
	/*
	 Checks if the provided action name already exists in the system to ensure the uniqueness of action names during the creation of new actions.
	@param actionName The name of the action to check for uniqueness within the system.
	@return true if the action name exists, otherwise false.
	*/
	public boolean checkIfActionNameExists(String actionName) {
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    boolean exists = false;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "SELECT count(*) FROM actions WHERE name = ?";
	        pstmt = connection.prepareStatement(query);
	        pstmt.setString(1, actionName);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            exists = rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
	        DatabaseUtility.disconnect(connection);
	    }
	    return exists;
	}
	
	/**
	 * Creates a new action in the database with the given name, description, affected object types, and logic.
	 * This supports the dynamic definition of actions within the system.
	 * @param name The name of the new action being created.
	 * @param description A description of the new action.
	 * @param objectTypeIds A list of object type IDs that the new action affects.
	 * @param logic The logic defining the new action.
	 * @return The ID of the newly created action, or null if the creation failed.
	 */
	public String createAction(String name, String description, List<Long> objectTypeIds, String logic) {
	    Connection connection = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String generatedId = null;
	    try {
	        String sql = "INSERT INTO actions (name, description, action_type, execution_logic) VALUES (?, ?, ?, ?)";
	        pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        pstmt.setString(1, name);
	        pstmt.setString(2, description);
	        // Assuming action_type is stored as a JSON string of objectTypeIds
	        pstmt.setString(3, objectTypeIds.toString());
	        pstmt.setString(4, logic);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creating action failed, no rows affected.");
	        }
	        rs = pstmt.getGeneratedKeys();
	        if (rs.next()) {
	            generatedId = rs.getString(1);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(PermissionsDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            DatabaseUtility.disconnect(connection);
	        } catch (SQLException ex) {
	            Logger.getLogger(PermissionsDAO.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    return generatedId;
	}
	
	/**
	 * Searches for an existing action by its name to prevent the creation of duplicate actions.
	 * @param actionName The name of the action being searched.
	 * @return The Action if found, null otherwise.
	 */
	public Action findActionByName(String actionName) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "SELECT * FROM actions WHERE name = ?;";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, actionName);
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            Action action = new Action();
	            action.setId(resultSet.getInt("id"));
	            action.setPermissionName(resultSet.getString("name"));
	            action.setDescription(resultSet.getString("description"));
	            // Set further properties of Action object as necessary
	            return action;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error searching action by name", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing resources", e);
	        }
	    }
	    return null;
	}
	
	/**
	 * Updates an existing action with new information, supporting corrections or changes after initial creation.
	 * @param actionId The ID of the action to be updated.
	 * @param description The new description of the action.
	 * @param affectedObjectTypes A list of object types that are affected by the action.
	 * @param logic The updated logic of the action.
	 * @return boolean indicating if the update was successful.
	 */
	public boolean updateAction(String actionId, String description, List<String> affectedObjectTypes, String logic) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "UPDATE actions SET description = ?, logic = ? WHERE id = ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, description);
	        preparedStatement.setString(2, logic);
	        preparedStatement.setString(3, actionId);
	
	        int affectedRows = preparedStatement.executeUpdate();
	        if (affectedRows > 0) {
	            // Logic to handle affectedObjectTypes here
	            return true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating action", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing preparedStatement", e);
	            }
	        }
	    }
	    return false;
	}
}
