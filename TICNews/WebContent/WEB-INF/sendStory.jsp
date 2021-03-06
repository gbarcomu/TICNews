<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<base href="${pageContext.request.contextPath}/">
<title>Enviar Noticia</title>
<link type="text/css" rel="stylesheet"
	href="css/styles.css">
<link rel="stylesheet"
	href="css/font-awesome.min.css">
</head>

<body>

	<jsp:include page="/WEB-INF/menu.jsp" />

	<main>
	<div class="formulario">

		<form action="private/NewStory" method="post">

			<div>
				<label for="title">Titulo</label> <input type="text" name="title"
					id="title">
			</div>

			<div>
				<label for="url">Link a la noticia</label> <input type="url"
					name="url" id="url">
			</div>

			<div>
				<label for="text">Contenido</label>
				<textarea name="text" id="text"></textarea>
			</div>

			<div>
				<input type="submit" value="Enviar" class="button">
			</div>
		</form>
	</div>
	</main>

	<jsp:include page="/WEB-INF/footer.jsp" />
	
</body>


</html>