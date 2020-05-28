package ro.iran.spectacole.container;

import ro.iran.spectacole.dao.EventDao;
import ro.iran.spectacole.dao.RoomDao;
import ro.iran.spectacole.dao.UserDao;
import ro.iran.spectacole.dao.GoingDao;
import ro.iran.spectacole.dao.InviteDao;

public class ApplicationContext {

	public static final RoomDao ROOM_DAO = new RoomDao();
	public static final EventDao EVENT_DAO = new EventDao();
	public static final UserDao USER_DAO = new UserDao();
	public static final GoingDao GOING_DAO = new GoingDao();
	public static final InviteDao INVITE_DAO = new InviteDao();
	
}
