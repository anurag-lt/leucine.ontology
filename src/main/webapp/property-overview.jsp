<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Property Overview</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-3.6.0.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container">
        <h1>Property Overview</h1>
        <div class="row">
            <!-- Property Search and Filter Section -->
            <jsp:include page="property-overview-property-search-and-filter.jsp" />
        </div>
        <div class="row">
            <!-- Property List Section -->
            <jsp:include page="property-overview-property-list.jsp" />
        </div>
    </div>
</body>
</html>