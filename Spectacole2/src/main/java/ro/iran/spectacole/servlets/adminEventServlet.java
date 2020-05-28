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
import ro.iran.spectacole.model.Events;

public class adminEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EventDao eventDao;

	public adminEventServlet() {
		this.eventDao = ApplicationContext.EVENT_DAO;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		System.out.println(action);
		switch (action) {
		case "/adminEvents/newEvent":
			showNewEvent(request, response);
			break;
		case "/adminEvents/insertEvent":
			try {
				if (eventDao.checkRoomId(request.getParameter("roomId"))==false) 
				{
					request.setAttribute("notadded", "The room does not exist");
					showEditEvent(request, response);
				}
				else
				{
					if (eventDao.checkCapacity(request.getParameter("roomId"), request.getParameter("nrPeople"))==false)
					{
						request.setAttribute("notadded", "The room does not support this number of people");
						showNewEvent(request, response);
					}
					else
					{
						if (eventDao.checkAvailability(request.getParameter("roomId"), request.getParameter("date"),
								(request.getParameter("hour")), request.getParameter("min"),
								request.getParameter("duration"))==false)
						{
							request.setAttribute("notadded", "The room is not available at that time");
							showNewEvent(request, response);
						}
						else
							insertEvent(request, response);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/adminEvents/deleteEvent":
			try {
				deleteEvent(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminEvents/updateEvent":
			try {
				if (eventDao.checkRoomId(request.getParameter("roomId"))) {
					if (eventDao.checkAvailability(request.getParameter("roomId"), request.getParameter("date"),
							(request.getParameter("hour")), request.getParameter("min"),
							request.getParameter("duration")))
						updateEvent(request, response);
					else {
						request.setAttribute("notadded", "The room is not available at that time");
						showEditEvent(request, response);
					}
				} else {
					request.setAttribute("notadded", "The room does not exist");
					showEditEvent(request, response);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/adminEvents/editEvent":
			try {
				showEditEvent(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminEvents":
			try {
				listEvent(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private void listEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Events> event = eventDao.selectAllEvents();
		request.setAttribute("adminEvents", event);
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminListEvents.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showNewEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminFormEvents.jsp");
		dispatcher.forward(request, response);
	}

	private void insertEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		String name = request.getParameter("name");
		String roomId = request.getParameter("roomId");
		String date = request.getParameter("date");
		String hour = request.getParameter("hour");
		String min = request.getParameter("min");
		String duration = request.getParameter("duration");
		String nrPeople = request.getParameter("nrPeople");
		eventDao.insertEvent(name, roomId, date, hour, min, duration, nrPeople);
		response.sendRedirect("/spectacole/adminEvents");
	}

	private void deleteEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String name = request.getParameter("name");
		String date = request.getParameter("date");
		eventDao.deleteEvent(name, date);
		response.sendRedirect("/spectacole/adminEvents");
	}

	private void showEditEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String name = request.getParameter("name");
		String date = request.getParameter("date");
		Events event = eventDao.selectIdEvent(name, date);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminFormEvents.jsp");
		request.setAttribute("event", event);
		dispatcher.forward(request, response);
	}

	private void updateEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String name = request.getParameter("name");
		String roomIds = request.getParameter("roomId");
		String date = request.getParameter("date");
		String hours = request.getParameter("hour");
		String mins = request.getParameter("min");
		String durations = request.getParameter("duration");
		String nrPeoples = request.getParameter("nrPeople");

		int roomId = Integer.parseInt(roomIds);
		int hour = Integer.parseInt(hours);
		int min = Integer.parseInt(mins);
		int duration = Integer.parseInt(durations);
		int nrPeople = Integer.parseInt(nrPeoples);
		Events event = new Events(name, roomId, date, hour, min, duration, nrPeople);
		eventDao.updateEvent(event);
		response.sendRedirect("/spectacole/adminEvents");
	}
}
