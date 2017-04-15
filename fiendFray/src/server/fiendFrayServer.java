package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import base.battle;
import base.parser;
import base.user;
import server.MySQLDriver;

@ServerEndpoint(value="/fiendFrayServer")
public class fiendFrayServer {
	// only once instance through all instances of this class
	private static Vector<Session> sessionVector = new Vector<Session>();
	private static Map<String, Session> usernameToSession = new HashMap<String, Session>();
	private static Map<Integer, battle> idToBattle = new HashMap<Integer, battle>();
	private static int battleIdCounter = 0;
		
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
		case "BattleRequest":
			// send battle request to client
			String toBattle = commands[1];
			String wantsToBattle = commands[2];
			try {
				for(String userKey : usernameToSession.keySet()) {
					if(userKey.equals(toBattle)) {
						Session s = usernameToSession.get(userKey);
						s.getBasicRemote().sendText("BattleRequest~" + wantsToBattle + " wants to battle!");
					}
				}
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
			break;
		case "StartGame":
			// send battle request to client
			String battleIdStr = commands[1];
			int battleIdInt = Integer.parseInt(battleIdStr);
			//try {
				for(int userKey : idToBattle.keySet()) {
					if(userKey == battleIdInt) {
						idToBattle.get(userKey).incrementRecognitionCount();
//						Session s = usernameToSession.get(userKey);
//						s.getBasicRemote().sendText("BattleRequest~" + wantsToBattle + " wants to battle!");
					}
				}
//			} catch(IOException ioe) {
//				System.out.println("ioe: " + ioe.getMessage());
//			}
			break;
		case "AcceptBattle":
			// identify players who battle
			String firstPlayer = commands[1];
			String secondPlayer = commands[2];
			
			//connect to database
			MySQLDriver msql = new MySQLDriver();
			msql.connect();
			parser newParser = msql.parseDB();
			List<user> allUsers = newParser.getAllUsers();
			
			// find users to add
			user firstUser = new user();
			user secondUser = new user();
			for(user x : allUsers) {
				if(x.getUsername().equals(firstPlayer)) {
					firstUser = x;
				}
				
				if(x.getUsername().equals(secondPlayer)) {
					secondUser = x;
				}
			}
			
			// create battle
			int battleId = battleIdCounter;
			battleIdCounter++;
			firstUser.battleId = battleId;
			secondUser.battleId = battleId;
			battle newBattle = new battle(firstUser, secondUser, this);
			idToBattle.put(battleId, newBattle);
			
			// tell clients to go to battle page
			try {
				for(String userKey : usernameToSession.keySet()) {
					if(userKey.equals(firstPlayer) || userKey.equals(secondPlayer)) {
						Session s = usernameToSession.get(userKey);
						s.getBasicRemote().sendText("GoBattle~" + Integer.toString(battleId));
					}
				}
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
			
			break;
		default:
			System.out.print("fuck me");
			break;
		}
		
		System.out.println(usernameToSession.toString());
	}
	
	public void sendMessage(String message, String firstPlayer, String secondPlayer) {
		// tell clients to go to battle page
		try {
			for(String userKey : usernameToSession.keySet()) {
				if(userKey.equals(firstPlayer) || userKey.equals(secondPlayer)) {
					Session s = usernameToSession.get(userKey);
					s.getBasicRemote().sendText("UpdateBoard" + message);
					System.out.println(message);
				}
			}
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
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
