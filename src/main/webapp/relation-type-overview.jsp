<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Relation Type Overview</title>
<!-- Include relevant CSS and JS files -->
<link href="path/to/bootstrap.css" rel="stylesheet">
<script src="path/to/jquery.min.js"></script>
<script src="path/to/bootstrap.js"></script>
</head>
<body>

<div class="container">

    <!-- Relation Types Table Section -->
    <jsp:include page="relation-types-table.jsp"></jsp:include>
    
    <!-- Edit Relation Type Section -->
    <jsp:include page="edit-relation-type.jsp"></jsp:include>
    
    <!-- Delete Confirmation Section -->
    <jsp:include page="delete-confirmation.jsp"></jsp:include>
    
    <!-- Manage Instances Section -->
    <jsp:include page="manage-instances.jsp"></jsp:include>

</div>

</body>
</html>