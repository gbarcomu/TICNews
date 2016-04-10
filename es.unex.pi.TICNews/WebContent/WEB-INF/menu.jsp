<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav>

	<a href="<c:url value='Index'/>"><img src="logo.png"
		alt=""></a>
	<ul class="menu">

		<li><a href="<c:url value='Index'/>">PORTADA</a></li>
		<li><a href="<c:url value='Index'/>">MAS VALORADAS</a></li>
		<li><a href="<c:url value='private/MyStories'/>">MIS NOTICIAS</a></li>
	</ul>



	<ul class="menuRight">
		<c:choose>
			<c:when test="${registredUser == null}">
				<li><a href="<c:url value='public/Login'/>">login</a></li>
				<li><a href="<c:url value='public/Register'/>">registrarse</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="<c:url value='private/Logout'/>">logout</a></li>
				<li class="userIcon"><a href="<c:url value='UserDetail?id=${registredUser.id}'/>">${registredUser.name}</a></li>
			</c:otherwise>
		</c:choose>



	</ul>

	<button type="button" class="button"
		onclick="location.href='<c:url value='private/NewStory'/>'">enviar noticia</button>
</nav>