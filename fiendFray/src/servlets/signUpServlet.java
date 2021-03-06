package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import base.parser;
import base.pet;
import base.stringConstants;
import base.user;
import server.MySQLDriver;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/signUpServlet")
public class signUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter(stringConstants.PASSWORD);
		String username = request.getParameter(stringConstants.USERNAME);
		String petImageURL = request.getParameter(stringConstants.PETIMAGEURL);
		String petName = request.getParameter(stringConstants.PETNAME);
		MySQLDriver msql = new MySQLDriver();
		msql.connect();
		parser newParser = msql.parseDB();
		//check to make sure all the fields were filled out
		if (username.isEmpty() || username == null){
			response.getWriter().write("no username provided");
		}
		else if (password.isEmpty() || password == null){
			response.getWriter().write("no password provided");
		}
		else if (petImageURL.isEmpty() || petImageURL == null) {
			response.getWriter().write("no pet selected");
		}
		else if (petName.isEmpty() || petName == null) {
			response.getWriter().write("no pet name provided");
		}
		else if (newParser.validUsername(username)){
			response.getWriter().write("username has already been chosen");
		}
		else{
			//create a user object
			user user = new user();
			user.setPassword(password);
			user.setUsername(username);
			user.setGuest(false);
			user.setGems(10);
			pet newPet = new pet();
			newPet.setImageURL(petImageURL);
			newPet.setName(petName);
			newPet.setMaxHP(30);
			newPet.setCurrentEXP(0);
			newPet.setCurrentLevel(1);
			newPet.setRequiredEXPToLevelUp(100);
			newPet.setCurrentHP(30);
			newPet.setEquppedWeapon(newParser.getAllWeapons().get(0));
			newPet.setWeaponID(1);
			if(petImageURL.contains("1")){
				newPet.setPetID(1);
			}
			else if(petImageURL.contains("2")){
				newPet.setPetID(2);
			}
			else{
				newPet.setPetID(3);
			}
			user.setUserPet(newPet);
			msql.addUser(user);
			HttpSession session = request.getSession(true);
			session.setAttribute(stringConstants.USER, user);
		}
	}
}