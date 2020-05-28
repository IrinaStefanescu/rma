
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
			<h3 class="text-center">List of Invites</h3>

			<hr>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Sender username</th>
						<th>Sender last name</th>
						<th>Sender first name</th>
						<th>Event</th>
						<th>Room</th>
						<th>Date</th>
						<th>Hour</th>
						<th>Minute</th>
						<th>Duration</th>
						<th>Number of people</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<!--   for (Todo todo: todos) {  -->
					<c:forEach var="invite" items="${invites}">
						<tr>
						
							<td><c:out value="${invite.sender.getUsername()}" /></td>
							<td><c:out value="${invite.sender.getLast()}" /></td>
							<td><c:out value="${invite.sender.getFirst()}" /></td>
							<td><c:out value="${invite.event.getName()}" /></td>
							<td><c:out value="${invite.event.getRoomId()}" /></td>
							<td><c:out value="${invite.event.getDate()}" /></td>
							<td><c:out value="${invite.event.getHour()}" /></td>
							<td><c:out value="${invite.event.getMin()}" /></td>
							<td><c:out value="${invite.event.getDuration()}" /></td>
							<td><c:out value="${invite.event.getNrPeople()}" /></td>
							<td>
							<a
								href="invites/acceptInvite?receiver=<c:out value='${invite.receiver.getUsername()}' />
								&sender=<c:out value='${invite.sender.getUsername()}' />
								&name=<c:out value='${invite.event.getName()}'/>&date=<c:out value='${invite.event.getDate()}'/>">Accept Invite</a>
							<a
								href="invites/deleteInvite?receiver=<c:out value='${invite.receiver.getUsername()}' />
								&sender=<c:out value='${invite.sender.getUsername()}' />
								&name=<c:out value='${invite.event.getName()}'/>&date=<c:out value='${invite.event.getDate()}'/>">Delete</a></td>
							
						</tr>
					</c:forEach>
					<!-- } -->
				</tbody>

			</table>
		</div>
	</div>

</body>
</html>