<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="${size}">
    <table class="table table-bordered table-hover">
        <thead class="table-light">
            <tr>
                <th>Property ID</th>
                <th>Property Name</th>
                <th>Object Type</th>
                <th>Data Type</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="propertyList">
            <!-- Property rows will be injected here via jQuery -->
        </tbody>
    </table>
    <!-- Pagination Placeholder -->
    <nav aria-label="Property list pagination">
        <ul class="pagination">
            <!-- Pagination links will be injected here via jQuery -->
        </ul>
    </nav>
</div>
<script>
$(document).ready(function() {
    function fetchProperties() {
        // Placeholder URL and parameters
        var apiUrl = '/api/fetchProperties';
        var params = {
            objectTypeId: '', // should be determined based on selection
            limit: 10,
            offset: 0,
            sortBy: 'name',
            sortOrder: 'asc'
        };

        $.ajax({
            url: apiUrl,
            type: 'GET',
            data: params,
            dataType: 'json',
            success: function(properties) {
                // Assuming 'properties' is an array of property objects
                $('#propertyList').empty(); // Clear existing rows
                $.each(properties, function(i, property) {
                    var row = $('<tr>').append(
                        $('<td>').text(property.propertyId),
                        $('<td>').text(property.propertyName),
                        $('<td>').text(property.objectType),
                        $('<td>').text(property.dataType),
                        $('<td>').append(
                            $('<a>').attr('href', 'edit-property?propertyId=' + property.propertyId)
                                    .text('Edit'),
                            ' | ',
                            $('<a>').attr('href', '#').attr('data-id', property.propertyId)
                                    .text('Archive').addClass('archive-action')
                        )
                    );
                    $('#propertyList').append(row);
                });
            },
            error: function(xhr, status, error) {
                console.error("Error fetching properties: ", error);
            }
        });
    }

    $(document).on('click', '.archive-action', function(e) {
        e.preventDefault();
        var propertyId = $(this).data('id');
        var confirmArchive = confirm('Are you sure you want to archive this property?');

        if (confirmArchive) {
            $.ajax({
                url: '/api/archiveProperty',
                type: 'POST',
                data: {propertyId: propertyId},
                success: function(response) {
                    if (response === true) {
                        alert('Property archived successfully.');
                        fetchProperties(); // Refresh the list
                    } else {
                        alert('Failed to archive property.');
                    }
                },
                error: function(xhr, status, error) {
                    console.error("Error archiving property: ", error);
                }
            });
        }
    });

    // Initial properties fetch
    fetchProperties();
});
</script>