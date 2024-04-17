<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form id="relationTypeForm" class="col-md-8">
    <div class="mb-3">
        <label for="relationTypeName" class="form-label">Name</label>
        <input type="text" class="form-control" id="relationTypeName" placeholder="Relation Type Name" required>
    </div>
    <div class="mb-3">
        <label for="relationDescription" class="form-label">Description</label>
        <textarea class="form-control" id="relationDescription" placeholder="Description" required></textarea>
    </div>
    <div class="mb-3">
        <label for="sourceObjectType" class="form-label">Source Object Type</label>
        <select class="form-select" id="sourceObjectType" required></select>
    </div>
    <div class="mb-3">
        <label for="targetObjectType" class="form-label">Target Object Type</label>
        <select class="form-select" id="targetObjectType" required></select>
    </div>
    
    <!-- Placeholder for Constraints, can be checkboxes, dropdowns based on requirements but not detailed in requirement -->
    
    <button type="button" class="btn btn-primary" id="saveButton">Save</button>
    <button type="button" class="btn btn-secondary" id="cancelButton">Cancel</button>
</form>

<script>
$(document).ready(function() {
    // Placeholder APIs
    const findAllObjectTypesURL = '/api/objectTypes';
    const createRelationTypeURL = '/api/relationTypes';
    const checkNameExistsURL = '/api/relationTypes/nameExists';
    
    // Function to fetch and append object types to select dropdown
    function fetchAndAppendObjectTypes() {
        $.get(findAllObjectTypesURL, function(data) {
            data.forEach(function(objectType) {
                $('#sourceObjectType, #targetObjectType').append(`<option value="${objectType.id}">${objectType.name}</option>`);
            });
        });
    }
    
    // Initial fetch for object types
    fetchAndAppendObjectTypes();
    
    // Save button click event
    $('#saveButton').click(function() {
        let name = $('#relationTypeName').val();
        let description = $('#relationDescription').val();
        let sourceObjectId = $('#sourceObjectType').val();
        let targetObjectId = $('#targetObjectType').val();
        
        // Basic Validation - Utilize HTML5 validation for simplicity in this example
        if(name && description && sourceObjectId && targetObjectId) {
            $.ajax({
                url: checkNameExistsURL,
                type: 'POST',
                data: JSON.stringify({name: name}),
                contentType: "application/json",
                success: function(data) {
                    if(!data) {
                        // If name doesn't exist, go ahead and create the relation type
                        $.ajax({
                            url: createRelationTypeURL,
                            type: 'POST',
                            data: JSON.stringify({
                                name: name,
                                description: description,
                                sourceObjectId: sourceObjectId,
                                targetObjectId: targetObjectId,
                                // Constraints would be added here
                            }),
                            contentType: "application/json",
                            success: function(result) {
                                // Navigate to confirmation modal
                                window.location.href = `/define-relation-type?section=confirmationModal&newId=${result.id}`;
                            }
                        });
                    } else {
                        alert("Relation Type name already exists!");
                    }
                }
            });
        } else {
            alert("All fields are required!");
        }
    });

    // Cancel button click event - Navigate back to the Relation Type Overview page
    $('#cancelButton').click(function() {
        window.location.href = "/relation-type-overview";
    });
});
</script>