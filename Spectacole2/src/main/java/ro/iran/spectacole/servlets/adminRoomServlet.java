package ro.iran.spectacole.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.iran.spectacole.container.ApplicationContext;
import ro.iran.spectacole.dao.EventDao;
import ro.iran.spectacole.dao.RoomDao;
import ro.iran.spectacole.model.Events;
import ro.iran.spectacole.model.Room;

public class adminRoomServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private RoomDao roomDao;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	public adminRoomServlet() {
		this.roomDao = ApplicationContext.ROOM_DAO;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		System.out.println(action);
		switch (action) {
		case "/adminRooms/newRoom":
			showNewRoom(request, response);
			break;
		case "/adminRooms/insertRoom":
			try {
				if (request.getParameter("projector").equals("n") || request.getParameter("projector").equals("y"))
					insertRoom(request, response);
				else {
					request.setAttribute("notadded",
							"Incorrect input for projector. Type 'n' for 'no' or 'y' for 'yes'");
					showNewRoom(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminRooms/deleteRoom":
			try {
				deleteRoom(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminRooms/updateRoom":
			try {
				if (request.getParameter("projector").equals("n") || request.getParameter("projector").equals("y"))
					updateRoom(request, response);
				else {
					request.setAttribute("notadded",
							"Incorrect input for projector. Type 'n' for 'no' or 'y' for 'yes'");
					showEditRoom(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/adminRooms/editRoom":
			try {
				showEditRoom(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminRooms/calendar":
			try {
				showCalendar(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminRooms":
			try {
				listRoom(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private void listRoom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Room> room = roomDao.selectAllRoom();
		request.setAttribute("adminRooms", room);
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminListRooms.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewRoom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminFormRooms.jsp");
		dispatcher.forward(request, response);
	}

	private void insertRoom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		String floor = request.getParameter("floor");
		String capacity = request.getParameter("capacity");
		String projector = request.getParameter("projector");
		roomDao.insertRoom(floor, capacity, projector);
		response.sendRedirect("/spectacole/adminRooms");
	}

	private void deleteRoom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		int roomId = Integer.parseInt(request.getParameter("roomId"));
		roomDao.deleteRoom(roomId);
		response.sendRedirect("/spectacole/adminRooms");
	}

	private void showEditRoom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		int roomId = Integer.parseInt(request.getParameter("roomId"));
		Room room = roomDao.selectIdRoom(roomId);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminFormRooms.jsp");
		request.setAttribute("room", room);
		dispatcher.forward(request, response);
	}

	private void updateRoom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String roomIds = request.getParameter("roomId");
		String floors = request.getParameter("floor");
		String capacitys = request.getParameter("capacity");
		String projector = request.getParameter("projector");
		int roomId = Integer.parseInt(roomIds);
		int floor = Integer.parseInt(floors);
		int capacity = Integer.parseInt(capacitys);
		Room room = new Room(roomId, floor, capacity, projector);
		roomDao.updateRoom(room);
		response.sendRedirect("/spectacole/adminRooms");
	}
	private void showCalendar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		Integer roomId= Integer.parseInt(request.getParameter("roomId"));
		EventDao eventDao = ApplicationContext.EVENT_DAO;
		List<Events> events = eventDao.selectIdRoom(roomId);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminCalendar.jsp");
		request.setAttribute("adminEvents", events);
		dispatcher.forward(request, response);
	}
}
