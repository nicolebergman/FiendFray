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

import base.stringConstants;


@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		DataStorage ds = (DataStorage) request.getSession().getAttribute(StringConstants.DATA);

		String username = (String)request.getParameter(stringConstants.USERNAME);
		String password = (String)request.getParameter(stringConstants.PASSWORD);
		//if it is a valid username
		if (password.isEmpty() || password == null){
			response.getWriter().write("no password provided");
		}
		else if (username.isEmpty() || username == null){
			response.getWriter().write("no username provided");
		}
	}
		
			
}
