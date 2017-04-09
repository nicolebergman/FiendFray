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
import base.user;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/signUpServlet")
public class signUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		DataStorage ds = (DataStorage) request.getSession().getAttribute(StringConstants.DATA);
		String password = request.getParameter(stringConstants.PASSWORD);
		String username = request.getParameter(stringConstants.USERNAME);
		String pet = request.getParameter(stringConstants.PET);
		String petName = request.getParameter(stringConstants.PETNAME);
		
		//check to make sure all the fields were filled out
		if (password.isEmpty() || password == null){
			response.getWriter().write("no password provided");
		}
		else if (username.isEmpty() || username == null){
			response.getWriter().write("no username provided");
		}
		else if (pet.isEmpty() || pet == null) {
			response.getWriter().write("no pet selected");
		}
		else if (petName.isEmpty() || petName == null) {
			response.getWriter().write("no pet name provided");
		}
//		else if (ds.validUsername(username)){
//			response.getWriter().write("username has already been chosen");
//		}
		else{
			
				//create a user object
				user user = new user();
				user.setPassword(password);
				user.setUsername(username);
//				user.setUserPet(userPet);
				//add the user to the database
//				ds.addUser(user);
//				//set the loggedin user to be the new user
//				ds.setLoggedInUser(user.getUsername());
		}
	}
}
