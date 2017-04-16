package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import base.parser;
import base.stringConstants;
import base.user;
import base.weapon;
import server.MySQLDriver;

/**
 * Servlet implementation class buyServlet
 */
@WebServlet("/buyServlet")
public class buyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public buyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		MySQLDriver msql = new MySQLDriver();
		msql.connect();
		parser newParser = msql.parseDB();
		
		String value = (String)request.getParameter("value");
		String weaponName = (String)request.getParameter("name");
		int weaponID = Integer.parseInt(value);
		HttpSession session = request.getSession(true);
		user currUser=(user) session.getAttribute(stringConstants.USER);
		weapon boughtWeapon = newParser.getAllWeapons().get(weaponID-1);
		if(currUser.getUserPet().getEquippedWeapon().getPrice()==boughtWeapon.getPrice()){
			response.getWriter().write(currUser.getGems()+"~Your pet is already using "+weaponName);
		}
		else if(currUser.getGems()>=boughtWeapon.getPrice()){
			currUser.setGems(currUser.getGems()-boughtWeapon.getPrice());
			currUser.getUserPet().setEquppedWeapon(newParser.getAllWeapons().get(weaponID-1));
			msql.userBought(currUser, currUser.getID());
			newParser = msql.parseDB();
			session.setAttribute(stringConstants.USER, newParser.getAllUsers().get(currUser.getID()-1));
			response.getWriter().write(currUser.getGems()+"~You successfully bought a "+weaponName);
			
		}
		else{
			response.getWriter().write(currUser.getGems()+"~You don't have enough gems to buy a "+weaponName);
		}
	}
}
