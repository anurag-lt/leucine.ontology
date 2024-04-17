<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="table-responsive">
    <table class="table">
        <thead>
            <tr>
                <th>Object Type Name</th>
                <th>Description</th>
                <th>Date Created</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="objectTypeList"></tbody>
    </table>
</div>
<script>
    $(document).ready(function () {
        function fetchObjectTypes() {
            $.ajax({
                url: '/api/objectTypes',
                type: 'GET',
                success: function (response) {
                    populateObjectTypes(response);
                },
                error: function () {
                    alert('Error fetching object types');
                }
            });
        }

        function populateObjectTypes(data) {
            $('#objectTypeList').empty();
            data.forEach(function (objectType) {
                $('#objectTypeList').append(`
                    <tr>
                        <td>${objectType.name}</td>
                        <td>${objectType.description}</td>
                        <td>${new Date(objectType.dateCreated).toLocaleDateString()}</td>
                        <td>
                            <button onclick="editObjectType('${objectType.id}')" class="btn btn-primary">Edit</button>
                            <button onclick="deleteObjectType('${objectType.id}')" class="btn btn-danger">Delete</button>
                            <button onclick="updateVisibility('${objectType.id}', ${objectType.isVisible})" class="btn btn-secondary">Toggle Visibility</button>
                            <button onclick="preventDuplication('${objectType.id}')" class="btn btn-warning">Prevent Duplication</button>
                        </td>
                    </tr>
                `);
            });
        }

        window.editObjectType = function (id) {
            window.location.href = '/edit-object-type?id=' + id;
        };

        window.deleteObjectType = function (id) {
            if(confirm('Are you sure you want to delete this Object Type?')) {
                $.ajax({
                    url: '/api/objectTypes/' + id,
                    type: 'DELETE',
                    success: function () {
                        alert('Object Type deleted successfully');
                        fetchObjectTypes();
                    },
                    error: function () {
                        alert('Error deleting object type');
                    }
                });
            }
        };

        window.updateVisibility = function (id, currentVisibility) {
            $.ajax({
                url: '/api/objectTypes/' + id + '/visibility',
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify({ isVisible: !currentVisibility }),
                success: function () {
                    alert('Visibility updated successfully');
                    fetchObjectTypes();
                },
                error: function () {
                    alert('Error updating visibility');
                }
            });
        };

        window.preventDuplication = function (id) {
            // Trigger a modal or a separate page for handling duplication logic.
            // This example assumes a modal popup logic.
            alert('This would trigger a modal for preventing duplication for ID: ' + id);
            // Modal logic to handle duplication prevention would be implemented here.
        };

        fetchObjectTypes();
    });
</script>