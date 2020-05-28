package ro.iran.spectacole.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.iran.spectacole.model.Events;
import ro.iran.spectacole.model.Room;

public class EventDao {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // Driverul conectorului de MySQL
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	Connection conn = null;
	PreparedStatement ps = null;

	private static final String SELECT_ALL_EVENTS = "select * from events";
	private static final String SELECT_ID_ROOM = "select name, roomId, date, hour, min, duration, nrPeople from events where roomId=? order by date desc,hour desc,min desc";
	private static final String SELECT_ID_EVENT = "select name, roomId, date, hour, min, duration, nrPeople from events where (name,date)=(?,?)";
	private static final String DELETE_EVENT = "delete from events where (name, date) = (?,?)";
	private static final String UPDATE_EVENT = "update events set name=?, roomId=?,date=?,hour=?, min=?,duration=?, nrPeople=? where (name, date) = (?,?)";
	private static final String INSERT_EVENT = "INSERT INTO events (name, roomId, date, hour, min, duration, nrPeople) VALUES (?,?,?,?,?,?,?);";

	protected Connection getConnection() throws ClassNotFoundException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public boolean checkRoomId(String roomId) {

		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("SELECT roomId FROM rooms WHERE roomId = ?")) {
			ps.setString(1, roomId);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
					return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean checkCapacity(String roomId, String nrPeople) {

		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("SELECT roomId, capacity FROM rooms WHERE roomId = ?")) {
			ps.setString(1, roomId);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				if (Integer.parseInt(nrPeople) <= rs.getInt("capacity"))
					return true;
				else if (Integer.parseInt(nrPeople) > rs.getInt("capacity"))
					return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean checkAvailability(String roomId, String date,String hourr, String minn,String durationn) {

		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("SELECT roomId, date,hour,min,duration FROM events WHERE (roomId,date) = (?,?)")) {
			Integer hour =Integer.parseInt(hourr);
			Integer min=Integer.parseInt(minn);
			Integer duration=Integer.parseInt(durationn);
			ps.setString(1, roomId);
			ps.setString(2, date);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				if(rs.getInt("hour")<hour&&rs.getInt("hour")*60+rs.getInt("min")+rs.getInt("duration")>hour*60+min)		
					return false;
				else if(rs.getInt("hour")>hour&&rs.getInt("hour")*60+rs.getInt("min")<hour*60+min+duration)
					return false;
				else if(rs.getInt("hour")==hour&&rs.getInt("min")<min&& rs.getInt("min")+duration>min)
					return false;
				else if(rs.getInt("hour")==hour&&rs.getInt("min")>min&& rs.getInt("min")<min+duration)
					return false;
				else if(rs.getInt("hour")==hour&&rs.getInt("min")==min)
					return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	public List<Events> getEvents() {

		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_ALL_EVENTS)) {
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			ArrayList<Events> events = new ArrayList<Events>();
			while (rs.next()) {
				String name = rs.getString("name");
				int roomId = rs.getInt("roomId");
				String date = rs.getString("date");
				int hour = rs.getInt("hour");
				int min = rs.getInt("min");
				int duration = rs.getInt("duration");
				int nrPeople = rs.getInt("nrPeople");
				events.add(new Events(name, roomId, date, hour, min, duration, nrPeople));
			}
			return events;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void insertEvent(String name, String roomIds, String date, String hours, String mins, String durations,
			String nrPeoples) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_EVENT)) {
			int roomId = Integer.parseInt(roomIds);
			int hour = Integer.parseInt(hours);
			int min = Integer.parseInt(mins);
			int duration = Integer.parseInt(durations);
			int nrPeople = Integer.parseInt(nrPeoples);

			ps.setString(1, name);
			ps.setInt(2, roomId);
			ps.setString(3, date);
			ps.setInt(4, hour);
			ps.setInt(5, min);
			ps.setInt(6, duration);
			ps.setInt(7, nrPeople);
			System.out.println(ps);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean updateEvent(Events event) {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_EVENT)) {
			{
				ps.setString(1, event.getName());
				ps.setInt(2, event.getRoomId());
				ps.setString(3, event.getDate());
				ps.setInt(4, event.getHour());
				ps.setInt(5, event.getMin());
				ps.setInt(6, event.getDuration());
				ps.setInt(7, event.getNrPeople());
				ps.setString(8, event.getName());
				ps.setString(9, event.getDate());
				System.out.println(ps);
				rowUpdated = ps.executeUpdate() > 0;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowUpdated;
	}

	public boolean deleteEvent(String name, String date) {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENT)) {
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, date);
			System.out.println(preparedStatement);
			rowDeleted = preparedStatement.executeUpdate() > 0;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowDeleted;
	}

	public List<Events> selectIdRoom(Integer roomIdd) {

		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_ID_ROOM)) {
			ps.setInt(1,roomIdd);
			System.out.println(ps);
			
			ResultSet rs = ps.executeQuery();
			ArrayList<Events> events = new ArrayList<Events>();
			while (rs.next()) {
				String name = rs.getString("name");
				int roomId = rs.getInt("roomId");
				String date = rs.getString("date");
				int hour = rs.getInt("hour");
				int min = rs.getInt("min");
				int duration = rs.getInt("duration");
				int nrPeople = rs.getInt("nrPeople");
				events.add(new Events(name, roomId, date, hour, min, duration, nrPeople));
			}
			return events;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public Events selectIdEvent(String namee, String datee) {
		Events event = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_EVENT)) {
			preparedStatement.setString(1, namee);
			preparedStatement.setString(2, datee);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				int roomId = rs.getInt("roomId");
				String date = rs.getString("date");
				int hour = rs.getInt("hour");
				int min = rs.getInt("min");
				int duration = rs.getInt("duration");
				int nrPeople = rs.getInt("nrPeople");
				event = new Events(name, roomId, date, hour, min, duration, nrPeople);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return event;

	}

	public List<Events> selectAllEvents() {
		List<Events> events = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENTS)) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				int roomId = rs.getInt("roomId");
				String date = rs.getString("date");
				int hour = rs.getInt("hour");
				int min = rs.getInt("min");
				int duration = rs.getInt("duration");
				int nrPeople = rs.getInt("nrPeople");
				events.add(new Events(name, roomId, date, hour, min, duration, nrPeople));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return events;
	}
}