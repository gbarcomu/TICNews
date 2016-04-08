<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav>

	<a href="<c:url value='ListNewsServlet'/>"><img src="logo.png"
		alt=""></a>
	<ul class="menu">

		<li><a href="<c:url value='ListNewsServlet'/>">PORTADA</a></li>
		<li><a href="<c:url value='ListNewsServlet'/>">MAS VALORADAS</a></li>
		<li><a href="<c:url value='ListMyNewsServlet'/>">MIS NOTICIAS</a></li>
	</ul>



	<ul class="menuRight">
		<c:choose>
			<c:when test="${registredUser == null}">
				<li><a href="<c:url value='LoginServlet'/>">login</a></li>
				<li><a href="<c:url value='RegisterServlet'/>">registrarse</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="<c:url value='LogoutServlet'/>">logout</a></li>
				<li class="userIcon"><a href="<c:url value='UserDetailServlet?id=${registredUser.id}'/>">${registredUser.name}</a></li>
			</c:otherwise>
		</c:choose>



	</ul>

	<button type="button" class="button"
		onclick="location.href='<c:url value='CreateNewsServlet'/>'">enviar noticia</button>
</nav>