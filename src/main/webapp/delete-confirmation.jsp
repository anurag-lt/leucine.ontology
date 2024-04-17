<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- Delete Confirmation Modal -->
<div id="deleteConfirmationModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Confirm Delete</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to delete this relation type?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-danger" id="confirmDeleteBtn">Delete</button>
      </div>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
    $(document).ready(function() {
        var relationTypeIdToDelete = null;

        // When a delete button is clicked, save the id and show modal
        $('.deleteBtn').on('click', function() {
            relationTypeIdToDelete = $(this).data('id');
            $('#deleteConfirmationModal').modal('show');
        });

        $('#confirmDeleteBtn').on('click', function() {
            if (relationTypeIdToDelete) {
                deleteRelationType(relationTypeIdToDelete);
            }
        });

        function deleteRelationType(relationTypeId) {
            $.ajax({
                url: `/api/deleteRelationType/${relationTypeId}`,
                type: 'DELETE',
                success: function(result) {
                    if (result) {
                        alert('Relation type deleted successfully.');
                        $('#deleteConfirmationModal').modal('hide');
                        // Assuming the function refetches the relation types to reflect the deletion
                        fetchRelationTypes(); 
                    } else {
                        alert('Failed to delete the relation type.');
                    }
                },
                error: function(error) {
                    console.log('Error deleting relation type:', error);
                }
            });
        }
    });
</script>