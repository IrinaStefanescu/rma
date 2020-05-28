package ro.iran.spectacole.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.iran.spectacole.model.Events;
import ro.iran.spectacole.model.Invites;
import ro.iran.spectacole.model.Users;

public class InviteDao {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	Connection conn = null;
	PreparedStatement ps = null;
	private static final String SELECT_ALL_INVITES = "select name,date,roomId,hour,min,duration,nrPeople,sender,user1.password senderPassword,"
			+ " user1.last senderLast, user1.first senderFirst, user1.birthDate senderBirthDate, user1.type senderType, receiver, user2.password receiverPassword,"
			+ " user2.last receiverLast,user2.first receiverFirst, user2.birthDate receiverBirthDate, user2.type receiverType FROM events join invites"
			+ " using (name,date) join users user1 on sender=user1.username join users user2 on receiver=user2.username;";
	private static final String SELECT_USER_INVITES = "select name,date,roomId,hour,min,duration,nrPeople,sender,user1.password senderPassword,"
			+ " user1.last senderLast, user1.first senderFirst, user1.birthDate senderBirthDate, user1.type senderType, receiver, user2.password receiverPassword,"
			+ " user2.last receiverLast,user2.first receiverFirst, user2.birthDate receiverBirthDate, user2.type receiverType FROM events join invites"
			+ " using (name,date) join users user1 on sender=user1.username join users user2 on receiver=user2.username where receiver =?";
	private static final String DELETE_INVITE = "delete from invites where  (sender,receiver, name, date) = (?,?,?,?);";
	private static final String INSERT_INVITE = "insert into invites (sender,receiver, name, date) VALUES (?,?,?,?);";

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
	public List<Invites> getInvitesAll() {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_ALL_INVITES)) {
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			ArrayList<Invites> invite = new ArrayList<Invites>();
			while (rs.next()) {
				String name = rs.getString("name");
				int roomId = rs.getInt("roomId");
				String date = rs.getString("date");
				int hour = rs.getInt("hour");
				int min = rs.getInt("min");
				int duration = rs.getInt("duration");
				int nrPeople = rs.getInt("nrPeople");
				Events event = new Events(name, roomId, date, hour, min, duration, nrPeople);

				String sender = rs.getString("sender");
				String senderPassword = rs.getString("senderPassword");
				String senderLast = rs.getString("senderLast");
				String senderFirst = rs.getString("senderFirst");
				String senderBirthDate = rs.getString("senderBirthDate");
				String senderType = rs.getString("senderType");
				Users user1 = new Users(sender, senderPassword, senderLast, senderFirst, senderBirthDate, senderType); //sender
				
				String receiver = rs.getString("receiver");
				String receiverPassword = rs.getString("receiverPassword");
				String receiverLast = rs.getString("receiverLast");
				String receiverFirst = rs.getString("receiverFirst");
				String receiverBirthDate = rs.getString("receiverBirthDate");
				String receiverType = rs.getString("receiverType");
				Users user2 = new Users(receiver, receiverPassword, receiverLast, receiverFirst, receiverBirthDate, receiverType);
				invite.add(new Invites(event, user1,user2));
			}
			return invite;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public List<Invites> getInvites(String loggedUser) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_USER_INVITES)) {
			ps.setString(1, loggedUser);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			ArrayList<Invites> invite = new ArrayList<Invites>();
			while (rs.next()) {
				String name = rs.getString("name");
				int roomId = rs.getInt("roomId");
				String date = rs.getString("date");
				int hour = rs.getInt("hour");
				int min = rs.getInt("min");
				int duration = rs.getInt("duration");
				int nrPeople = rs.getInt("nrPeople");
				Events event = new Events(name, roomId, date, hour, min, duration, nrPeople);

				String sender = rs.getString("sender");
				String senderPassword = rs.getString("senderPassword");
				String senderLast = rs.getString("senderLast");
				String senderFirst = rs.getString("senderFirst");
				String senderBirthDate = rs.getString("senderBirthDate");
				String senderType = rs.getString("senderType");
				Users user1 = new Users(sender, senderPassword, senderLast, senderFirst, senderBirthDate, senderType); //sender
				
				String receiver = rs.getString("receiver");
				String receiverPassword = rs.getString("receiverPassword");
				String receiverLast = rs.getString("receiverLast");
				String receiverFirst = rs.getString("receiverFirst");
				String receiverBirthDate = rs.getString("receiverBirthDate");
				String receiverType = rs.getString("receiverType");
				Users user2 = new Users(receiver, receiverPassword, receiverLast, receiverFirst, receiverBirthDate, receiverType);
				invite.add(new Invites(event, user1,user2));
			}
			return invite;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void insertInvite(String sender,String receiver, String name, String date) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_INVITE)) {
			ps.setString(1, sender);
			ps.setString(2, receiver);
			ps.setString(3, name);
			ps.setString(4, date);
			System.out.println(ps);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean deleteInvite(String sender,String receiver, String name, String date) {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INVITE)) {
			preparedStatement.setString(1, sender);
			preparedStatement.setString(2, receiver);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, date);
			System.out.println(preparedStatement);
			rowDeleted = preparedStatement.executeUpdate() > 0;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowDeleted;
	}
}
