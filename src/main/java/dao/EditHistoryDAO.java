package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EditHistoryDAO {

	
	/*
	 Method to create an edit history record in the database.
	 This method captures detailed information about each edit made within the system, including the object type and property edited, the user who made the edit, a description of the change, the timestamp of the edit, and the action type. It's used in various sections to track and audit changes effectively.
	*/
	public boolean createEditHistory(ObjectType objectType, Property property, int userId, String changeDescription, Timestamp editTimestamp, EditHistory.ActionTypes actionType) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    String insertQuery = "INSERT INTO edit_history (object_type_id, property_id, user_id, change_description, edit_timestamp, action_type) VALUES (?, ?, ?, ?, ?, ?)";
	
	    try {
	        connection = DatabaseUtility.connect();
	        preparedStatement = connection.prepareStatement(insertQuery);
	        preparedStatement.setInt(1, objectType.getId());
	        preparedStatement.setInt(2, property.getId());
	        preparedStatement.setInt(3, userId);
	        preparedStatement.setString(4, changeDescription);
	        preparedStatement.setTimestamp(5, editTimestamp);
	        preparedStatement.setString(6, actionType.name());
	
	        int affectedRows = preparedStatement.executeUpdate();
	        if (affectedRows > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	
	    return false;
	}
	
	/**
	 * Fetches a detailed view of an edit history record by its ID. This method is crucial for displaying detailed change logs in the Audit Logs Review, allowing users to explore changes and interventions at a granular level.
	 * @param id The unique identifier of the edit history record to be retrieved.
	 * @return An EditHistory object filled with edit history data or null if not found.
	 */
	public EditHistory getEditHistoryById(int id) {
	    Connection connection = DatabaseUtility.connect();
	    EditHistory editHistory = null;
	    try {
	        String query = "SELECT * FROM edit_history WHERE id = ?;";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setInt(1, id);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            editHistory = new EditHistory();
	            editHistory.setId(resultSet.getInt("id"));
	            ObjectType objectType = new ObjectType();
	            objectType.setId(resultSet.getInt("object_type_id"));
	            editHistory.setObjectType(objectType);
	
	            Property property = new Property();
	            property.setId(resultSet.getInt("property_id"));
	            editHistory.setProperty(property);
	
	            editHistory.setUserId(resultSet.getInt("user_id"));
	            editHistory.setChangeDescription(resultSet.getString("change_description"));
	            editHistory.setEditTimestamp(resultSet.getTimestamp("edit_timestamp"));
	            editHistory.setActionType(EditHistory.ActionTypes.valueOf(resultSet.getString("action_type")));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error getting edit history by ID", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return editHistory;
	}
	
	/**
	 * Retrieves a list of edit history records from the database, applying optional filters.
	 * @param limit The limit on the number of edit history records to fetch.
	 * @param offset The offset from where to start fetching edit history records.
	 * @param filterBy Filter conditions such as date range, action type, and user.
	 * @return A list of edit histories that match the given criteria.
	 */
	public List<EditHistory> getEditHistories(int limit, int offset, Map<String, Object> filterBy) {
	    List<EditHistory> editHistories = new ArrayList<>();
	    Connection connection = DatabaseUtility.connect();
	    try {
	        StringBuilder query = new StringBuilder("SELECT * FROM edit_history WHERE 1=1 ");
	        filterBy.forEach((key, value) -> {
	            switch (key) {
	                case "dateRange":
	                    query.append(" AND edit_timestamp BETWEEN '" + value.get("start") + "' AND '" + value.get("end") + "'");
	                    break;
	                case "actionType":
	                    query.append(" AND action_type = '" + value + "'");
	                    break;
	                case "userId":
	                    query.append(" AND user_id = " + value);
	                    break;
	            }
	        });
	        query.append(" LIMIT ").append(limit).append(" OFFSET ").append(offset);
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query.toString());
	        while(resultSet.next()) {
	            EditHistory editHistory = new EditHistory();
	            editHistory.setId(resultSet.getInt("id"));
	            ObjectType objectType = new ObjectType(); // Initialize object type with default or null values as placeholders
	            objectType.setId(resultSet.getInt("object_type_id")); // Assuming you have a method to set object type ID
	            editHistory.setObjectType(objectType);
	            Property property = new Property(); // Similar assumption for property
	            property.setId(resultSet.getInt("property_id"));
	            editHistory.setProperty(property);
	            editHistory.setUserId(resultSet.getInt("user_id"));
	            editHistory.setChangeDescription(resultSet.getString("change_description"));
	            editHistory.setEditTimestamp(resultSet.getTimestamp("edit_timestamp"));
	            editHistory.setActionType(EditHistory.ActionTypes.valueOf(resultSet.getString("action_type")));
	            editHistories.add(editHistory);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return editHistories;
	}
}
