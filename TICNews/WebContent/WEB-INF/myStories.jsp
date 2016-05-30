<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<base href="${pageContext.request.contextPath}/">
<title>Mis Noticias</title>
<link type="text/css" rel="stylesheet" href="css/styles.css">
<link rel="stylesheet"
	href="css/font-awesome.min.css">
</head>

<body>

	<jsp:include page="/WEB-INF/menu.jsp" />

	<main> <c:forEach var="newsList" items="${newsList}">

		<article>

			<h1>
				<a href="<c:url value='${newsList.url}'/>">${newsList.title}</a>
			</h1>

			<p>${newsList.text}</p>
			<div class="feedback">

				<span class="comentarios"><a
					href="<c:url value='Story?id=${newsList.id}'/>">comentarios</a></span> <span
					class="edit"><a
					href="<c:url value='private/EditStory?id=${newsList.id}'/>"> <i
						class="fa fa-pencil-square-o"></i> editar
				</a></span>

			</div>

		</article>
	</c:forEach> </main>

	<jsp:include page="/WEB-INF/footer.jsp" />

</body>

</html>