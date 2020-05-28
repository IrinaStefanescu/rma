package ro.iran.spectacole.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ro.iran.spectacole.container.ApplicationContext;
import ro.iran.spectacole.dao.UserDao;
import ro.iran.spectacole.model.Users;

public class LoginCheckServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao;

	public LoginCheckServlet() {
		super();
		this.userDao = ApplicationContext.USER_DAO;
	}

	protected static String getUsername(HttpServletRequest request, HttpServletResponse response) {
		return request.getParameter("username");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		session.setAttribute("loggedUser", username);
		System.out.println(session.getAttribute("loggedUser"));
		UserDao userDao = ApplicationContext.USER_DAO;
		List<Users> users = userDao.getUsers();
		int ok = 0;
		for (Users element : users) {
			if (element.getUsername().equals(username) && element.getPassword().equals(password))
				if (element.getType().equals("admin"))
					ok = 1;
				else if (element.getType().equals("user"))
					ok = 2;
		}
		if (ok == 1)
			response.sendRedirect("adminIndex.jsp");
		else if (ok == 2)
			response.sendRedirect("userIndex.jsp");
		else {
			request.setAttribute("error", "Incorrect input");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String last = request.getParameter("last");
		String first = request.getParameter("first");
		String birthDate = request.getParameter("birthDate");
		String type = new String("user");
		userDao.insertUser(username, password, last, first, birthDate, type);
		response.sendRedirect("/spectacole/login.jsp");
	}
}
