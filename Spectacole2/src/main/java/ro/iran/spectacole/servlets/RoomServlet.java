package ro.iran.spectacole.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ro.iran.spectacole.container.ApplicationContext;
import ro.iran.spectacole.dao.EventDao;
import ro.iran.spectacole.dao.RoomDao;
import ro.iran.spectacole.model.Events;
import ro.iran.spectacole.model.Room;

public class RoomServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private RoomDao roomDao = ApplicationContext.ROOM_DAO;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		switch (action) {
		case "/rooms":
			try {
				listRoom(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/rooms/calendar":
			try {
				showCalendar(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private void listRoom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Room> room = roomDao.selectAllRoom();
		request.setAttribute("rooms", room);
		RequestDispatcher dispatcher = request.getRequestDispatcher("rooms.jsp");
		dispatcher.forward(request, response);
	}
	private void showCalendar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		Integer roomId= Integer.parseInt(request.getParameter("roomId"));
		EventDao eventDao = ApplicationContext.EVENT_DAO;
		List<Events> events = eventDao.selectIdRoom(roomId);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/userCalendar.jsp");
		request.setAttribute("events", events);
		dispatcher.forward(request, response);
	}

}
