package ro.iran.spectacole.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ro.iran.spectacole.container.ApplicationContext;
import ro.iran.spectacole.dao.GoingDao;
import ro.iran.spectacole.model.Going;

public class GoingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private GoingDao goingDao;

	public GoingServlet() {
		this.goingDao = ApplicationContext.GOING_DAO;
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
		case "/going/deleteGoing":
			try {
				deleteGoing(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/going/insertGoing":
			try {
				insertGoing(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminGoing":
			try {
				listGoingAll(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/going":
			try {
				listGoing(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private void listGoing(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		List<Going> going = goingDao.getGoing((String)session.getAttribute("loggedUser"));
		request.setAttribute("going", going);
		RequestDispatcher dispatcher = request.getRequestDispatcher("going.jsp");
		dispatcher.forward(request, response);
	}

	private void listGoingAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Going> going = goingDao.getGoingAll();
		request.setAttribute("going", going);
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminListGoing.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertGoing(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("loggedUser");
		String name = request.getParameter("name");
		String date = request.getParameter("date");
		goingDao.insertGoing(username, name, date);
		response.sendRedirect("/spectacole/going");
	}

	private void deleteGoing(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("loggedUser");
		String name = request.getParameter("name");
		String date = request.getParameter("date");
		goingDao.deleteGoing(username, name, date);
		response.sendRedirect("/spectacole/going");
	}
}
