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

	<main> <c:forEach var="newsList" items="${newsList}">

		<article>

			<h1>
				<a href=${newsList.url}>${newsList.title}</a>
			</h1>
			
			<p>${newsList.text}</p>
			<div class="feedback">

				<span class="comentarios"><a href="<c:url value='ListCommentsServlet?id=${newsList.id}'/>">comentarios</a></span>
				<span class="edit"><a href="<c:url value='EditNewsServlet?id=${newsList.id}'/>"> <i
						class="fa fa-pencil-square-o"></i> editar
				</a></span>

			</div>

		</article> </c:forEach>

</main>

	<footer>
		<p>Programacion en Internet</p>
		<p>Guillermo Barco</p>
	</footer>

</body>

</html>