package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.List;


public class RolesDAO {

	
	/**
	 * Fetches a list of all available roles that can be assigned to users within the facility.
	 * It populates the roles selection dropdown in the Customer Onboarding page during 
	 * the User Roles and Permissions configuration.
	 *
	 * @return A list of all roles within the system.
	 */
	public List<Roles> findAllRoles() {
	    List<Roles> resultList = new ArrayList<>();
	    String query = "SELECT * FROM roles";
	    Connection connection = DatabaseUtility.connect();
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	         ResultSet resultSet = preparedStatement.executeQuery()) {
	        while (resultSet.next()) {
	            Roles role = new Roles();
	            role.setId(resultSet.getInt("id"));
	            role.setName(resultSet.getString("name"));
	            role.setDescription(resultSet.getString("description"));
	            role.setCreatedAt(resultSet.getTimestamp("created_at"));
	            role.setLastModifiedAt(resultSet.getTimestamp("last_modified_at"));
	            role.setVisibilityState(Roles.VisibilityState.valueOf(resultSet.getString("visibility_state").toUpperCase()));
	            resultList.add(role);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return resultList;
	}
	
	/**
	 * Retrieves a list of permissions associated with a given roleId.
	 * Used in the Customer Onboarding - User Roles and Permissions to fetch permissions for role setup.
	 * @param roleId The unique identifier of the role for which permissions are being fetched.
	 * @return List<Permission> The list of permissions associated with the role.
	 */
	public List<Permission> findPermissionsByRoleId(int roleId) {
	    List<Permission> permissions = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "SELECT * FROM permissions WHERE role_id = ?";
	        statement = connection.prepareStatement(query);
	        statement.setInt(1, roleId);
	        resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            Permission permission = new Permission();
	            permission.setId(resultSet.getInt("id"));
	            permission.setPermissionName(resultSet.getString("permission_name"));
	            permission.setPermissionType(resultSet.getString("permission_type"));
	            permission.setModule(resultSet.getString("module"));
	            permissions.add(permission);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching permissions by roleId", e);
	    } finally {
	        DatabaseUtility.disconnect(resultSet, statement, connection);
	    }
	    return permissions;
	}
	
	/**
	 * Saves the mapping between roles and permissions for a specific facility.
	 * @param facilityId The unique identifier of the facility to which the roles and permissions are being assigned.
	 * @param rolePermissionsMap A mapping between role IDs and a List of permission IDs representing the permissions assigned to that role.
	 * @return boolean indicating whether the operation was successful.
	 */
	public boolean saveRolePermissions(int facilityId, Map<Integer, List<Integer>> rolePermissionsMap) {
	    Connection connection = null;
	    try {
	        connection = DatabaseUtility.connect();
	        connection.setAutoCommit(false);
	        for (Map.Entry<Integer, List<Integer>> entry : rolePermissionsMap.entrySet()) {
	            for (Integer permissionId : entry.getValue()) {
	                String sql = "INSERT INTO role_permissions (role_id, permission_id, facility_id) VALUES (?, ?, ?)";
	                try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                    statement.setInt(1, entry.getKey());
	                    statement.setInt(2, permissionId);
	                    statement.setInt(3, facilityId);
	                    statement.executeUpdate();
	                }
	            }
	        }
	        connection.commit();
	        return true;
	    } catch (SQLException e) {
	        if (connection != null) {
	            try {
	                connection.rollback();
	            } catch (SQLException ex) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	}
	
	/**
	 * Updates the permissions assigned to roles for a specified facility.
	 * @param facilityId The unique identifier of the facility.
	 * @param rolePermissionsMap A map between role IDs and lists of permission IDs.
	 * @return boolean indicating success or failure of the update operation.
	 */
	public boolean updateRolePermissions(int facilityId, Map<Integer, List<Integer>> rolePermissionsMap) {
	    Connection connection = null;
	    try {
	        connection = DatabaseUtility.connect();
	        // Assuming a table 'facility_role_permissions' maps roles to permissions for a facility
	        // Start transaction
	        connection.setAutoCommit(false);
	        // Deleting existing permissions for roles related to the facility
	        String deleteQuery = "DELETE FROM facility_role_permissions WHERE facility_id = ?";
	        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
	            deleteStmt.setInt(1, facilityId);
	            deleteStmt.executeUpdate();
	        }
	        // Inserting updated permissions
	        String insertQuery = "INSERT INTO facility_role_permissions (facility_id, role_id, permission_id) VALUES (?, ?, ?)";
	        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
	            for (Map.Entry<Integer, List<Integer>> entry : rolePermissionsMap.entrySet()) {
	                for (Integer permissionId : entry.getValue()) {
	                    insertStmt.setInt(1, facilityId);
	                    insertStmt.setInt(2, entry.getKey());
	                    insertStmt.setInt(3, permissionId);
	                    insertStmt.addBatch();
	                }
	            }
	            insertStmt.executeBatch();
	        }
	        // Commit transaction
	        connection.commit();
	        return true;
	    } catch (SQLException e) {
	        if (connection != null) {
	            try {
	                connection.rollback();
	            } catch (SQLException ex) {
	                Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, "Transaction rollback failed", ex);
	            }
	        }
	        Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, "Failed to update role permissions", e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	}
}
