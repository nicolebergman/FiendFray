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
import base.pet;
import base.stringConstants;
import base.user;
import server.MySQLDriver;


@WebServlet("/loginServlet")
public class loginGuestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		MySQLDriver msql = new MySQLDriver();
		msql.connect();
		parser newParser = msql.parseDB();
		user newGuest = new user();
		newGuest.setGems(10);
		newGuest.setGuest(true);
		newGuest.setPassword("guest");
		newGuest.setUsername("Guest"+newParser.getAllUsers().size());
		pet guestPet = new pet();
		guestPet.setCurrentHP(30);
		guestPet.setCurrentLevel(1);
		guestPet.setEquppedWeapon(newParser.getAllWeapons().get(0));
		guestPet.setImageURL("/images/pet1.png");
		guestPet.setMaxHP(30);
		guestPet.setName("Guest Pet");
		newParser.addUser(newGuest);
		HttpSession session = request.getSession(true);
		session.setAttribute(stringConstants.USER, newGuest);
	}
		
			
}
