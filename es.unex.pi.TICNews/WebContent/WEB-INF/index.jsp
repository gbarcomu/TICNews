<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<base href="${pageContext.request.contextPath}/">
<title>TIC News</title>
<link type="text/css" rel="stylesheet" href="css/styles.css">
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
</head>

<body>

	<jsp:include page="/WEB-INF/menu.jsp" />

	<main> <c:forEach var="newsmap" items="${newsMap}">
		<article>

			<h1>
				<a href=${newsmap.key.url}>${newsmap.key.title}</a>
			</h1>
			<p>
				enviado por <a
					href="<c:url value='UserDetail?id=${newsmap.value.id}'/>"
					class="enviadoPor">${newsmap.value.name}</a>:
			</p>
			<p>${newsmap.key.text}</p>

			<div class="feedback">

				<span class="comentarios"><a
					href="<c:url value='Story?id=${newsmap.key.id}'/>">comentarios</a></span>
				<span class="like"><i class="fa fa-thumbs-o-up"></i></span> <span
					class="dislike"><i class="fa fa-thumbs-o-down"></i></span> <span>${newsmap.key.likes}</span>

			</div>

		</article>
	</c:forEach> </main>

	<jsp:include page="/WEB-INF/footer.jsp" />

</body>

</html>