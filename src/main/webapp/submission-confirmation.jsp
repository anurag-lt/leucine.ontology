<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- Submission Confirmation Modal -->
<div class="modal fade" id="submissionConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="submissionConfirmationModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="submissionConfirmationModalLabel">Submission Confirmation</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <p>Your new object type has been successfully created.</p>
        </div>
        <div class="modal-footer">
            <button type="button" id="createAnotherObjectType" class="btn btn-secondary">Create Another Object Type</button>
            <button type="button" id="goToObjectOverview" class="btn btn-primary">Go to Object Type Overview</button>
        </div>
    </div>
  </div>
</div>

<script>
$(document).ready(function(){

    // Handle "Create Another Object Type" button
    $('#createAnotherObjectType').click(function() {
        $('#submissionConfirmationModal').modal('hide');
        // Reset form fields logic could be invoked here, assuming a function resetObjectTypeForm exists
        // Example: resetObjectTypeForm();
        window.location.href = '/create-object-type.jsp?section=object-type-definition-form'; // Assuming the URL pattern, adjust accordingly
    });

    // Handle "Go to Object Type Overview" button
    $('#goToObjectOverview').click(function() {
        window.location.href = '/object-type-overview.jsp'; // Assuming the URL pattern, adjust accordingly
    });
    
});
</script>