package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import base.parser;
import base.stringConstants;
import base.user;
import server.MySQLDriver;


@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		MySQLDriver msql = new MySQLDriver();
		msql.connect();
		parser newParser = msql.parseDB();
		
		String username = (String)request.getParameter(stringConstants.USERNAME);
		String password = (String)request.getParameter(stringConstants.PASSWORD);
		
		user loggedInUser;
		
		if (newParser.validUsername(username)){
			// correct password
			if (newParser.correctPassword(username, password)){
				loggedInUser = newParser.getUsersMap().get(username);
				HttpSession session = request.getSession(true);
				session.setAttribute(stringConstants.USER, loggedInUser);
			}
			else{
				response.getWriter().write("Incorrect password");
			}
		}
		else{
			response.getWriter().write("Invalid username");
		}	
	}
		
			
}
