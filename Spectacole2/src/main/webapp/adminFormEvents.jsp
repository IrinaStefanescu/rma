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
			yearRange: '2017:2022'
		});
	});
</script>

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
	<p style="color: red">${notadded}</p>

	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${event != null}">
					<form
						action="<%=request.getContextPath()%>/adminEvents/updateEvent"
						method="post">
				</c:if>
				<c:if test="${event == null}">
					<form
						action="<%=request.getContextPath()%>/adminEvents/insertEvent"
						method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${event != null}">
            			Edit Event
            		</c:if>
						<c:if test="${event == null}">
            			Add New Event
            		</c:if>
					</h2>
				</caption>

				<c:if test="${event != null}">
					<td><input type="hidden" name="name"
						value="<c:out value='${event.name}' />" /></td>
					<td><input type="hidden" name="date"
						value="<c:out value='${event.date}' />" /></td>
				</c:if>

				<c:if test="${event == null}">
					<fieldset class="form-group">
						<label>Event Name</label> <input type="text"
							value="<c:out value='${event.name}' />" class="form-control"
							name="name" required="required">
					</fieldset>
				</c:if>

				<fieldset class="form-group">
					<label>Room</label> <input type="text"
						value="<c:out value='${event.roomId}' />" class="form-control"
						name="roomId" required="required">
				</fieldset>

				<c:if test="${event == null}">
					<form action="DatePicker">
						<label for="Date">Date</label> <input type="text" name="date"
							id="datepicker" class="form-control">
				</c:if>

				<fieldset class="form-group">
					<label>Hour</label> <input type="text"
						value="<c:out value='${event.hour}' />" class="form-control"
						name="hour" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Minute</label> <input type="text"
						value="<c:out value='${event.min}' />" class="form-control"
						name="min" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Duration</label> <input type="text"
						value="<c:out value='${event.duration}' />" class="form-control"
						name="duration" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Number of people</label> <input type="text"
						value="<c:out value='${event.nrPeople}' />" class="form-control"
						name="nrPeople" required="required">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>