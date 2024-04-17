package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class FacilitiesDAO {

	
	/**
	 * Fetches all available facility types for selection in the 'Type of Facility' dropdown.
	 * @return A list of all facility types.
	 */
	public List<Facilities.FacilityTypes> fetchAllFacilityTypes() {
	    List<Facilities.FacilityTypes> facilityTypes = new ArrayList<>();
	    Connection connection = null;
	    Statement statement = null;
	    ResultSet resultSet = null;
	    try {
	        connection = DatabaseUtility.connect();
	        statement = connection.createStatement();
	        String sql = "SELECT DISTINCT facility_types FROM facilities;";
	        resultSet = statement.executeQuery(sql);
	        while (resultSet.next()) {
	            String type = resultSet.getString("facility_types");
	            facilityTypes.add(Facilities.FacilityTypes.valueOf(type.toUpperCase()));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(FacilitiesDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (statement != null) try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
	        DatabaseUtility.disconnect(connection);
	    }
	    return facilityTypes;
	}
	
	/**
	 * Checks if a facility name already exists in the system to ensure uniqueness.
	 * @param facilityName The name of the facility to check for uniqueness.
	 * @return true if the name exists, false otherwise.
	 */
	public boolean checkFacilityNameExists(String facilityName) {
	    Connection connection = DatabaseUtility.connect();
	    try {
	        String query = "SELECT COUNT(*) AS count FROM facilities WHERE name = ?;";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, facilityName);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            int count = resultSet.getInt("count");
	            return count > 0;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error checking if facility name exists", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return false;
	}
	
	
	/**
	 * Creates a new facility record in the database with the given details.
	 * @param facilityName The name of the facility.
	 * @param location The location or address of the facility.
	 * @param facilityType The type of facility, categorized by predefined types.
	 * @param operatingHours The operating hours of the facility.
	 * @param visibilityState The visibility state of the facility.
	 * @return The created Facilities object or null if the operation fails.
	 */
	public Facilities createFacility(String facilityName, String location, Facilities.FacilityTypes facilityType, String operatingHours, Facilities.VisibilityStates visibilityState) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "INSERT INTO facilities (name, location, facility_types, operating_hours, visibility_states) VALUES (?, ?, ?::facility_types, ?, ?::visibility_states) RETURNING id";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, facilityName);
	        preparedStatement.setString(2, location);
	        preparedStatement.setString(3, facilityType.toString());
	        preparedStatement.setString(4, operatingHours);
	        preparedStatement.setString(5, visibilityState.toString());
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            Facilities facility = new Facilities();
	            facility.setId(resultSet.getInt("id"));
	            facility.setName(facilityName);
	            facility.setLocation(location);
	            facility.setFacilityType(facilityType);
	            facility.setOperatingHours(operatingHours);
	            facility.setVisibilityState(visibilityState);
	            facility.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Assuming creation timestamp is set here
	            facility.setUpdatedAt(facility.getCreatedAt()); // Assuming updated timestamp is the same as creation for new records
	            return facility;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error creating facility", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	            }
	        }
	    }
	    return null;
	}
	
	/**
	 * Retrieves a summary of the facility registration.
	 * @param facilityId The unique identifier of the facility.
	 * @return Facility object containing summary details of the facility.
	 */
	public Facilities getFacilitySummary(String facilityId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Facilities facility = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "SELECT * FROM facilities WHERE id = ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, Integer.parseInt(facilityId));
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            facility = new Facilities();
	            facility.setId(resultSet.getInt("id"));
	            facility.setName(resultSet.getString("name"));
	            facility.setLocation(resultSet.getString("location"));
	            facility.setFacilityType(Facilities.FacilityTypes.valueOf(resultSet.getString("facility_types")));
	            facility.setOperatingHours(resultSet.getString("operating_hours"));
	            facility.setCreatedAt(resultSet.getTimestamp("created_at"));
	            facility.setUpdatedAt(resultSet.getTimestamp("updated_at"));
	            facility.setVisibilityState(Facilities.VisibilityStates.valueOf(resultSet.getString("visibility_states")));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching facility summary", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }
	    return facility;
	}
	
	/**
	 * Updates the status of the facility's registration process in the database.
	 * @param facilityId The unique identifier of the facility.
	 * @param status The new status to update the facility record to.
	 * This method is used in the Customer Onboarding page's Confirmation and Next Steps section to reflect the completion of the registration process.
	 */
	public boolean updateFacilityStatus(String facilityId, String status) {
	    Connection connection = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "UPDATE facilities SET visibility_states = ? WHERE id = ?;";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, status);
	        preparedStatement.setInt(2, Integer.parseInt(facilityId));
	
	        int rowCount = preparedStatement.executeUpdate();
	        DatabaseUtility.disconnect(connection);
	        return rowCount > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        if (connection != null) {
	            DatabaseUtility.disconnect(connection);
	        }
	        return false;
	    }
	}
}
