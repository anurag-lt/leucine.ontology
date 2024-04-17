<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-8">
    <form id="editObjectTypeForm">
        <div class="mb-3">
            <label for="objectTypeName" class="form-label">Name</label>
            <input type="text" class="form-control" id="objectTypeName" required>
        </div>
        <div class="mb-3">
            <label for="objectTypeDescription" class="form-label">Description</label>
            <textarea class="form-control" id="objectTypeDescription" rows="3" required></textarea>
        </div>
        <div class="mb-3">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" id="visibilityPublic" value="public">
                <label class="form-check-label" for="visibilityPublic">Public</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" id="visibilityPrivate" value="private">
                <label class="form-check-label" for="visibilityPrivate">Private</label>
            </div>
        </div>
        <div class="mb-3">
            <label for="objectTypeProperties" class="form-label">Properties</label>
            <select class="form-select" id="objectTypeProperties" multiple>
                <!-- Options populated dynamically -->
            </select>
        </div>
        <div class="mb-3">
            <label for="objectTypeRelationships" class="form-label">Relationships</label>
            <select class="form-select" id="objectTypeRelationships" multiple>
                <!-- Options populated dynamically -->
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Save Changes</button>
    </form>
</div>
<script>
$(document).ready(function () {
    // Fetch and populate form data
    const fetchData = () => {
        $.get("/api/getObjectTypeDetailsById?objectTypeId=obj-123", function(data) {
            $('#objectTypeName').val(data.name);
            $('#objectTypeDescription').val(data.description);
            if(data.visibility === 'public') $('#visibilityPublic').prop('checked', true);
            else $('#visibilityPrivate').prop('checked', true);
            // Populate properties and relationships
            // This assumes a structure of the API response and relevant fields
        });
    }

    fetchData();

    // Navigational Flows and Validations
    $('#editObjectTypeForm').submit(function(e) {
        e.preventDefault();

        var name = $('#objectTypeName').val();
        var description = $('#objectTypeDescription').val();
        var visibility = $('#visibilityPublic').is(':checked') ? true : false;
        
        // Simple validation (further validations can be added as needed)
        if (!name || !description || (!$('#visibilityPublic').is(':checked') && !$('#visibilityPrivate').is(':checked'))) {
            alert('Name, Description and Visibility are required.');
            return;
        }

        var properties = $('#objectTypeProperties').val(); // Assume array of selected property IDs
        var relationships = $('#objectTypeRelationships').val(); // Assume array of selected relationship IDs

        $.ajax({
            url: '/api/updateObjectType',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                objectTypeId: 'obj-123',
                name: name,
                description: description,
                isPublic: visibility,
                propertyIds: properties,
                relationTypeIds: relationships
            }),
            success: function(response) {
                if(response) {
                    // Assuming response to be a boolean
                    window.location.href = "/object-type-overview?section=update-success";
                } else {
                    alert('Error updating the object type. Please try again.');
                }
            },
            error: function() {
                alert('Error connecting to the server. Please try again.');
            }
        });
    });

    // Add New Property Flow
    $('#addNewProp').click(function() {
        window.location.href = "/add-new-property?section=form&objectTypeId=obj-123";
    });

    // Define New Relationship Flow
    $('#defineNewRel').click(function() {
        window.location.href = "/define-relation-type?section=form&objectTypeId=obj-123";
    });
});
</script>