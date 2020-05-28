package ro.iran.spectacole.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.iran.spectacole.model.Room;

public class RoomDao {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // Driverul conectorului de MySQL
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	Connection conn = null;
	PreparedStatement ps = null;

	private static final String SELECT_ALL_ROOM = "select * from rooms";
	private static final String SELECT_ID_ROOM = "select roomId, floor, capacity, projector from rooms where roomId=?";
	private static final String DELETE_ROOM = "delete from rooms where roomId = ?";
	private static final String UPDATE_ROOM = "update rooms set floor=?, capacity=?,projector=? where roomId=?";
	private static final String INSERT_ROOM = "INSERT INTO rooms (floor, capacity,projector) VALUES (?,?,?);";

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
	public List<Room> getRooms() {

		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ROOM)){
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			ArrayList<Room> rooms = new ArrayList<Room>();
			while (rs.next()) {
				int roomId = rs.getInt("roomId");
				int floor = rs.getInt("floor");
				int capacity = rs.getInt("capacity");
				String projector = rs.getString("projector");
				rooms.add(new Room(roomId,floor,capacity,projector));
			}
			return rooms;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public void insertRoom(String floors, String capacitys, String projector) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROOM)) {

			int floor = Integer.parseInt(floors);
			int capacity = Integer.parseInt(capacitys);
			preparedStatement.setInt(1, floor);
			preparedStatement.setInt(2, capacity);
			preparedStatement.setString(3, projector);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean updateRoom(Room room) {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROOM)) {
			{
				preparedStatement.setInt(1, room.getFloor());
				preparedStatement.setInt(2, room.getCapacity());
				preparedStatement.setString(3, room.getProjector());
				preparedStatement.setInt(4, room.getRoomId());
				System.out.println(preparedStatement);
				rowUpdated = preparedStatement.executeUpdate() > 0;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowUpdated;
	}

	public boolean deleteRoom(int roomId) {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROOM)) {
			preparedStatement.setInt(1, roomId);
			System.out.println(preparedStatement);
			rowDeleted = preparedStatement.executeUpdate() > 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowDeleted;
	}

	public Room selectIdRoom(int id) {
		Room room = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_ROOM)) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int roomId = rs.getInt("roomId");
				int floor = rs.getInt("floor");
				int capacity = rs.getInt("capacity");
				String projector = rs.getString("projector");
				room = new Room(roomId, floor, capacity, projector);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return room;

	}

	public List<Room> selectAllRoom() {
		List<Room> rooms = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROOM)) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int roomId = rs.getInt("roomId");
				int floor = rs.getInt("floor");
				int capacity = rs.getInt("capacity");
				String projector = rs.getString("projector");
				rooms.add(new Room(roomId, floor, capacity, projector));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rooms;
	}
}
