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
			yearRange : '1970:2020'
		});
	});
</script>
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div>
				<a href="<%=request.getContextPath()%>/userIndex.jsp"
					class="navbar-brand"> Home</a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/rooms"
					class="nav-link">Rooms</a></li>
			</ul>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/events"
					class="nav-link">Events</a></li>
			</ul>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/going"
					class="nav-link">Going to Events</a></li>
			</ul>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/invites"
					class="nav-link">Invites</a></li>
			</ul>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/users"
					class="nav-link">Users</a></li>
			</ul>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/users/editProfile"
					class="nav-link">Edit Profile</a></li>
			</ul>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/" class="nav-link">Logout</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${user != null}">
					<form action="updateUser" method="get">
				</c:if>
				<caption>
					<h2>
						<c:if test="${user != null}">
            			Edit User
            		</c:if>
					</h2>
				</caption>

				<c:if test="${user != null}">
					<td><input type="hidden" name="username"
						value="<c:out value='${user.username}' />" /></td>
				</c:if>

				<c:if test="${user == null}">
					<fieldset class="form-group">
						<label>Username</label> <input type="text"
							value="<c:out value='${user.username}' />" class="form-control"
							name="username" required="required">
					</fieldset>
				</c:if>

				<fieldset class="form-group">
					<label>Password</label> <input type="text"
						value="<c:out value='${user.password}' />" class="form-control"
						name="password" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Last</label> <input type="text"
						value="<c:out value='${user.last}' />" class="form-control"
						name="last">
				</fieldset>

				<fieldset class="form-group">
					<label>First</label> <input type="text"
						value="<c:out value='${user.first}' />" class="form-control"
						name="first">
				</fieldset>

				<form action="DatePicker">
					<label for="birthDate">Birth Date</label> <input type="text"
						value="<c:out value='${user.birthDate}' />" name="birthDate"
						id="datepicker" class="form-control">
					<c:if test="${user != null}">
						<td><input type="hidden" name="type"
							value="<c:out value='${user.type}' />" /></td>
					</c:if>

					<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>