package server;

import java.io.IOException;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/fiendFrayServer")
public class fiendFrayServer {
	// only once instance through all instances of this class
	private static Vector<Session> sessionVector = new Vector<Session>();
	
	@OnOpen
	public void open(Session session) {
		System.out.println("user connected to server!");
		
		// notify other users (if any) of new connection
		try {
			for(Session s : sessionVector) {
				// send data back out to all clients
				s.getBasicRemote().sendText("User connected to Fiend Fray!");
			}
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
		
		// add user to sessions vector
		sessionVector.add(session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("message: " + message);
		try {
			for(Session s : sessionVector) {
				// send data back out to all clients
				s.getBasicRemote().sendText(message);
			}
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("closing");
		sessionVector.remove(session);
	}
	
	@OnError
	public void onError(Throwable error) {
		System.out.println("error");
		error.printStackTrace();
	}
}
