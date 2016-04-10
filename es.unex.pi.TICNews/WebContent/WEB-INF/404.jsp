<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<base href="${pageContext.request.contextPath}/">
<title>Error 404</title>
<link type="text/css" rel="stylesheet" href="css/styles.css">
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
</head>

<body>

	<jsp:include page="/WEB-INF/menu.jsp" />

	<p> ERROR 404, el recurso al que estas intentado acceder no existe. </p>

	<jsp:include page="/WEB-INF/footer.jsp" />

</body>


</html>