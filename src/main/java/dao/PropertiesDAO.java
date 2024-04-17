package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class PropertiesDAO {

	
	/**
	 * Retrieves all available object types for selection in the 'Affected Object Types' dropdown during the creation of a new action.
	 * It is used in the 'Create Action' section of the 'Action Management Module' page to allow users to specify which object types an action affects.
	 * @return A list of all available ObjectTypes.
	 */
	public List<ObjectTypes> fetchObjectTypes() {
	    Connection connection = null;
	    List<ObjectTypes> objectTypesList = new ArrayList<>();
	    try {
	        connection = DatabaseUtility.connect();
	        PreparedStatement pst = connection.prepareStatement("SELECT * FROM object_types;");
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	            ObjectTypes objectType = new ObjectTypes();
	            objectType.setId(rs.getInt("id"));
	            objectType.setName(rs.getString("name"));
	            objectType.setDescription(rs.getString("description"));
	            objectType.setVisibilityState(ObjectTypes.VisibilityStates.valueOf(rs.getString("visibility_state").toUpperCase()));
	            objectTypesList.add(objectType);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching object types", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return objectTypesList;
	}
	
	
	/**
	 * Checks if the property name provided by the user is unique within the specified object type.
	 * @param propertyName The name of the property being created.
	 * @param objectTypeId The identifier of the object type that the property belongs to.
	 * @return true if the property name is unique; false otherwise.
	 */
	public boolean checkPropertyNameUniqueness(String propertyName, String objectTypeId) {
	    Connection connection = DatabaseUtility.connect();
	    try {
	        String query = "SELECT count(*) FROM properties WHERE name = ? AND fk_object_type_id = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, propertyName);
	            pstmt.setString(2, objectTypeId);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    int count = rs.getInt(1);
	                    return count == 0;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return false;
	}
	
	
	/**
	 * Creates a new property with the given details and associates it with an object type.
	 * @param propertyName The name of the property being created.
	 * @param propertyType The data type of the property.
	 * @param unit The unit of measurement for the property, applicable if the type is number.
	 * @param visibility The visibility state of the property.
	 * @param objectTypeId The identifier of the object type that the property belongs to.
	 * @return The created property object or null if the creation failed.
	 */
	public Properties createProperty(String propertyName, String propertyType, String unit, String visibility, String objectTypeId) {
	    Properties property = new Properties();
	    Connection connection = DatabaseUtility.connect();
	    try {
	        String sql = "INSERT INTO properties (name, property_type, unit, visibility, fk_object_type_id) VALUES (?, ?, ?, ?, ?)";
	        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setString(1, propertyName);
	            statement.setString(2, propertyType);
	            statement.setString(3, unit);
	            statement.setString(4, visibility);
	            statement.setInt(5, Integer.parseInt(objectTypeId));
	
	            int affectedRows = statement.executeUpdate();
	            if (affectedRows == 0) {
	                throw new SQLException("Creating property failed, no rows affected.");
	            }
	
	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    property.setId(generatedKeys.getInt(1));
	                    property.setName(propertyName);
	                    property.setPropertyType(Properties.PropertyTypes.valueOf(propertyType.toUpperCase()));
	                    property.setDefaultValue(unit); // Assuming 'defaultValue' field is used to store 'unit'.
	                    property.setVisibilityState(Properties.VisibilityStates.valueOf(visibility.toUpperCase()));
	                    property.setFkObjectType(new ObjectTypes());
	                    property.getFkObjectType().setId(Integer.parseInt(objectTypeId));
	                } else {
	                    throw new SQLException("Creating property failed, no ID obtained.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        property = null;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return property;
	}
}
