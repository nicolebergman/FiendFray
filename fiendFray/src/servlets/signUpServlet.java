package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import base.stringConstants;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class signUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		DataStorage ds = (DataStorage) request.getSession().getAttribute(StringConstants.DATA);
		String password = request.getParameter(stringConstants.PASSWORD);
		String username = request.getParameter(stringConstants.USERNAME);
		//check to make sure all the fields were filled out
		if (password.isEmpty() || password == null){
			response.getWriter().write("no password provided");
		}
		else if (username.isEmpty() || username == null){
			response.getWriter().write("no username provided");
		}
//		else if (ds.validUsername(username)){
//			response.getWriter().write("username has already been chosen");
//		}
		else{
			
//				//create a user object
//				User user = new User();
//				user.setPassword(password);
//				user.setUsername(username);
//				//add the user to the xml file
//				ds.addUser(user);
//				//set the loggedin user to be the new user
//				ds.setLoggedInUser(user.getUsername());
		}
	}
}
