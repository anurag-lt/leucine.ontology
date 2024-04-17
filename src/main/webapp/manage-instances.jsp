<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="container mt-4" id="manageInstancesContainer">
    <h2>Manage Relation Instances</h2>
    <table class="table" id="relationInstancesTable">
        <thead>
            <tr>
                <th>Instance ID</th>
                <th>Source Object</th>
                <th>Target Object</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <!-- Rows will be dynamically added here -->
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
    $(document).ready(function() {
        var relationTypeId = getQueryVariable('relationTypeId');
        if(relationTypeId) {
            fetchRelationInstances(relationTypeId);
        }
        
        $(document).on("click", ".deleteInstanceBtn", function() {
            var instanceId = $(this).data("id");
            deleteRelationInstance(instanceId);
        });

        $(document).on("click", ".editInstanceBtn", function() {
            var instanceId = $(this).data("id");
            window.location.href = `edit-relation-instance?instanceId=${instanceId}`;
        });

        function fetchRelationInstances(relationTypeId) {
            $.ajax({
                url: `/api/relationInstances/${relationTypeId}`,
                type: 'GET',
                success: function(relationInstances) {
                    relationInstances.forEach(function(instance) {
                        $('#relationInstancesTable tbody').append(`
                            <tr>
                                <td>${instance.instanceId}</td>
                                <td>${instance.sourceObject}</td>
                                <td>${instance.targetObject}</td>
                                <td>
                                    <button class="btn btn-primary editInstanceBtn" data-id="${instance.instanceId}">Edit</button>
                                    <button class="btn btn-danger deleteInstanceBtn" data-id="${instance.instanceId}">Delete</button>
                                </td>
                            </tr>
                        `);
                    });
                },
                error: function(error) {
                    console.error("Error fetching relation instances:", error);
                }
            });
        }

        function deleteRelationInstance(instanceId) {
            if(confirm('Are you sure you want to delete this instance?')) {
                $.ajax({
                    url: `/api/deleteRelationInstance/${instanceId}`,
                    type: 'DELETE',
                    success: function(response) {
                        if(response) {
                            alert('Instance deleted successfully');
                            location.reload(); // Refresh data
                        } else {
                            alert('Failed to delete instance');
                        }
                    },
                    error: function(error) {
                        console.error("Error deleting relation instance:", error);
                    }
                });
            }
        }

        // Utility function to parse query strings
        function getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split('&');
            for (var i=0; i < vars.length; i++) {
                var pair = vars[i].split('=');
                if (decodeURIComponent(pair[0]) == variable) {
                    return decodeURIComponent(pair[1]);
                }
            }
            console.log('Query variable %s not found', variable);
        }
    });
</script>