<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="filterSearchSection" class="col-md-12 mb-4">
    <form id="filterForm">
        <div class="form-row">
            <div class="col">
                <input type="text" class="form-control" id="objectTypeName" placeholder="Object Type Name">
            </div>
            <div class="col">
                <input type="text" class="form-control" id="description" placeholder="Description">
            </div>
            <div class="col">
                <input type="text" class="form-control" id="dateCreatedFrom" placeholder="Date Created From">
            </div>
            <div class="col">
                <input type="text" class="form-control" id="dateCreatedTo" placeholder="Date Created To">
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </div>
    </form>
</div>
<script>
    $(document).ready(function () {
        // Initialize date pickers
        $('#dateCreatedFrom, #dateCreatedTo').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayHighlight: true
        });

        $('#filterForm').on('submit', function (e) {
            e.preventDefault();
            var objectTypeName = $('#objectTypeName').val().trim(),
                description = $('#description').val().trim(),
                dateCreatedFrom = $('#dateCreatedFrom').val(),
                dateCreatedTo = $('#dateCreatedTo').val();

            // Validate input fields
            if (!objectTypeName || !description) {
                alert('Object Type Name and Description cannot be empty.');
                return;
            }
            if (!isValidDate(dateCreatedFrom) || !isValidDate(dateCreatedTo)) {
                alert('Incorrect date format. Please use YYYY-MM-DD format.');
                return;
            }

            // Assuming an API endpoint for fetching filtered object types
            $.ajax({
                url: '/api/objectTypes/filter',
                type: 'GET',
                data: {
                    name: objectTypeName,
                    description: description,
                    dateCreatedFrom: dateCreatedFrom,
                    dateCreatedTo: dateCreatedTo
                },
                success: function (response) {
                    // Assuming the response is the filtered list of object types
                    // This part should ideally be handled in the object-type-list.jsp's script
                    // For the purpose of this example, a console log is provided
                    console.log(response);
                    alert('Filter applied'); // Placeholder action
                },
                error: function () {
                    alert('Error applying filters.');
                }
            });
        });

        function isValidDate(dateStr) {
            var regex = /^\d{4}-\d{2}-\d{2}$/;
            return dateStr.match(regex) != null;
        }
    });
</script>