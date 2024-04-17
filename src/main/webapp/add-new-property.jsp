<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Property</title>
    <%-- Include CSS files --%>
    <link rel="stylesheet" type="text/css" href="path/to/your/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="path/to/your/css/style.css">
    <script src="path/to/your/js/jquery-3.5.1.min.js"></script>
    <script src="path/to/your/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <%-- Include JSP files --%>
        <jsp:include page="add-new-property-form.jsp"></jsp:include>
        <jsp:include page="confirmation-modal.jsp"></jsp:include>
    </div>
</body>
</html>