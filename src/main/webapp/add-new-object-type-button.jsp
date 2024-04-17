<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addObjectTypeModal">
  Add New Object Type
</button>

<!-- Modal -->
<div class="modal fade" id="addObjectTypeModal" tabindex="-1" role="dialog" aria-labelledby="addObjectTypeModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="addObjectTypeModalLabel">Add New Object Type</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="newObjectTypeForm">
          <div class="form-group">
            <label for="objectTypeName">Name</label>
            <input type="text" class="form-control" id="objectTypeName" placeholder="Enter Object Type Name" required>
          </div>
          <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" id="description" placeholder="Enter Description" required></textarea>
          </div>
          <!-- Dropdown for base properties could be added here -->
          <div class="form-group">
            <button type="submit" class="btn btn-success">Create</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<script>
  $(document).ready(function () {
    $('#newObjectTypeForm').submit(function (e) {
      e.preventDefault();

      var name = $('#objectTypeName').val().trim();
      var description = $('#description').val().trim();

      if (!name || !description) {
        alert('Both Name and Description are required fields.');
        return;
      }

      // Call to check for the uniqueness of the name
      $.ajax({
        url: '/api/objectTypes/checkNameUniqueness',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify({ name: name }),
        success: function (response) {
          if(response.isUnique) {
            // Proceed to create new object type
            $.ajax({
              url: '/api/objectTypes/create',
              type: 'POST',
              contentType: "application/json",
              data: JSON.stringify({ name: name, description: description }),
              success: function (newObjectType) {
                alert('New Object Type successfully created.');
                $('#addObjectTypeModal').modal('hide');
                // Refresh the object type list, assuming a function fetchObjectTypes() in object-type-list.jsp that does this.
                fetchObjectTypes();
              },
              error: function () {
                alert('Failed to create new Object Type.');
              }
            });
          } else {
            alert('The name for the new Object Type must be unique.');
          }
        },
        error: function () {
          alert('Error checking name uniqueness.');
        }
      });
    });
  });
</script>