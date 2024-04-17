<div class="modal fade" id="confirmationModal" tabindex="-1" aria-labelledby="confirmationModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="confirmationModalLabel">Confirm Changes</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>Please confirm your changes:</p>
        <!-- A summary of changes can be dynamically populated here based on what the user inputs in the form -->
        <ul>
          <li>Name: <span id="confirmName"></span></li>
          <li>Description: <span id="confirmDescription"></span></li>
          <li>Visibility: <span id="confirmVisibility"></span></li>
          <!-- Add properties and relationships as needed -->
        </ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="confirmBtn">Confirm</button>
      </div>
    </div>
  </div>
</div>

<script>
$(document).ready(function () {
  // Populate confirmation modal with the current form data
  function populateConfirmationModal() {
    $('#confirmName').text($('#objectTypeName').val());
    $('#confirmDescription').text($('#objectTypeDescription').val());
    var visibilityText = $('#visibilityPublic').is(':checked') ? 'Public' : 'Private';
    $('#confirmVisibility').text(visibilityText);
    // Properties and Relationships can be added similarly
  }

  $('#editObjectTypeForm').on('submit', function(e) {
    e.preventDefault();
    populateConfirmationModal();
    $('#confirmationModal').modal('show');
  });
  
  $('#confirmBtn').click(function() {
    // Assuming an API endpoint "/api/updateObjectType" for updating the object type details
    $.ajax({
      url: '/api/updateObjectType',
      method: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({
        objectTypeId: 'obj-123',
        name: $('#objectTypeName').val(),
        description: $('#objectTypeDescription').val(),
        visibility: $('#visibilityPublic').is(':checked'),
        // Assuming propertyIds and relationshipIds are collected and structured correctly
        propertyIds: $('#objectTypeProperties').val(),
        relationshipIds: $('#objectTypeRelationships').val()
      }),
      success: function(response) {
        $('#confirmationModal').modal('hide');
        if (response) {
            // Navigate to object-type-overview?section=update-success on successful update
            window.location.href = '/object-type-overview?section=update-success';
        } else {
            alert('Update failed, please try again.');
        }
      },
      error: function() {
        $('#confirmationModal').modal('hide');
        alert('An error occurred during the update. Please try again.');
      }
    });
  });
  
  // Cancel button logic, if needed, though bootstrap handles modal close by default
});
</script>