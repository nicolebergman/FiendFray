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
	private static boolean exitGame = true;
		
	@OnOpen
	public void open(Session session) {
		System.out.println("user has connected to server!");
		
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
			// false positive for game exit
			exitGame = false;
			break;
		default:
			// send message to all other clients
			try {
				for(Session s : sessionVector) {
					// send data back out to all other clients
					if(s != session) {
						s.getBasicRemote().sendText(message);
					}
				}
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
			break;
		}
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("user has disconnected from server!");
		
		sessionVector.remove(session);
		
		// notify other users (if any) of disconnect
		try {
			for(Session s : sessionVector) {
				// send data back out to all clients
				if(exitGame) {
					s.getBasicRemote().sendText("Someone has left the fray!");
				} else {
					exitGame = true;
				}
			}
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	@OnError
	public void onError(Throwable error) {
		System.out.println("error");
	}
}
