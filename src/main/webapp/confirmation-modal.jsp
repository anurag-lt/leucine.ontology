<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Bootstrap 5 Modal -->
<div class="modal fade" id="confirmationModal" tabindex="-1" aria-labelledby="confirmationModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered col-md-4">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="confirmationModalLabel">Confirm Your Changes</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Please review your changes before confirming.</p>
        <!-- Dynamic content related to changes will go here. For simplicity, this is not implemented as it depends on dynamic server-side data. -->
      </div>
      <div class="modal-footer">
        <button type="button" id="cancelButton" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" id="confirmButton" class="btn btn-primary">Confirm</button>
      </div>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function(){
    // Trigger modal display
    $('#confirmationModal').modal('show');

    $('#cancelButton').click(function(){
        // Closing the modal and bring user back to form without submitting changes
        $('#confirmationModal').modal('hide');
        window.location.href = 'edit-object-type.jsp?section=form'; // Redirecting user back to the form for potential revision
    });

    $('#confirmButton').click(function(){
        // Assuming '/api/updateObjectType' as the API endpoint for update operation
        var objectTypeId = ''; // This value should be dynamically set based on the object type being edited
        $.ajax({
            url: '/api/updateObjectType',
            type: 'POST',
            data: JSON.stringify({
                // Assuming required data structure for the update API request
                objectTypeId: objectTypeId,
                // Other necessary data fields go here
            }),
            contentType: "application/json",
            success: function (response) {
                // Assuming true indicates a successful update operation
                if(response) {
                    // Redirecting to the overview page with an indicator of update success
                    window.location.href = 'object-type-overview.jsp?section=update-success';
                } else {
                    alert('Update failed. Please try again.');
                    $('#confirmationModal').modal('hide');
                }
            },
            error: function () {
                alert('Error in update operation. Please try again.');
                $('#confirmationModal').modal('hide');
            }
        });
    });
});
</script>