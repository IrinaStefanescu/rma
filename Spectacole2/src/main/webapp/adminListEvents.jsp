
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
			<h3 class="text-center">List of Events</h3>

			<hr>
			<div class="container text-left">
				<a href="<%=request.getContextPath()%>/adminEvents/newEvent"
					class="btn btn-success">Add New Event</a>
			</div>
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
						<!--   for (Todo todo: todos) {  -->
						<c:forEach var="event" items="${adminEvents}">
							<tr>
								<td><c:out value="${event.name}" /></td>
								<td><c:out value="${event.roomId}" /></td>
								<td><c:out value="${event.date}" /></td>
								<td><c:out value="${event.hour}" /></td>
								<td><c:out value="${event.min}" /></td>
								<td><c:out value="${event.duration}" /></td>
								<td><c:out value="${event.nrPeople}" /></td>

								<td><a
									href="adminEvents/editEvent?name=<c:out value='${event.name}'/>&date=<c:out value='${event.date}'/>">Edit</a>
									&nbsp;&nbsp;&nbsp;&nbsp; <a
									href="adminEvents/deleteEvent?name=<c:out value='${event.name}'/>&date=<c:out value='${event.date}'/>">Delete</a></td>
							</tr>
						</c:forEach>
						<!-- } -->
					</tbody>

				</table>
		</div>
	</div>
</body>
</html>