<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Edit Relation Type Modal -->
<div id="editRelationTypeModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Edit Relation Type</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="editRelationTypeForm">
          <div class="form-group">
            <label for="relationTypeName">Name</label>
            <input type="text" class="form-control" id="relationTypeName" placeholder="Name" required>
          </div>
          <div class="form-group">
            <label for="relationTypeDescription">Description</label>
            <textarea class="form-control" id="relationTypeDescription" placeholder="Description" required></textarea>
          </div>
          <div class="form-group">
            <label for="sourceObjectType">Source Object Type</label>
            <select class="form-control" id="sourceObjectType" required>
              <!-- Options will be filled by JS -->
            </select>
          </div>
          <div class="form-group">
            <label for="targetObjectType">Target Object Type</label>
            <select class="form-control" id="targetObjectType" required>
              <!-- Options will be filled by JS -->
            </select>
          </div>
          <input type="hidden" id="relationTypeId" />
          <button type="submit" class="btn btn-primary">Save changes</button>
        </form>
      </div>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
    $(document).ready(function() {
        $('#editRelationTypeModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget);
            var relationTypeId = button.data('id'); // Extract info from data-* attributes
            fetchRelationTypeDetails(relationTypeId);
        });

        $('#editRelationTypeForm').on('submit', function(e) {
            e.preventDefault();
            updateRelationType();
        });

        function fetchRelationTypeDetails(relationTypeId) {
            // Assuming API end-point
            $.ajax({
                url: `/api/relationTypes/${relationTypeId}`,
                type: "GET",
                success: function(relationType) {
                    $('#relationTypeName').val(relationType.name);
                    $('#relationTypeDescription').val(relationType.description);
                    $('#relationTypeId').val(relationType.id);
                    // Invoke another function to populate dropdowns
                    populateObjectTypes(relationType.sourceObjectId, relationType.targetObjectId);
                },
                error: function(error) {
                    console.log("Error fetching relation type details:", error);
                }
            });
        }

        function populateObjectTypes(selectedSourceId, selectedTargetId) {
            // Dummy API call for object types
            $.ajax({
                url: "/api/objectTypes",
                type: "GET",
                success: function(objectTypes) {
                    var sourceDropdown = $('#sourceObjectType');
                    var targetDropdown = $('#targetObjectType');
                    objectTypes.forEach(function(type) {
                        sourceDropdown.append($('<option>', {
                            value: type.id,
                            text: type.name,
                            selected: type.id === selectedSourceId
                        }));
                        targetDropdown.append($('<option>', {
                            value: type.id,
                            text: type.name,
                            selected: type.id === selectedTargetId
                        }));
                    });
                },
                error: function(error) {
                    console.log("Error fetching object types:", error);
                }
            });
        }

        function updateRelationType() {
            var relationTypeId = $('#relationTypeId').val();
            var data = {
                name: $('#relationTypeName').val(),
                description: $('#relationTypeDescription').val(),
                sourceObjectTypeId: $('#sourceObjectType').val(),
                targetObjectTypeId: $('#targetObjectType').val()
            };

            $.ajax({
                url: `/api/updateRelationType/${relationTypeId}`,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function(result) {
                    if(result) {
                        alert("Relation type updated successfully.");
                        $('#editRelationTypeModal').modal('hide');
                        // Refresh the parent table or page as needed
                    } else {
                        alert("Failed to update relation type.");
                    }
                },
                error: function(error) {
                    console.log("Error updating relation type:", error);
                    alert("Error updating relation type.");
                }
            });
        }
    });
</script>