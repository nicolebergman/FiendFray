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
import server.MySQLDriver;

/**
 * Servlet implementation class WinnerServlet
 */
@WebServlet("/WinnerServlet")
public class WinnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WinnerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get user from server
		MySQLDriver msql = new MySQLDriver();
		msql.connect();
		//parser newParser = msql.parseDB();
		
		String username = (String) request.getParameter("username");
		String winner = (String) request.getParameter("winner");
		HttpSession session = request.getSession(true);
		user currUser = (user) session.getAttribute(stringConstants.USER);
		
		// update exp and gems
		int currExp = currUser.getUserPet().getCurrentEXP();
		int requiredExp = currUser.getUserPet().getRequiredEXPToLevelUp();
		
		System.out.println(requiredExp);
		
		int expReward = 0;
		int gemReward = 0;
		if(winner.equals("true")) {
			expReward = 50;
			gemReward = 20;
		} else {
			expReward = 20;
			gemReward = 5;
		}
		
		if(currExp + expReward >= requiredExp) {
			currUser.getUserPet().setCurrentLevel(currUser.getUserPet().getCurrentLevel() + 1);
			currUser.getUserPet().setMaxHP(currUser.getUserPet().getMaxHP() + 20);
			currUser.getUserPet().setCurrentEXP(currExp + expReward - requiredExp);
		} else {
			currUser.getUserPet().setCurrentEXP(currExp + expReward);
		}
		
		currUser.setGems(currUser.getGems() + gemReward);
		
		// update in server
		msql.updateEXP(currUser);
		msql.updateGems(currUser);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
