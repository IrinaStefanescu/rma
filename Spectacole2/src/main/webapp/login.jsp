
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Room Management Application</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>

<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div>
				<a href="<%=request.getContextPath()%>/login.jsp"
					class="navbar-brand">Login</a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/register.jsp"
					class="nav-link">Register</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<form action="LoginCheckServlet" method="get">
					<caption>
						<h2>Login</h2>
					</caption>

					<p style="color: red">${error}</p>

					<fieldset class="form-group">
						<label>Username</label> <input type="text"
							value="<c:out value='${user.username}' />" class="form-control"
							name="username" required="required">
					</fieldset>

					<fieldset class="form-group">
						<label>Password</label> <input type="password"
							value="<c:out value='${user.password}' />" class="form-control"
							name="password" required="required">
					</fieldset>
					<td><a href="forgotMyPassword.jsp">Forgot My Password</a></td>
					<button type="submit" class="btn btn-success">Login</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
