<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div class="col-md-6">
    <form id="addNewPropertyForm">
        <div class="form-group">
            <label for="propertyName">Property Name</label>
            <input type="text" class="form-control" id="propertyName" required>
        </div>
        <div class="form-group">
            <label for="propertyType">Property Type</label>
            <select class="form-control" id="propertyType" required>
                <option value="Text">Text</option>
                <option value="Number">Number</option>
                <option value="Boolean">Boolean</option>
                <option value="Date">Date</option>
            </select>
        </div>
        <div class="form-group">
            <label for="unit">Unit</label>
            <input type="text" class="form-control" id="unit" disabled>
        </div>
        <div class="form-group">
            <label>Visibility</label>
            <div>
                <input type="radio" id="public" name="visibility" value="Public" checked>
                <label for="public">Public</label>
                <input type="radio" id="private" name="visibility" value="Private">
                <label for="private">Private</label>
            </div>
        </div>
        <div class="form-group">
            <label for="objectTypeSelection">Object Type Selection</label>
            <select class="form-control" id="objectTypeSelection" required></select>
        </div>
        <button type="submit" class="btn btn-primary">Add Property</button>
    </form>
</div>

<script>
$(document).ready(function () {
    $('#propertyType').change(function () {
        if ($(this).val() == 'Number') {
            $('#unit').prop('disabled', false);
        } else {
            $('#unit').prop('disabled', true).val('');
        }
    });

    function fetchObjectTypes() {
        // Assuming '/api/fetchObjectTypes' as the API endpoint for fetching object types
        $.ajax({
            url: '/api/fetchObjectTypes',
            type: 'GET',
            success: function (response) {
                // Assuming response is a JSON array of object types
                response.forEach(function (objectType) {
                    $('#objectTypeSelection').append(new Option(objectType.name, objectType.id));
                });
            }
        });
    }

    $('#addNewPropertyForm').submit(function (event) {
        event.preventDefault();

        // Validations
        var propertyName = $('#propertyName').val();
        var propertyType = $('#propertyType').val();
        var unit = $('#unit').val();
        var visibility = $('input[name="visibility"]:checked').val();
        var objectTypeId = $('#objectTypeSelection').val();

        if(!propertyName || !propertyType || !visibility || (propertyType == 'Number' && !unit)) {
            alert('Please fill all required fields.');
            return;
        }

        // API Call to check property name uniqueness
        $.ajax({
            url: '/api/checkPropertyNameUniqueness',
            type: 'POST',
            data: JSON.stringify({ propertyName: propertyName, objectTypeId: objectTypeId }),
            contentType: "application/json",
            success: function (response) {
                // Assuming response is a boolean indicating uniqueness
                if(response) {
                    // Create Property
                    $.ajax({
                        url: '/api/createProperty',
                        type: 'POST',
                        data: JSON.stringify({
                            propertyName: propertyName,
                            propertyType: propertyType,
                            unit: unit,
                            visibility: visibility,
                            objectTypeId: objectTypeId
                        }),
                        contentType: "application/json",
                        success: function (creationResponse) {
                            // Assuming successful property creation redirects to 'property-overview'
                            window.location.href = 'property-overview.jsp?created=true';
                        },
                        error: function () {
                            alert('Error creating property. Please try again.');
                        }
                    });
                } else {
                    alert('Property name already exists within this object type.');
                }
            },
            error: function () {
                alert('Error checking property name. Please try again.');
            }
        });
    });

    fetchObjectTypes();
});
</script>