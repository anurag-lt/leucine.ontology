package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



public class BusinessRulesDAO {

	
	/**
	 * Retrieves a list of all available object types for selection in the 'Relation Type Definition Form' dropdown.
	 * @return List<ObjectType> The list of all object types available.
	 */
	public List<ObjectTypeEnum> findAllObjectTypes() {
	    List<ObjectTypeEnum> objectTypes = new ArrayList<>();
	    Connection connection = DatabaseUtility.connect();
	    try {
	        Statement statement = connection.createStatement();
	        String query = "SELECT id, name FROM object_types";
	        ResultSet resultSet = statement.executeQuery(query);
	        while (resultSet.next()) {
	            ObjectTypeEnum objectType = ObjectTypeEnum.valueOf(resultSet.getString("name").toUpperCase());
	            objectTypes.add(objectType);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return objectTypes;
	}
	
	/*
	 Checks if a RelationType with the given name already exists in the database to prevent duplication.
	 Parameters include the relation type name (String).
	 Returns true if the name exists, false otherwise.
	*/
	public boolean checkRelationTypeNameExists(String name) {
	  Connection connection = null;
	  PreparedStatement preparedStatement = null;
	  ResultSet resultSet = null;
	  String query = "SELECT COUNT(*) FROM relation_types WHERE name = ?;";
	  try {
	    connection = DatabaseUtility.connect();
	    preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setString(1, name);
	    resultSet = preparedStatement.executeQuery();
	    if(resultSet.next()) {
	      int count = resultSet.getInt(1);
	      return count > 0;
	    }
	  } catch (SQLException e) {
	    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error checking relation type name exists", e);
	  } finally {
	    if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
	    if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
	    DatabaseUtility.disconnect(connection);
	  }
	  return false;
	}
	
	/**
	 * Creates a new relation type with specified details including constraints.
	 * @param name Name of the new relation type.
	 * @param description A detailed description of the relation type.
	 * @param sourceObjectId The unique identifier for the source object type in the relation.
	 * @param targetObjectId The unique identifier for the target object type in the relation.
	 * @param constraints List of constraints applied to the relation type.
	 * @return The id of the newly created relation type or -1 if the operation fails.
	 */
	public long createRelationType(String name, String description, long sourceObjectId, long targetObjectId, List<RelationConstraint> constraints) {
	    Connection connection = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "INSERT INTO relation_types (name, description, fk_source_object_id, fk_target_object_id) VALUES (?, ?, ?, ?) RETURNING id;";
	    try {
	        pstmt = connection.prepareStatement(sql);
	        pstmt.setString(1, name);
	        pstmt.setString(2, description);
	        pstmt.setLong(3, sourceObjectId);
	        pstmt.setLong(4, targetObjectId);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            long relationTypeId = rs.getLong(1);
	            // Insert relation constraints here, if applicable
	            // For each RelationConstraint in constraints, insert into the database
	            DatabaseUtility.disconnect(connection);
	            return relationTypeId;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error creating relation type", e);
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            DatabaseUtility.disconnect(connection);
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing resources", e);
	        }
	    }
	    return -1;
	}
}
