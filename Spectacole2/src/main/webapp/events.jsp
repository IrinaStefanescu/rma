
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Room Management Application</title>
<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
<script type="text/javascript"  src="js/jquery-ui-1.8.2.custom.js"></script>
<link href="themes/base/jquery.ui.all.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$(document).ready(function(){
	$('#event.date').datepicker();
});
</script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
  
  <%  String username=(String)session.getAttribute("loggedUser"); %>
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

	<div class="row">
		<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

		<div class="container">
			<h3 class="text-center">List of Events</h3>

			<hr>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Name</th>
						<th>Room</th>
						<th>Date</th>
						<th>Hour</th>
						<th>Minute</th>
						<th>Duration</th>
						<th>Number of people</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="event" items="${events}">
						<tr>
							<td><c:out value="${event.name}" /></td>
							<td><c:out value="${event.roomId}" /></td>
							<td><c:out value="${event.date}" /></td>
							<td><c:out value="${event.hour}" /></td>
							<td><c:out value="${event.min}" /></td>
							<td><c:out value="${event.duration}" /></td>
							<td><c:out value="${event.nrPeople}" /></td>
							<td><a
								href="going/insertGoing?username=<c:out value= '${username}' />
								&name=<c:out value='${event.name}'/>&date=<c:out value='${event.date}'/>">Going</a>
								<a
								href="invites/newInvite?sender=<c:out value= '${username}' />
								&name=<c:out value='${event.name}'/>&date=<c:out value='${event.date}'/>">Invite</a>
								</td>

						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
	</div>
</body>
</html>