<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Object Type</title>
    <link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/js/jquery.min.js'/>"></script>
    <script src="<c:url value='/js/bootstrap.min.js'/>"></script>
</head>
<body>

<div class="container">
    <div class="row">
        <h2>Edit Object Type</h2>
    </div>
    <div class="row">
        <%@ include file="edit-object-type-form.jsp" %>
    </div>
    <div class="row">
        <%@ include file="confirmation-modal.jsp" %>
    </div>
</div>

</body>
</html>