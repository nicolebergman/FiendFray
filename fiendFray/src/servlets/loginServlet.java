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
		
		if (newParser.validUsername(username)){
			//correct password
			if (newParser.correctPassword(username, password)){
				HttpSession session = request.getSession(true);
				session.setAttribute(stringConstants.USER, username);
			}
			//incorrect password
			else{
				response.getWriter().write("Incorrect password");
			}
		}
		//invalid username
		else{
			response.getWriter().write("Invalid username");
		}	
	}
		
			
}
