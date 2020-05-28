package ro.iran.spectacole.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.iran.spectacole.container.ApplicationContext;
import ro.iran.spectacole.dao.EventDao;
import ro.iran.spectacole.model.Events;

public class EventServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private EventDao eventDao = ApplicationContext.EVENT_DAO;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Events> event = eventDao.selectAllEvents();
		request.setAttribute("events", event);
		RequestDispatcher dispatcher = request.getRequestDispatcher("events.jsp");
		dispatcher.forward(request, response);
	}
}
