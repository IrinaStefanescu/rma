<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Forgot Password</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : "yy-mm-dd",
			changeMonth : true,
			changeYear : true,
			yearRange: '1970:2020'
		});
	});
</script>
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
				<form action="adminUsers/change" method="post">
					<caption>
						<h2>Forgotten Password</h2>
					</caption>

					<p style="color: red">${errorPassword}</p>

					<fieldset class="form-group">
						<label>What is your username?</label> <input type="text"
							value="<c:out value='${user.username}' />" class="form-control"
							name="username" required="required">
					</fieldset>

					<fieldset class="form-group">
						<label>What is your first name?</label> <input type="text"
							value="<c:out value='${user.first}' />" class="form-control"
							name="first" required="required">
					</fieldset>

					<fieldset class="form-group">
						<label>What is your last name?</label> <input type="text"
							value="<c:out value='${user.last}' />" class="form-control"
							name="last" required="required">
					</fieldset>
					
					<form action="DatePicker">
						<label for="birthDate">What is your birth date?</label> <input type="text" name="birthDate"
							id="datepicker" class="form-control">
							
							
					<fieldset class="form-group">
						<label>Choose a new password</label> <input type="password"
							value="<c:out value='${user.password}' />" class="form-control"
							name="password1" required="required">
					</fieldset>
					
					<fieldset class="form-group">
						<label>Confirm the new password</label> <input type="password"
							value="<c:out value='${user.password}' />" class="form-control"
							name="password2" required="required">
					</fieldset>
					
					<button type="submit" class="btn btn-success">Change</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>