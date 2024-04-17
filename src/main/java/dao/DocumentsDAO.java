package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class DocumentsDAO {

	
	/**
	 * This method is used in Customer Onboarding - Documentation Upload to upload necessary documentation supporting the facility's operations or compliance needs.
	 * @param facilityId String representing the unique identifier of the facility to which the document is being uploaded
	 * @param documentType DocumentType enumeration value indicating the type of the document being uploaded
	 * @param documentContent byte[] array containing the content of the document to be uploaded
	 * @param fileName String representing the name of the file being uploaded
	 * @param mimeType String indicating the MIME type of the document being uploaded
	 * @return Document The Document object after successfully uploading the document
	 */
	 public Document uploadDocument(String facilityId, Documents.DocumentType documentType, byte[] documentContent, String fileName, String mimeType) {
	     Connection connection = DatabaseUtility.connect();
	     try {
	         String sql = "INSERT INTO documents (facility_id, document_type, document_content, file_name, mime_type) VALUES (?,?,?,?,?)";
	         PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	         pstmt.setString(1, facilityId);
	         pstmt.setString(2, documentType.name());
	         pstmt.setBytes(3, documentContent);
	         pstmt.setString(4, fileName);
	         pstmt.setString(5, mimeType);
	         int affectedRows = pstmt.executeUpdate();
	         if (affectedRows == 0) {
	             throw new SQLException("Creating document failed, no rows affected.");
	         }
	         try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	             if (generatedKeys.next()) {
	                 Documents document = new Documents();
	                 document.setId(generatedKeys.getInt(1));
	                 document.setFkFacilityId(facilityId);
	                 document.setDocumentType(documentType);
	                 document.setFilePath(fileName);
	                 // Assuming the file_path is equivalent to fileName in this context
	                 return document;
	             } else {
	                 throw new SQLException("Creating document failed, no ID obtained.");
	             }
	         }
	     } catch (SQLException e) {
	         Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error uploading document", e);
	         return null;
	     } finally {
	         DatabaseUtility.disconnect(connection);
	     }
	 }
	
	/**
	 * Used in Customer Onboarding - Documentation Upload to retrieve and display a listing of documents previously uploaded for the facility.
	 * @param facilityId String representing the unique identifier of the facility for which documents are being retrieved.
	 * @return List<Documents> The list of Documents objects associated with the given facilityId.
	 */
	public List<Documents> getDocumentsByFacilityId(String facilityId) {
	    List<Documents> documents = new ArrayList<>();
	    String query = "SELECT * FROM documents WHERE fk_facility_id = ?;";
	    Connection connection = DatabaseUtility.connect();
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, facilityId);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            Documents document = new Documents();
	            document.setId(resultSet.getInt("id"));
	            document.setDocumentName(resultSet.getString("document_name"));
	            document.setDocumentType(Documents.DocumentType.valueOf(resultSet.getString("document_type").toUpperCase()));
	            document.setUploadDate(resultSet.getDate("upload_date"));
	            document.setExpiryDate(resultSet.getDate("expiry_date"));
	            document.setDocumentStatus(Documents.DocumentStatus.valueOf(resultSet.getString("document_status").toUpperCase()));
	            document.setFilePath(resultSet.getString("file_path"));
	            documents.add(document);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error retrieving documents for facilityId: " + facilityId, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return documents;
	}
	
	
	/**
	 * Deletes a document identified by its unique identifier from the database.
	 * @param documentId String representing the unique identifier of the document to be deleted.
	 * @return boolean indicating whether the deletion was successful or not.
	 */
	public boolean deleteDocument(String documentId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    boolean deletionSuccess = false;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "DELETE FROM documents WHERE id = ?;";
	
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, Integer.parseInt(documentId));
	
	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            deletionSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error deleting document with ID " + documentId, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	            }
	        }
	    }
	    return deletionSuccess;
	}
}
