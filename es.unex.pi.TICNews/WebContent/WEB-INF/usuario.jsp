<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>TIC News</title>
<link type="text/css" rel="stylesheet" href="styles.css">
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
</head>

<body>

	<jsp:include page="/WEB-INF/menu.jsp" />

	<main id="user">

	<h1>${user.name}</h1>
	<h2>${user.email}</h2>

	</main>

	<footer>
		<p>Programacion en Internet</p>
		<p>Guillermo Barco</p>
	</footer>

</body>

</html>