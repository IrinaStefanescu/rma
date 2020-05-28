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
	<p style="color: red">${notadded}</p>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${room != null}">
					<form action="<%=request.getContextPath()%>/adminRooms/updateRoom" method="post">
				</c:if>
				<c:if test="${room == null}">
					<form action="<%=request.getContextPath()%>/adminRooms/insertRoom" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${room != null}">
            			Edit Room
            		</c:if>
						<c:if test="${room == null}">
            			Add New Room
            		</c:if>
					</h2>
				</caption>

				<c:if test="${room != null}">
					<td><input type="hidden" name="roomId"
						value="<c:out value='${room.roomId}' />" /></td>
				</c:if>


				<fieldset class="form-group">
					<label>Floor</label> <input type="text"
						value="<c:out value='${room.floor}' />" class="form-control"
						name="floor" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Capacity</label> <input type="text"
						value="<c:out value='${room.capacity}' />" class="form-control"
						name="capacity">
				</fieldset>

				<fieldset class="form-group">
					<label>Projector</label> <input type="text"
						value="<c:out value='${room.projector}' />" class="form-control"
						name="projector">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>