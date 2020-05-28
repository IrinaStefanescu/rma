<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>

<%
	Gson gsonObj = new Gson();
Map<Object, Object> map = null;
List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
String dataPoints1 = null;

Map<Object, Object> map2 = null;
List<Map<Object, Object>> list2 = new ArrayList<Map<Object, Object>>();
String dataPoints2 = null;

Map<Object, Object> map3 = null;
List<Map<Object, Object>> list3 = new ArrayList<Map<Object, Object>>();
String dataPoints3 = null;

Map<Object, Object> map4 = null;
List<Map<Object, Object>> list4 = new ArrayList<Map<Object, Object>>();
String dataPoints4 = null;
try {
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "");
	Connection connection2 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "");
	Connection connection3 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "");
	Connection connection4 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "");
	Statement statement = connection.createStatement();
	Statement statement2 = connection2.createStatement();
	Statement statement3 = connection3.createStatement();
	Statement statement4 = connection4.createStatement();

	Integer xVal, yVal;
	ResultSet resultSet = statement.executeQuery(
	"select roomId, count(*)/(select count(*) from events)*100 count from events group by roomId");
	while (resultSet.next()) {
		xVal = resultSet.getInt("roomId");
		yVal = resultSet.getInt("count");
		map = new HashMap<Object, Object>();
		map.put("label", xVal);
		map.put("y", yVal);
		list.add(map);
		dataPoints1 = gsonObj.toJson(list);
	}

	String xVal2;
	Integer yVal2;
	ResultSet resultSet2 = statement2.executeQuery("select name, duration from events");
	while (resultSet2.next()) {
		xVal2 = resultSet2.getString("name");
		yVal2 = resultSet2.getInt("duration");
		map2 = new HashMap<Object, Object>();
		map2.put("label", xVal2);
		map2.put("y", yVal2);
		list2.add(map2);
		dataPoints2 = gsonObj.toJson(list2);
	}

	Integer yVal3, xVal3;
	ResultSet resultSet3 = statement3.executeQuery(
	"select (select count(*) from rooms where projector='y') proj,count(roomId) total from rooms");
	while (resultSet3.next()) {
		Integer zVal=resultSet3.getInt("total");
		xVal3 = (zVal - resultSet3.getInt("proj"))*100/zVal;
		yVal3 = (resultSet3.getInt("proj"))*100/zVal;
		map3 = new HashMap<Object, Object>();
		map3.put("label", "Rooms with projector");
		map3.put("y", yVal3);
		list3.add(map3);
		map3 = new HashMap<Object, Object>();
		map3.put("label", "Rooms without projector");
		map3.put("y", xVal3);
		list3.add(map3);
		dataPoints3 = gsonObj.toJson(list3);
	}

	String xVal4;
	Integer yVal4;
	ResultSet resultSet4 = statement4.executeQuery("select username, count(*)/(select count(*) from going)*100 count from going group by username");
	while (resultSet4.next()) {
		xVal4 = resultSet4.getString("username");
		yVal4 = resultSet4.getInt("count");
		map4 = new HashMap<Object, Object>();
		map4.put("label", xVal4);
		map4.put("y", yVal4);
		list4.add(map4);
		dataPoints4 = gsonObj.toJson(list4);
	}

	connection.close();
	connection2.close();
	connection3.close();
	connection4.close();
} catch (SQLException e) {
	System.out.println(e.getMessage());
	dataPoints1 = null;
	dataPoints2 = null;
	dataPoints3 = null;
	dataPoints4 = null;
}
%>
<!DOCTYPE HTML>
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

	<script>
		window.onload = function() {
	<%if (dataPoints1 != null) {%>
		var chart = new CanvasJS.Chart("chartContainer1", {
				theme : "light2", // "light1", "dark1", "dark2"
				animationEnabled : true,
				exportEnabled : true,
				title : {
					text :"Room statistics"
				},
				data : [ {
					type : "pie",
					toolTipContent : "<b>{label}</b>: {y}%",
					indexLabelFontSize : 16,
					indexLabel : "Room {label} - {y}%",
					dataPoints :
	<%out.print(dataPoints1);%>
		} ]
			});
			chart.render();
	<%}%>
		
	<%if (dataPoints2 != null) {%>
		var chart2 = new CanvasJS.Chart("chartContainer2", {
				theme : "light2", // "light1", "dark1", "dark2"
				animationEnabled : true,
				exportEnabled : true,
				title : {
					text : "Events duration"
				},
				data : [ {
					type : "pie",
					toolTipContent : "<b>{label}</b>: {y}%",
					indexLabelFontSize : 16,
					indexLabel : "Event {label} - {y}",
					dataPoints :
	<%out.print(dataPoints2);%>
		} ]
			});
			chart2.render();
	<%}%>
		
	<%if (dataPoints3 != null) {%>
		var chart3 = new CanvasJS.Chart("chartContainer3", {
				theme : "light3", // "light1", "dark1", "dark2"
				animationEnabled : true,
				exportEnabled : true,
				title : {
					text : "Projector statistics"
				},
				data : [ {
					type : "pie",
					toolTipContent : "<b>{label}</b>: {y}%",
					indexLabelFontSize : 16,
					indexLabel : "{label} - {y}%",
					dataPoints :
	<%out.print(dataPoints3);%>
		} ]
			});
			chart3.render();
	<%}%>
		
	<%if (dataPoints4 != null) {%>
		var chart4 = new CanvasJS.Chart("chartContainer4", {
				theme : "light1", // "light1", "dark1", "dark2"
				animationEnabled : true,
				exportEnabled : true,
				title : {
					text : "Most active users"
				},
				data : [ {
					type : "pie",
					toolTipContent : "<b>{label}</b>: {y}%",
					indexLabelFontSize : 16,
					indexLabel : "{label} - {y}%",
					dataPoints :
	<%out.print(dataPoints4);%>
		} ]
			});
			chart4.render();
	<%}%>
		}
	</script>

	<h1 style="text-align: center">Project statistics</h1>
	<br/><br/>
<body>
	<div id="chartContainer1"
		style="width: 45%; height: 230px; display: inline-block;"></div>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>

<body>
	<div id="chartContainer2"
		style="width: 45%; height: 230px; display: inline-block;"></div>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
<br />
<body>
	<div id="chartContainer3"
		style="width: 45%; height: 230px; display: inline-block;"></div>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>

<body>
	<div id="chartContainer4"
		style="width: 45%; height: 230px; display: inline-block;"></div>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</body>
</html>