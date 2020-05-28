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
import ro.iran.spectacole.dao.UserDao;
import ro.iran.spectacole.model.Users;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserDao userDao = ApplicationContext.USER_DAO;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		System.out.println(action);
		switch (action) {
		case "/users":
			try {
				listUsers(request, response);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "/users/editProfile":
			try {
				showEditProfile(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/users/updateUser":
			try {
				updateUser(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private void listUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Users> users = userDao.selectAllUsers();
		request.setAttribute("users", users);
		RequestDispatcher dispatcher = request.getRequestDispatcher("users.jsp");
		dispatcher.forward(request, response);
	}
	private void showEditProfile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("loggedUser");
		Users user = userDao.selectIdUser(name);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/userFormUsers.jsp");
		request.setAttribute("user", user);
		dispatcher.forward(request, response);
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String last = request.getParameter("last");
		String first = request.getParameter("first");
		String birthDate = request.getParameter("birthDate");
		String type = request.getParameter("type");
		Users users = new Users(username, password, last, first, birthDate, type);
		userDao.updateUser(users);
		response.sendRedirect("/spectacole/userIndex.jsp");
	}

}
