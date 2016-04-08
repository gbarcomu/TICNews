<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Registro</title>
<link type="text/css" rel="stylesheet" href="styles.css">
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
</head>

<body>

	<jsp:include page="/WEB-INF/menu.jsp" />

	<main>
	<div class="formulario">

		<form action="RegisterServlet" method="post">

			<div>
				<label for="username">Nombre de Usuario</label> <input type="text"
					name="username" id="username">
			</div>

			<div>
				<label for="password">Password</label> <input type="password"
					name="password" id="password">
			</div>

			<div>
				<label for="passwordRepeated">Password</label> <input
					type="password" name="passwordRepeated" id="passwordRepeated">
			</div>

			<div>
				<label for="email">Email</label> <input type="email" name="email"
					id="email">
			</div>

			<div>
				<input type="submit" value="Registrate" class="button">
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