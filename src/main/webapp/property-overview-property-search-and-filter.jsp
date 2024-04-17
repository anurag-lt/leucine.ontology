<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="${size}">
    <form id="propertySearchForm">
        <div class="mb-3">
            <label for="propertyName" class="form-label">Property Name</label>
            <input type="text" class="form-control" id="propertyName" name="propertyName" required>
        </div>
        <div class="mb-3">
            <label for="objectType" class="form-label">Object Type</label>
            <select class="form-select" id="objectType" name="objectType" required>
                <option value="">Select Object Type</option>
                <!-- Options are populated dynamically via AJAX call -->
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
        <button type="button" class="btn btn-secondary" id="resetBtn">Reset</button>
    </form>
</div>
<script>
$(document).ready(function() {
    // Fetch and populate object types
    $.ajax({
        url: '/api/getAllObjectTypes',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            var objectTypes = response; // Assume response is directly the array of object types
            $.each(objectTypes, function(index, objectType) {
                $('#objectType').append($('<option>', { 
                    value: objectType,
                    text : objectType 
                }));
            });
        },
        error: function(xhr, status, error) {
            console.error("Error fetching object types: ", error);
        }
    });

    // Form submission
    $('#propertySearchForm').on('submit', function(e) {
        e.preventDefault();
        var propertyName = $('#propertyName').val();
        var objectType = $('#objectType').val();
        
        // Validate inputs
        if(propertyName.trim() === '' || objectType === '') {
            alert('Please fill in all required fields.');
            return false;
        }
        
        // Submit the search criteria
        $.ajax({
            url: '/api/findPropertiesByCriteria',
            type: 'GET',
            data: {
                propertyName: propertyName,
                objectType: objectType
            },
            dataType: 'json',
            success: function(response) {
                // Assume the response contains the properties that match criteria
                // The actual implementation of updating the Property List section would depend on its structure
                // This is a placeholder for the logic that would handle the response
                console.log("Search results: ", response);
                // This could involve updating the DOM to display the properties,
                // or triggering an update in another component that displays the property list.
            },
            error: function(xhr, status, error) {
                console.error("Error searching properties: ", error);
            }
        });
    });

    // Reset button
    $('#resetBtn').on('click', function() {
        $('#propertyName').val('');
        $('#objectType').val('').trigger('change'); // Reset dropdown
        // Optionally, trigger a search refresh here if the system should display all records on reset
    });
});
</script>