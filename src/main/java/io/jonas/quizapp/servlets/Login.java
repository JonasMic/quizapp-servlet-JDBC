package io.jonas.quizapp.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import io.jonas.quizapp.beans.UserInfo;
import io.jonas.quizapp.dao.UserDao;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(
				"./views/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println(username);
		System.out.println(password);

		// get the user from the database

		UserDao dao = new UserDao();
		UserInfo user = dao.getUserByName(username);
		System.out.println(user.getPassword());
		if (password.equals(user.getPassword())) {
			HttpSession session = req.getSession();
			session.setAttribute("user", username);
			session.setAttribute("role", user.getRole());
			// setting session to expire in 30 minutes
			session.setMaxInactiveInterval(1 * 60);
			Cookie userCookie = new Cookie("user", username);
			Cookie roleCookie = new Cookie("role", user.getRole());
			userCookie.setMaxAge(1 * 60);
			res.addCookie(userCookie);
			res.addCookie(roleCookie);
			res.sendRedirect(req.getContextPath()+"/questions");
			//RequestDispatcher dispatcher = req.getRequestDispatcher("questions");
			//dispatcher.forward(req, res);

		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/errorPage.jsp");
			dispatcher.forward(req, res);
		}
	}

}
