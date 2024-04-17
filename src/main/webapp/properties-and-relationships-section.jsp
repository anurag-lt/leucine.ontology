<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- Property Modal -->
<div class="modal fade" id="propertyModal" tabindex="-1" role="dialog" aria-labelledby="propertyModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="propertyModalLabel">Add Property</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <div class="form-group">
                <label for="propertyName">Property Name</label>
                <input type="text" class="form-control" id="propertyName" required>
            </div>
            <div class="form-group">
                <label for="dataType">Data Type</label>
                <select class="form-control" id="dataType">
                    <!-- Dynamically populated -->
                </select>
            </div>
            <div class="form-group">
                <label for="defaultValue">Default Value</label>
                <input type="text" class="form-control" id="defaultValue">
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" id="addProperty" class="btn btn-primary">Add</button>
        </div>
    </div>
  </div>
</div>

<!-- Relationship Modal -->
<div class="modal fade" id="relationshipModal" tabindex="-1" role="dialog" aria-labelledby="relationshipModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="relationshipModalLabel">Add Relationship</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <div class="form-group">
                <label for="relationshipName">Relationship Name</label>
                <input type="text" class="form-control" id="relationshipName" required>
            </div>
            <div class="form-group">
                <label for="sourceObjectType">Source Object Type</label>
                <input type="text" class="form-control" id="sourceObjectType" required>
            </div>
            <div class="form-group">
                <label for="targetObjectType">Target Object Type</label>
                <input type="text" class="form-control" id="targetObjectType" required>
            </div>
            <div class="form-group">
                <label for="cardinality">Cardinality</label>
                <select class="form-control" id="cardinality">
                    <option>1 to 1</option>
                    <option>1 to many</option>
                    <option>many to 1</option>
                    <option>many to many</option>
                </select>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" id="addRelationship" class="btn btn-primary">Add</button>
        </div>
    </div>
  </div>
</div>

<script>
$(document).ready(function(){
    // Fetch all data types for the properties
    function fetchDataTypes() {
        $.ajax({
            url: "/api/data-types/fetchAllDataTypes",
            type: "GET",
            success: function(response) {
                response.forEach(function(dataType) {
                    $('#dataType').append(new Option(dataType.name, dataType.id));
                });
            },
            error: function(error) {
                console.log("Error fetching data types", error);
            }
        });
    }
    fetchDataTypes();

    // Add property action
    $('#addProperty').click(function() {
        const propertyName = $('#propertyName').val().trim();
        const dataType = $('#dataType').val();
        const defaultValue = $('#defaultValue').val().trim();

        if (!propertyName || !dataType) {
            alert("Property Name and Data Type are required.");
            return;
        }

        // Assume an API endpoint for adding a property to an object type
        // This is a placeholder for actual implementation
        // On successful addition, simulate closing the modal and refreshing the parent form
        console.log("Adding Property", {propertyName, dataType, defaultValue});
        $("#propertyModal").modal('hide');
        alert("Property added successfully.");
        // Trigger an event or callback here to refresh/reload parent form
    });

    // Add relationship action
    $('#addRelationship').click(function() {
        const relationshipName = $('#relationshipName').val().trim();
        const sourceObjectType = $('#sourceObjectType').val().trim();
        const targetObjectType = $('#targetObjectType').val().trim();
        const cardinality = $('#cardinality').val();

        if (!relationshipName || !sourceObjectType || !targetObjectType || !cardinality) {
            alert("All fields are required to add a relationship.");
            return;
        }

        // Assume an API endpoint for adding a relationship to an object type
        // This is a placeholder for actual implementation
        console.log("Adding Relationship", {relationshipName, sourceObjectType, targetObjectType, cardinality});
        $("#relationshipModal").modal('hide');
        alert("Relationship added successfully.");
        // Trigger an event or callback here to refresh/reload parent form
    });
});
</script>