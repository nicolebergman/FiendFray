package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
	private static Map<String, Session> usernameToSession = new HashMap<String, Session>();
		
	@OnOpen
	public void open(Session session) {
		//System.out.println("user has connected to server!");
		
		// add user to sessions vector
		sessionVector.add(session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("message: " + message);
		
		// match message with command
		String[] commands = message.split("~");
		
		switch(commands[0]) {
		case "SwitchPage":
			// remove session from internal server vector
			String userName = commands[1];
			usernameToSession.remove(userName);
			break;
		case "Logout":
			// notify other users (if any) of disconnect
			try {
				for(String userKey : usernameToSession.keySet()) {
					// send data back out to all clients
					Session s = usernameToSession.get(userKey);
					if(s != session) {
						s.getBasicRemote().sendText(commands[1] + " has left the fray!");
					}
				}
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
			
			// remove session from internal server vector
			String username = commands[1];
			usernameToSession.remove(username);
			
			break;
		case "UserEnter":
			// send message to all other clients
			try {
				for(String userKey : usernameToSession.keySet()) {
					// send data back out to all clients
					Session s = usernameToSession.get(userKey);
					if(s != session) {
						s.getBasicRemote().sendText(commands[1]);
					}
				}
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
			break;
		case "Buy":
			// send message to all other clients
			try {
				for(String userKey : usernameToSession.keySet()) {
					// send data back out to all clients
					Session s = usernameToSession.get(userKey);
					if(s != session) {
						s.getBasicRemote().sendText(commands[1]);
					}
				}
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
			break;
		case "ChatMessage":
			// send message to other client in game
			try {
				for(String userKey : usernameToSession.keySet()) {
					// send data back out to all clients
					Session s = usernameToSession.get(userKey);
					s.getBasicRemote().sendText(commands[1]);
				}
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
			break;
		case "AddToServer":
			// add to server vector
			String name = commands[1];
			usernameToSession.put(name, session);
			break;
		default:
			System.out.print("nothin' to see here!");
			break;
		}
		
		System.out.println(usernameToSession.toString());
	}
	
	@OnClose
	public void close(Session session) {
		//System.out.println("user has disconnected from server!");
		
		//sessionVector.remove(session);
	}
	
	@OnError
	public void onError(Throwable error) {
		System.out.println("error");
	}
}
