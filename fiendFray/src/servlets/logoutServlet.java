package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import base.parser;
import base.stringConstants;
import base.user;
import server.MySQLDriver;

/**
 * Servlet implementation class logoutServlet
 */
@WebServlet("/logoutServlet")
public class logoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public logoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MySQLDriver msql = new MySQLDriver();
		msql.connect();
		parser newParser = msql.parseDB();
		HttpSession session = request.getSession(true);
		user currUser = (user) session.getAttribute(stringConstants.USER);
		currUser.isOnline=false;
		msql.updateUser(currUser);
	}
}
