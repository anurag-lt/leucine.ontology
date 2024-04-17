package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.List;
import java.util.ArrayList;


public class ActionsDAO {

	
	/*
	 Inserts a new action into the database with detailed parameters provided by the user.
	 @param name The human-readable name for the action, serving as a unique identifier.
	 @param description A brief summary of what the action is intended to do within the system.
	 @param objectTypeIds A list of IDs for the object types that the action can apply to, facilitating a customizable automation process.
	 @param logic A String representation of the action's logic, detailing the conditions and outcomes for its execution.
	 @return boolean indicating the success of the action insertion.
	*/
	public boolean createAction(String name, String description, List<Integer> objectTypeIds, String logic) {
	  Connection connection = DatabaseUtility.connect();
	  PreparedStatement preparedStatement = null;
	  try {
	    String sql = "INSERT INTO actions (name, description, logic) VALUES (?, ?, ?)";
	    preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    preparedStatement.setString(1, name);
	    preparedStatement.setString(2, description);
	    preparedStatement.setString(3, logic);
	    int affectedRows = preparedStatement.executeUpdate();
	    if (affectedRows == 0) {
	      throw new SQLException("Creating action failed, no rows affected.");
	    }
	
	    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	      if (generatedKeys.next()) {
	        long actionId = generatedKeys.getLong(1);
	        for (Integer objectTypeId : objectTypeIds) {
	          sql = "INSERT INTO ActionObjectTypes (action_id, object_type_id) VALUES (?, ?)";
	          preparedStatement = connection.prepareStatement(sql);
	          preparedStatement.setLong(1, actionId);
	          preparedStatement.setInt(2, objectTypeId);
	          preparedStatement.executeUpdate();
	        }
	      } else {
	        throw new SQLException("Creating action failed, no ID obtained.");
	      }
	    }
	    return true;
	  } catch (SQLException ex) {
	    Logger.getLogger(ActionsDao.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	  } finally {
	    DatabaseUtility.disconnect(connection);
	    try {
	      if (preparedStatement != null) preparedStatement.close();
	    } catch (SQLException e) {
	      Logger.getLogger(ActionsDao.class.getName()).log(Level.SEVERE, null, e);
	    }
	  }
	}
	
	/*
	 Used in the 'Action Details Form' section of the 'Create Action' page to verify that the given action name does not already exist in the system, ensuring uniqueness.
	*/
	public boolean checkIfActionNameExists(String actionName) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    boolean exists = false;
	
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "SELECT COUNT(*) AS count FROM actions WHERE name = ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, actionName);
	
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            exists = resultSet.getInt("count") > 0;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error checking if action name exists", e);
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            DatabaseUtility.disconnect(connection);
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing resources", e);
	        }
	    }
	    return exists;
	}
	
	/**
	 * Used in the 'Action Details Form' section of the 'Create Action' page to populate the 'Affected Object Types' dropdown, allowing the administrator to select which object types an action applies to.
	 * @return A list of all available ObjectTypes within the system.
	 */
	public List<ObjectTypes> fetchObjectTypes() {
	    List<ObjectTypes> objectTypes = new ArrayList<>();
	    String query = "SELECT id, name, description FROM object_types";
	    try (Connection connection = DatabaseUtility.connect();
	         Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {
	        while (rs.next()) {
	            ObjectTypes objectType = new ObjectTypes();
	            objectType.setId(rs.getInt("id"));
	            objectType.setName(rs.getString("name"));
	            objectType.setDescription(rs.getString("description"));
	            objectTypes.add(objectType);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching object types", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return objectTypes;
	}
	
	/*
	 Method to update an existing action with newly provided details.
	 Updates the action based on the given identifier and other parameters representing updated values.
	*/
	public boolean updateAction(int id, String name, String description, Actions.ActionTypes actionType, String triggerEvent, Timestamp executionTime, Timestamp createdAt, Timestamp lastModifiedAt, ObjectTypes fkObjectType) {
	    Connection connection = DatabaseUtility.connect();
	    try {
	        String query = "UPDATE actions SET name=?, description=?, action_type=?, trigger_event=?, execution_time=?, created_at=?, last_modified_at=?, fk_object_type_id=? WHERE id=?";
	        PreparedStatement pstmt = connection.prepareStatement(query);
	        pstmt.setString(1, name);
	        pstmt.setString(2, description);
	        pstmt.setString(3, actionType.name());
	        pstmt.setString(4, triggerEvent);
	        pstmt.setTimestamp(5, executionTime);
	        pstmt.setTimestamp(6, createdAt);
	        pstmt.setTimestamp(7, lastModifiedAt);
	        pstmt.setInt(8, fkObjectType.getId());
	        pstmt.setInt(9, id);
	        int result = pstmt.executeUpdate();
	        return result > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(ActionsDAO.class.getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	}
}
