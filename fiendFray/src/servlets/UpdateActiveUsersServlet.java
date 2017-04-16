package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.parser;
import base.stringConstants;
import base.user;
import server.MySQLDriver;

/**
 * Servlet implementation class UpdateActiveUsersServlet
 */
@WebServlet("/UpdateActiveUsersServlet")
public class UpdateActiveUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateActiveUsersServlet() {
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
		parser newParser = msql.parseDB();
		List<user> allUsers = newParser.getAllUsers();
		
		// make response string of all online
		String usersResp = "";
		for(int i = 0; i < allUsers.size(); i++) {
			if(i != 0) {
				usersResp += "~";
			}
			
			usersResp += allUsers.get(i).getUsername();
		}
		
		// write response back to ajax
		PrintWriter out = response.getWriter();
		out.print(usersResp);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
