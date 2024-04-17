<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<div class="container col-md-8">
    <form id="objectTypeForm">
        <div class="form-group">
            <label for="objectTypeName">Object Type Name</label>
            <input type="text" class="form-control" id="objectTypeName" required>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" id="description" rows="3"></textarea>
        </div>
        <div class="form-group">
            <label for="uniqueIdentifier">Unique Identifier</label>
            <input type="text" class="form-control" id="uniqueIdentifier" required>
        </div>
        <div class="form-group">
            <label for="baseProperties">Base Properties</label>
            <select multiple class="form-control" id="baseProperties">
                <!-- Dynamically populated -->
            </select>
        </div>
        <div class="form-group">
            <label for="businessRules">Business Rules</label>
            <input type="text" class="form-control" id="businessRules">
        </div>
        <button type="button" id="saveObjectType" class="btn btn-primary">Save</button>
        <button type="button" id="cancel" class="btn btn-secondary">Cancel</button>
    </form>
</div>

<script>
$(document).ready(function(){
    // Fetch and populate base properties dropdown
    function fetchBaseProperties() {
        $.ajax({
            url: "/api/property-management/getPropertyIdsForDropdown",
            type: "GET",
            success: function(response){
                response.forEach(function(property){
                    $('#baseProperties').append(new Option(property.name, property.id));
                });
            },
            error: function(error){
                console.log("Error fetching properties", error);
            }
        });
    }
    fetchBaseProperties();
    
    // Form validation and submission
    $('#saveObjectType').click(function(e){
        e.preventDefault();
        var name = $('#objectTypeName').val().trim();
        var description = $('#description').val().trim();
        var uniqueIdentifier = $('#uniqueIdentifier').val().trim();
        var basePropertyIds = $('#baseProperties').val();
        
        if(name === "" || uniqueIdentifier === ""){
            alert("Object Type Name and Unique Identifier are required.");
            return;
        }

        // Check for duplicate
        $.ajax({
            url: "/api/object-type-management/checkForDuplicateObjectType",
            type: "POST",
            data: JSON.stringify({ name: name, uniqueIdentifier: uniqueIdentifier }),
            contentType: "application/json",
            success: function(response){
                if(response === false){
                    // No duplicate found, proceed with creation
                    $.ajax({
                        url: "/api/object-type-management/createObjectType",
                        type: "POST",
                        data: JSON.stringify({
                            name: name,
                            description: description,
                            uniqueIdentifier: uniqueIdentifier,
                            basePropertyIds: basePropertyIds,
                            businessRules: {}
                        }),
                        contentType: "application/json",
                        success: function(response){
                            alert("Object Type created successfully.");
                            window.location.href = "/object-type-overview.jsp";
                        },
                        error: function(error){
                            console.log("Error creating object type", error);
                            alert("Failed to create object type.");
                        }
                    });
                } else {
                    alert("An Object Type with the provided name or unique identifier already exists.");
                }
            },
            error: function(error){
                console.log("Error checking for duplicates", error);
                alert("Error checking for duplicate Object Type.");
            }
        });
    });

    // Cancel button navigational flow
    $('#cancel').click(function(e){
        e.preventDefault();
        window.location.href = "/object-type-overview.jsp";
    });

});
</script>