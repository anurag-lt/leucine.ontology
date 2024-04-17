package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;



public class EditHistoriesDAO {

	
	/**
	 * Fetches a paginated and filtered list of edit history records from the database based on specified criteria.
	 * @param limit The maximum number of edit history records to fetch.
	 * @param offset The starting index from where to fetch the records.
	 * @param filterByAction Filters edit history records by the type of action performed.
	 * @param filterByModule Filters edit history records by the system module in which the edit was made.
	 * @param fromDate The starting date for fetching records.
	 * @param toDate The ending date for fetching records.
	 * @return A list of EditHistories objects.
	 */
	public List<EditHistories> fetchEditHistories(int limit, int offset, String filterByAction, String filterByModule, LocalDate fromDate, LocalDate toDate) {
	    List<EditHistories> editHistoriesList = new ArrayList<>();
	    Connection connection = DatabaseUtility.connect();
	    try {
	        PreparedStatement ps = connection.prepareStatement("SELECT * FROM edit_histories WHERE action_type = ? AND module = ? AND timestamp >= ? AND timestamp <= ? LIMIT ? OFFSET ?");
	        ps.setString(1, filterByAction);
	        ps.setString(2, filterByModule);
	        ps.setDate(3, java.sql.Date.valueOf(fromDate));
	        ps.setDate(4, java.sql.Date.valueOf(toDate));
	        ps.setInt(5, limit);
	        ps.setInt(6, offset);
	
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            EditHistories history = new EditHistories();
	            history.setId(rs.getInt("id"));
	            ObjectTypes objectType = new ObjectTypes(); // Assuming ObjectTypes is properly defined with setters
	            objectType.setId(rs.getInt("object_type_id"));
	            history.setObjectType(objectType);
	            history.setUserId(rs.getInt("user_id"));
	            history.setDescription(rs.getString("description"));
	            history.setTimestamp(rs.getTimestamp("timestamp"));
	            editHistoriesList.add(history);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(EditHistoriesDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return editHistoriesList;
	}
	
	/**
	 * Counts the total number of edit history records matching the specified filters.
	 * This method aids in pagination by calculating the total number of pages required to display all relevant records.
	 * @param filterByAction Filters edit history records by the type of action performed.
	 * @param filterByModule Filters edit history records by the system module in which the edit was made.
	 * @param fromDate The starting date for fetching records.
	 * @param toDate The ending date for fetching records.
	 * @return The total count of edit history records matching the filters.
	 */
	public int countFilteredEditHistories(String filterByAction, String filterByModule, LocalDate fromDate, LocalDate toDate) {
	    Connection connection = DatabaseUtility.connect();
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    int count = 0;
	    try {
	        String query = "SELECT COUNT(*) AS total FROM edit_histories WHERE action_type LIKE ? AND module LIKE ? AND timestamp >= ? AND timestamp <= ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, '%' + filterByAction + '%');
	        preparedStatement.setString(2, '%' + filterByModule + '%');
	        preparedStatement.setDate(3, java.sql.Date.valueOf(fromDate));
	        preparedStatement.setDate(4, java.sql.Date.valueOf(toDate));
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            count = resultSet.getInt("total");
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.closeQuietly(resultSet);
	        DatabaseUtility.closeQuietly(preparedStatement);
	        DatabaseUtility.disconnect(connection);
	    }
	    return count;
	}
	
	/**
	 * Retrieves detailed information for a specific edit history record identified by its unique ID.
	 * @param editHistoryId The unique identifier of the edit history record.
	 * @return EditHistories An object containing the details of the specified edit history record,
	 * or null if not found.
	 */
	public EditHistories fetchEditHistoryDetails(int editHistoryId) {
	    Connection connection = DatabaseUtility.connect();
	    try {
	        String query = "SELECT * FROM edit_histories WHERE id = ?;";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setInt(1, editHistoryId);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            EditHistories editHistory = new EditHistories();
	            editHistory.setId(resultSet.getInt("id"));
	            ObjectTypes objectType = new ObjectTypes();
	            objectType.setId(resultSet.getInt("object_type_id"));
	            editHistory.setObjectType(objectType);
	            editHistory.setUserId(resultSet.getInt("user_id"));
	            editHistory.setDescription(resultSet.getString("description"));
	            editHistory.setTimestamp(resultSet.getTimestamp("timestamp"));
	            return editHistory;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching edit history details", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return null;
	}
}
