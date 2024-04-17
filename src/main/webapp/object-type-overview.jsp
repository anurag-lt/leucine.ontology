<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Object Type Overview</title>
    <!-- Assuming Bootstrap & jQuery are included globally -->
</head>
<body>
    <div class="container-fluid">
        <!-- Include other section JSPs here -->
        <jsp:include page="object-type-list.jsp"/>
        <jsp:include page="filter-and-search.jsp"/>
        <jsp:include page="add-new-object-type-button.jsp"/>
    </div>
    <script>
        $(document).ready(function(){
            // Global functionalities for object-type-overview
        });
    </script>
</body>
</html>