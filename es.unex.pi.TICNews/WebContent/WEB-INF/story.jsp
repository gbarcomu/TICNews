<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<base href="${pageContext.request.contextPath}/">
<title>Noticia</title>
<link type="text/css" rel="stylesheet"
	href="css/styles.css">
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
</head>

<body>

	<jsp:include page="/WEB-INF/menu.jsp" />

	<main>

	<article>

		<h1>
			<a href="<c:url value='${news.url}'/>">${news.title}</a>
		</h1>

		<p>
			enviado por <a href="<c:url value='UserDetail?id=${user.id}'/>"
				class="enviadoPor">${user.name}</a>:
		</p>

		<p>${news.text}</p>

		<div class="feedback">

			<span class="like"><i class="fa fa-thumbs-o-up"></i></span> <span
				class="dislike"><i class="fa fa-thumbs-o-down"></i></span> <span>${news.likes}</span>

		</div>

		<div class="newComment">

			<form action="private/Comment?newsID=${news.id}" method="post">

				<fieldset>
					<legend>Comenta</legend>

					<div>
						<label for="nuevoComent"></label>
						<textarea name="nuevoComent" id="nuevoComent"></textarea>
					</div>

					<input type="submit" value="enviar" class="button">

				</fieldset>


			</form>

		</div>

		<c:forEach var="commentsWithUser" items="${commentsWithUser}">

			<div class="comentary">

				<h2>
					<a
						href="<c:url value='UserDetail?id=${commentsWithUser.value.id}'/>">${commentsWithUser.value.name}</a>
				</h2>

				<p>${commentsWithUser.key.text}</p>

			</div>
		</c:forEach>
	</article>


	</main>

	<jsp:include page="/WEB-INF/footer.jsp" />

</body>

</html>