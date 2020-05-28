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

public class adminUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	public adminUserServlet() {
		this.userDao = ApplicationContext.USER_DAO;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		System.out.println(action);
		switch (action) {
		case "/adminUsers/newUser":
			showNewUser(request, response);
			break;
		case "/adminUsers/insertUser":
			try {
				insertUser(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminUsers/deleteUser":
			try {
				deleteUser(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminUsers/updateUser":
			try {
				updateUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/adminUsers/editUser":
			try {
				showEditUser(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminUsers/change":
			try {
				verifyProfile(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/adminUsers":
			try {
				listUsers(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private void listUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Users> users = userDao.selectAllUsers();
		request.setAttribute("adminUsers", users);
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminListUsers.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminFormUsers.jsp");
		dispatcher.forward(request, response);
	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String last = request.getParameter("last");
		String first = request.getParameter("first");
		String birthDate = request.getParameter("birthDate");
		String type = request.getParameter("type");
		userDao.insertUser(username, password, last, first, birthDate, type);
		response.sendRedirect("/spectacole/adminUsers");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		userDao.deleteUser(request.getParameter("username"));
		response.sendRedirect("/spectacole/adminUsers");
	}

	private void showEditUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String name = request.getParameter("username");
		Users user = userDao.selectIdUser(name);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminFormUsers.jsp");
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
		if (!(type.equals("admin") || type.equals("user"))) {
			request.setAttribute("error", "Incorrect type");
			showEditUser(request, response);
		} else {
			Users users = new Users(username, password, last, first, birthDate, type);
			userDao.updateUser(users);
			HttpSession session = request.getSession();
			String loggedUser = (String) session.getAttribute("loggedUser");
			if (userDao.returnType(loggedUser).equals("admin"))
				response.sendRedirect("/spectacole/adminUsers");
			else
				response.sendRedirect("/spectacole/users");
		}
	}

	private void verifyProfile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String username = request.getParameter("username");
		String last = request.getParameter("last");
		String first = request.getParameter("first");
		System.out.println(last + " " + first);
		String birthDate = request.getParameter("birthDate");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if (!password1.equals(password2)) {
			request.setAttribute("errorPassword", "Password mismatch");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/forgotMyPassword.jsp");
			dispatcher.forward(request, response);
		} else {
			String type = "user";
			Users users = new Users(username, password1, last, first, birthDate, type);
			if (userDao.verifyProfile(users)) {
				userDao.changePassword(users);
				response.sendRedirect("/spectacole");
			} else {
				request.setAttribute("errorPassword", "Wrong credentials");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/forgotMyPassword.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
