package ro.iran.spectacole.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ro.iran.spectacole.model.Events;
import ro.iran.spectacole.model.Going;
import ro.iran.spectacole.model.Users;

public class GoingDao {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	Connection conn = null;
	PreparedStatement ps = null;
	
	private static final String SELECT_ALL_EVENTS = "select * from users join going using (username) join events using (name,date)";
	private static final String SELECT_EVENTS_USER = "select * from users join going using (username) join events using (name,date) where username =?";
	private static final String DELETE_EVENT = "delete from going where (username, name, date) = (?,?,?);";
	private static final String INSERT_EVENT = "insert into going (username, name, date) VALUES (?,?,?);";

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

	public List<Going> getGoingAll() {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_ALL_EVENTS)) {
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			ArrayList<Going> going = new ArrayList<Going>();
			while (rs.next()) {
				String name = rs.getString("name");
				int roomId = rs.getInt("roomId");
				String date = rs.getString("date");
				int hour = rs.getInt("hour");
				int min = rs.getInt("min");
				int duration = rs.getInt("duration");
				int nrPeople = rs.getInt("nrPeople");
				Events event = new Events(name, roomId, date, hour, min, duration, nrPeople);

				String username = rs.getString("username");
				String password = rs.getString("password");
				String last = rs.getString("last");
				String first = rs.getString("first");
				String birthDate = rs.getString("birthDate");
				String type = rs.getString("type");
				Users user = new Users(username, password, last, first, birthDate, type);
				going.add(new Going(event, user));
			}
			return going;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public List<Going> getGoing(String loggedUser) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_EVENTS_USER)) {
			ps.setString(1, loggedUser);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			ArrayList<Going> going = new ArrayList<Going>();
			while (rs.next()) {
				String name = rs.getString("name");
				int roomId = rs.getInt("roomId");
				String date = rs.getString("date");
				int hour = rs.getInt("hour");
				int min = rs.getInt("min");
				int duration = rs.getInt("duration");
				int nrPeople = rs.getInt("nrPeople");
				Events event = new Events(name, roomId, date, hour, min, duration, nrPeople);

				String username = rs.getString("username");
				String password = rs.getString("password");
				String last = rs.getString("last");
				String first = rs.getString("first");
				String birthDate = rs.getString("birthDate");
				String type = rs.getString("type");
				Users user = new Users(username, password, last, first, birthDate, type);
				going.add(new Going(event, user));
			}
			return going;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void insertGoing(String username, String name, String date) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_EVENT)) {
			ps.setString(1, username);
			ps.setString(2, name);
			ps.setString(3, date);
			System.out.println(ps);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean deleteGoing(String username, String name, String date) {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENT)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, date);
			System.out.println(preparedStatement);
			rowDeleted = preparedStatement.executeUpdate() > 0;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowDeleted;
	}
}
