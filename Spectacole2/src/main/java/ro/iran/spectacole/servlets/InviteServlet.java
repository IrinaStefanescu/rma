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
import ro.iran.spectacole.dao.EventDao;
import ro.iran.spectacole.dao.GoingDao;
import ro.iran.spectacole.dao.InviteDao;
import ro.iran.spectacole.dao.UserDao;
import ro.iran.spectacole.model.Events;
import ro.iran.spectacole.model.Invites;
import ro.iran.spectacole.model.Users;

public class InviteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InviteDao inviteDao;

	public InviteServlet() {
		this.inviteDao = ApplicationContext.INVITE_DAO;
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
		case "/invites/newInvite":
			showNewInvite(request, response);
			break;
		case "/invites/deleteInvite":
			try {
				deleteInvite(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/invites/deleteInvite2":
			try {
				deleteInvite(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/invites/insertInvite":
			try {
				insertInvite(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/invites/acceptInvite":
			try {
				acceptInvite(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
		case "/adminInvites":
			try {
				listAdminInvites(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/invites":
			try {
				listInvites(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	private void listInvites(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		List<Invites> invites = inviteDao.getInvites((String) session.getAttribute("loggedUser"));
		request.setAttribute("invites", invites);
		RequestDispatcher dispatcher = request.getRequestDispatcher("userListInvites.jsp");
		dispatcher.forward(request, response);
	}
	private void listAdminInvites(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Invites> invites = inviteDao.getInvitesAll();
		request.setAttribute("invites", invites);
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminListInvites.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewInvite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao userDao = ApplicationContext.USER_DAO;
		HttpSession session = request.getSession();
		String sender = (String) session.getAttribute("loggedUser");
		List<Users> users = userDao.selectInviteUsers(sender);
		request.setAttribute("users", users);

		String name = request.getParameter("name");
		String date = request.getParameter("date");
		EventDao eventDao = ApplicationContext.EVENT_DAO;
		Events event = eventDao.selectIdEvent(name, date);
		request.setAttribute("event", event);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/userFormInvites.jsp");
		dispatcher.forward(request, response);
	}

	private void insertInvite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		HttpSession session = request.getSession();
		String sender = (String) session.getAttribute("loggedUser");
		String username = request.getParameter("receiver");
		String name = request.getParameter("name");
		String date = request.getParameter("date");
		inviteDao.insertInvite(sender, username, name, date);
		response.sendRedirect("/spectacole/events");
	}

	private void deleteInvite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		String receiver = (String) session.getAttribute("loggedUser");
		String username = request.getParameter("sender");
		String name = request.getParameter("name");
		String date = request.getParameter("date");
		inviteDao.deleteInvite(username, receiver, name, date);
		response.sendRedirect("/spectacole/invites");
	}
	private void acceptInvite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		String receiver = (String) session.getAttribute("loggedUser");
		String username = request.getParameter("sender");
		String name = request.getParameter("name");
		String date = request.getParameter("date");
		
		GoingDao goingDao = ApplicationContext.GOING_DAO;
		goingDao.insertGoing(receiver, name, date);
		inviteDao.deleteInvite(username, receiver, name, date);
		
		response.sendRedirect("/spectacole/going");
	}
}
