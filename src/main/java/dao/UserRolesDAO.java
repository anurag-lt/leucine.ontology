package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserRolesDAO {

	
	/**
	 * Fetches a list of all available roles that can be assigned to users within the facility.
	 * This method is used in the 'Customer Onboarding' page, specifically within the 'User Roles and Permissions' modal,
	 * to populate the dropdown with available roles for assignment.
	 *
	 * @return List<Role> A list of Role objects representing all user roles available for assignment within a facility.
	 */
	public List<Role> findAllRoles() {
	    List<Role> roles = new ArrayList<>();
	    Connection connection = null;
	    Statement stmt = null;
	    try {
	        connection = DatabaseUtility.connect();
	        stmt = connection.createStatement();
	        String query = "SELECT * FROM user_roles";
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	            Role role = new Role();
	            role.setId(rs.getInt("id"));
	            role.setRoleName(rs.getString("role_name"));
	            role.setDescription(rs.getString("description"));
	            role.setCreatedAt(rs.getTimestamp("created_at"));
	            role.setModifiedAt(rs.getTimestamp("modified_at"));
	            role.setStatus(Role.DocumentStatus.valueOf(rs.getString("status").toUpperCase()));
	            roles.add(role);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(UserRolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException ex) {
	                Logger.getLogger(UserRolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
	    return roles;
	}
	
	/**
	 * Saves the mapping between roles and permissions for a specific facility as part of the onboarding process.
	 * @param facilityId The unique identifier of the facility.
	 * @param rolePermissionsMap A mapping between role IDs and lists of permission IDs.
	 */
	public boolean saveRolePermissions(int facilityId, Map<Integer, List<Integer>> rolePermissionsMap) {
	    Connection connection = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "INSERT INTO facility_role_permissions (facility_id, role_id, permission_id) VALUES (?, ?, ?)";
	        connection.setAutoCommit(false);
	        PreparedStatement pstmt = connection.prepareStatement(sql);
	        for (Map.Entry<Integer, List<Integer>> entry : rolePermissionsMap.entrySet()) {
	            for(Integer permissionId : entry.getValue()) {
	                pstmt.setInt(1, facilityId);
	                pstmt.setInt(2, entry.getKey());
	                pstmt.setInt(3, permissionId);
	                pstmt.addBatch();
	            }
	        }
	        pstmt.executeBatch();
	        connection.commit();
	        return true;
	    } catch (SQLException e) {
	        if (connection != null) {
	            try {
	                connection.rollback();
	            } catch (SQLException ex) {
	                Logger.getLogger(UserRolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	        Logger.getLogger(UserRolesDAO.class.getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	}
	
	
	/**
	 * Updates existing roles and permissions mapping for a facility.
	 * This method is used during the customer onboarding process in the 'User Roles and Permissions' modal when modifying the roles and permissions associated with a facility.
	 *
	 * @param facilityId The unique identifier for the facility where roles and permissions need to be updated.
	 * @param rolePermissionsMap A mapping between role IDs and corresponding permission IDs to update.
	 * @return boolean indicating whether the update was successful.
	 */
	public boolean updateRolePermissions(int facilityId, Map<Integer, List<Integer>> rolePermissionsMap) {
	    Connection connection = null;
	    try {
	        connection = DatabaseUtility.connect();
	        // Assuming a table structure 'role_permissions' with columns 'facility_id', 'role_id', 'permission_id'
	        // Begin transaction
	        connection.setAutoCommit(false);
	        String deleteQuery = "DELETE FROM role_permissions WHERE facility_id = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
	            pstmt.setInt(1, facilityId);
	            pstmt.executeUpdate();
	        }
	
	        String insertQuery = "INSERT INTO role_permissions (facility_id, role_id, permission_id) VALUES (?, ?, ?)";
	        for (Map.Entry<Integer, List<Integer>> entry : rolePermissionsMap.entrySet()) {
	            for (Integer permissionId : entry.getValue()) {
	                try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
	                    pstmt.setInt(1, facilityId);
	                    pstmt.setInt(2, entry.getKey());
	                    pstmt.setInt(3, permissionId);
	                    pstmt.executeUpdate();
	                }
	            }
	        }
	        // Commit transaction
	        connection.commit();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (connection != null) {
	            try {
	                connection.rollback();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	}
}
