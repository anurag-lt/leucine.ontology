<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table-responsive">
    <table class="table">
        <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Source Object Type</th>
                <th>Target Object Type</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="relationTypesTableBody">
            <!-- Dynamic rows will be added here -->
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
    $(document).ready(function() {
        fetchRelationTypes();

        $("#relationTypesTableBody").on("click", ".editBtn", function() {
            var relationTypeId = $(this).data("id");
            window.location.href = `edit-relation-type?relationTypeId=${relationTypeId}`;
        });

        $("#relationTypesTableBody").on("click", ".deleteBtn", function() {
            var relationTypeId = $(this).data("id");
            var confirmation = confirm("Are you sure you want to delete this relation type?");
            if (confirmation) {
                deleteRelationType(relationTypeId);
            }
        });

        $("#relationTypesTableBody").on("click", ".manageInstancesBtn", function() {
            var relationTypeId = $(this).data("id");
            window.location.href = `manage-relation-instances?relationTypeId=${relationTypeId}`;
        });
    });

    function fetchRelationTypes() {
        // Dummy API Endpoint
        var apiUrl = "/api/relationTypes";

        $.ajax({
            url: apiUrl,
            type: "GET",
            success: function(response) {
                // Assuming the response to be a list of relation types
                response.forEach(function(item) {
                    var row = `<tr>
                        <td>${item.name}</td>
                        <td>${item.description}</td>
                        <td>${item.sourceObjectType}</td>
                        <td>${item.targetObjectType}</td>
                        <td>
                            <button class="btn btn-primary editBtn" data-id="${item.id}">Edit</button>
                            <button class="btn btn-danger deleteBtn" data-id="${item.id}">Delete</button>
                            <button class="btn btn-info manageInstancesBtn" data-id="${item.id}">Manage Instances</button>
                        </td>
                    </tr>`;
                    $("#relationTypesTableBody").append(row);
                });
            },
            error: function(err) {
                console.log("Error fetching relation types:", err);
            }
        });
    }

    function deleteRelationType(relationTypeId) {
        // Dummy API Endpoint
        var apiUrl = `/api/deleteRelationType/${relationTypeId}`;

        $.ajax({
            url: apiUrl,
            type: "DELETE",
            success: function(response) {
                if(response) {
                    alert("Relation Type Deleted Successfully!");
                    fetchRelationTypes(); // Refresh the table
                } else {
                    alert("Failed to delete the relation type.");
                }
            },
            error: function(err) {
                console.log("Error deleting relation type:", err);
            }
        });
    }
</script>