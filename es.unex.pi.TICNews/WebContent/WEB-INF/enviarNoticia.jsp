<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<title>Enviar Noticia</title>
	<link type="text/css" rel="stylesheet" href="styles.css">
	<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
</head>

<body>

	<jsp:include page="/WEB-INF/menu.jsp"/>
	
	<main>
		<div class="formulario">

			<form action="CreateNewsServlet" method="post">

				<div>
					<label for="titulo">Titulo</label>
					<input type="text" name="titulo" id="titulo">
				</div>

				<div>
					<label for="url">Link a la noticia</label>
					<input type="url" name="url" id="url">
				</div>

				<div>
					<label for="cuerpoNoticia">Contenido</label>
					<textarea name="cuerpoNoticia" id="cuerpoNoticia"></textarea>
				</div>

				<div>
					<input type="submit" value="Enviar" class="button">
				</div>
			</form>
		</div>
	</main>

	<footer>
		<p>Programacion en Internet</p>
		<p>Guillermo Barco</p>
	</footer>

</body>


</html>