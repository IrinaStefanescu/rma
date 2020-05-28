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
				<a href="<%=request.getContextPath()%>/adminIndex.jsp"
					class="navbar-brand"> Home</a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/adminRooms"
					class="nav-link">Rooms</a></li>
			</ul>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/adminEvents"
					class="nav-link">Events</a></li>
			</ul>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/adminGoing"
					class="nav-link">Going to Events</a></li>
			</ul>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/adminInvites"
					class="nav-link">Invites</a></li>
			</ul>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/adminUsers"
					class="nav-link">Users</a></li>
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
			<h3 class="text-center">List of Rooms</h3>
			<hr>
			<div class="container text-left">
				<a href="<%=request.getContextPath()%>/adminRooms/newRoom"
					class="btn btn-success">Add New Room</a>
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Room ID</th>
						<th>Floor</th>
						<th>Capacity</th>
						<th>Projector</th>
						<th>Actions</th>
						<th>Calendar</th>
					</tr>
				</thead>
				<tbody>
					<!--   for (Todo todo: todos) {  -->
					<c:forEach var="room" items="${adminRooms}">
						<tr>
							<td><c:out value="${room.roomId}" /></td>
							<td><c:out value="${room.floor}" /></td>
							<td><c:out value="${room.capacity}" /></td>
							<td><c:out value="${room.projector}" /></td>


							<td><a
								href="adminRooms/editRoom?roomId=<c:out value='${room.roomId}' />">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="adminRooms/deleteRoom?roomId=<c:out value='${room.roomId}' />">Delete</a></td>
							<td><a href="adminRooms/calendar?roomId=<c:out value='${room.roomId}'/>">Check Calendar</a></td>
						</tr>
					</c:forEach>
					<!-- } -->
				</tbody>

			</table>
		</div>
	</div>
</body>
</html>