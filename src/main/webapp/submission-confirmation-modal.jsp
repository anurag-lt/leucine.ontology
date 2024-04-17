<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- This JSP assumes Bootstrap is loaded in the parent or main JSP file -->

<div class="modal fade" id="submissionConfirmationModal" tabindex="-1" aria-labelledby="confirmationModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="confirmationModalLabel">Submission Confirmation</h5>
        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Your Relation Type has been successfully created.
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="closeModal">Close</button>
        <button type="button" class="btn btn-primary" id="viewRelationType">View Relation Type</button>
        <button type="button" class="btn btn-primary" id="createAnotherRelationType">Create Another Relation Type</button>
      </div>
    </div>
  </div>
</div>

<script>
    $(document).ready(function() {
        let newRelationTypeId;

        // Extract the newlyCreatedRelationTypeId from URL if exists
        newRelationTypeId = new URLSearchParams(window.location.search).get('newId');

        // 'View Relation Type' button click event
        $('#viewRelationType').on('click', function() {
            if(newRelationTypeId) {
                window.location.href = `/relation-type-overview?section=details&id=${newRelationTypeId}`;
            }
        });

        // 'Create Another Relation Type' button click event
        $('#createAnotherRelationType').on('click', function() {
            $.ajax({
                url: '/api/relationTypes/resetForm',
                method: 'POST',
                success: function(response) {
                    if(response) {
                        // Assuming resetRelationTypeForm() clears the form and returns true on success
                        $('#relationTypeForm').trigger("reset"); // Reset the form
                        $('#submissionConfirmationModal').modal('hide');
                    }
                }
            });
        });

        // 'Close' button click event which redirects to Relation Type Overview
        $('#closeModal').on('click', function() {
            $('#submissionConfirmationModal').modal('hide');
            window.location.href = "/relation-type-overview";
        });

        // Optionally, if the modal should automatically show up when the page is accessed with a newId
        if(newRelationTypeId) {
            $('#submissionConfirmationModal').modal('show');
        }
    });
</script>