package ro.iran.spectacole.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.iran.spectacole.model.Users;

public class UserDao {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // Driverul conectorului de MySQL
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	Connection conn = null;
	PreparedStatement ps = null;

	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String SELECT_ID_USER = "select username, password, last, first, birthDate, type from users where username=?";
	private static final String SELECT_INVITE_USERS = "select * from users where type='user' and username != ?";
	private static final String DELETE_USER = "delete from users where username = ?";
	private static final String UPDATE_USER = "update users set username=?, password=?,last=?,first=?,birthDate=?,type=? where username=?";
	private static final String INSERT_USER = "INSERT INTO users (username,password,last,first,birthDate,type) VALUES (?,?,?,?,?,?);";
	private static final String CREDENTIALS_USER = "select * from users where (username,last,first,birthDate,type)= (?,?,?,?,?);";
	private static final String CHANGE_PASSWORD = "update users set password=? where username=?";
	private static final String RETURN_TYPE_USER = "select type from users where username=?";
	

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

	public List<Users> getUsers() {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS)) {
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			ArrayList<Users> users = new ArrayList<Users>();
			while (rs.next()) {

				String username = rs.getString("username");
				String password = rs.getString("password");
				String last = rs.getString("last");
				String first = rs.getString("first");
				String birthDate = rs.getString("birthDate");
				String type = rs.getString("type");
				users.add(new Users(username, password, last, first, birthDate, type));
			}
			return users;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void insertUser(String username, String password, String last, String first, String birthDate, String type) {
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_USER)) {

			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, last);
			ps.setString(4, first);
			ps.setString(5, birthDate);
			ps.setString(6, type);
			System.out.println(ps);
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean updateUser(Users users) {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
			{
				preparedStatement.setString(1, users.getUsername());
				preparedStatement.setString(2, users.getPassword());
				preparedStatement.setString(3, users.getLast());
				preparedStatement.setString(4, users.getFirst());
				preparedStatement.setString(5, users.getBirthDate());
				preparedStatement.setString(6, users.getType());
				preparedStatement.setString(7, users.getUsername());
				System.out.println(preparedStatement);
				rowUpdated = preparedStatement.executeUpdate() > 0;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowUpdated;
	}

	public boolean deleteUser(String username) {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
			preparedStatement.setString(1, username);
			System.out.println(preparedStatement);
			rowDeleted = preparedStatement.executeUpdate() > 0;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowDeleted;
	}
	public String returnType(String name) {
		String type = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(RETURN_TYPE_USER)) {
			preparedStatement.setString(1, name);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				type = rs.getString("type");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return type;
	}
	
	public Users selectIdUser(String name) {
		Users users = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_USER)) {
			preparedStatement.setString(1, name);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String last = rs.getString("last");
				String first = rs.getString("first");
				String birthDate = rs.getString("birthDate");
				String type = rs.getString("type");
				users = new Users(username, password, last, first, birthDate, type);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return users;
	}

	public List<Users> selectAllUsers() {
		List<Users> users = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String last = rs.getString("last");
				String first = rs.getString("first");
				String birthDate = rs.getString("birthDate");
				String type = rs.getString("type");
				users.add(new Users(username, password, last, first, birthDate, type));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return users;
	}

	public List<Users> selectInviteUsers(String loggedUser) {
		List<Users> users = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INVITE_USERS)) {
			preparedStatement.setString(1, loggedUser);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String last = rs.getString("last");
				String first = rs.getString("first");
				String birthDate = rs.getString("birthDate");
				String type = rs.getString("type");
				users.add(new Users(username, password, last, first, birthDate, type));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return users;
	}

	public boolean verifyProfile(Users users) {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREDENTIALS_USER)) {
			{
				preparedStatement.setString(1, users.getUsername());
				preparedStatement.setString(2, users.getLast());
				preparedStatement.setString(3, users.getFirst());
				preparedStatement.setString(4, users.getBirthDate());
				preparedStatement.setString(5, users.getType());
				System.out.println(preparedStatement);
				preparedStatement.executeQuery();
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					rowUpdated = true;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowUpdated;
	}

	public boolean changePassword(Users users) {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PASSWORD)) {
			{
				preparedStatement.setString(1, users.getPassword());
				preparedStatement.setString(2, users.getUsername());
				System.out.println(preparedStatement);
				rowUpdated = preparedStatement.executeUpdate() > 0;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowUpdated;
	}
}
