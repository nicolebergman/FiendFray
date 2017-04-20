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
//			try {
//				for(String userKey : usernameToSession.keySet()) {
//					// send data back out to all clients
//					Session s = usernameToSession.get(userKey);
//					if(s != session) {
//						s.getBasicRemote().sendText(commands[1] + " has left the fray!");
//					}
//				}
//			} catch(IOException ioe) {
//				System.out.println("ioe: " + ioe.getMessage());
//			}
			
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
		case "UpdateActiveUsers":
			// tell clients on home page to update their active user's list
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
		case "ProcessInputs":
			// send battle request to client
			String bIdStr = commands[1];
			int bId = Integer.parseInt(bIdStr);
			
			int card1Ind = -1;
			int card1X;
			int card1Y;
			int card2Ind = -1;
			int card2X;
			int card2Y;
			
			if(commands[2].equals("yourCard1")) {
				card1Ind = 0;
			} else if (commands[2].equals("yourCard2")) {
				card1Ind = 1;
			} else if (commands[2].equals("yourCard3")) {
				card1Ind = 2;
			} else if (commands[2].equals("yourCard4")) {
				card1Ind = 3;
			}
			
			card1X = Integer.parseInt(Character.toString(commands[3].charAt(0)));
			card1Y = Integer.parseInt(Character.toString(commands[3].charAt(1)));
			
			if(commands[4].equals("yourCard1")) {
				card2Ind = 0;
			} else if (commands[4].equals("yourCard2")) {
				card2Ind = 1;
			} else if (commands[4].equals("yourCard3")) {
				card2Ind = 2;
			} else if (commands[4].equals("yourCard4")) {
				card2Ind = 3;
			}
			
			card2X = Integer.parseInt(Character.toString(commands[5].charAt(0)));
			card2Y = Integer.parseInt(Character.toString(commands[5].charAt(1)));
			
			System.out.println(card1Ind);
			System.out.println(card1X);
			System.out.println(card1Y);
			System.out.println(card2Ind);
			System.out.println(card2X);
			System.out.println(card2Y);
			
			idToBattle.get(bId).receiveInputs(card1Ind, card1X, card1Y, card2Ind, card2X, card2Y);
			
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
					if(userKey.equals(firstPlayer)) {
						Session s = usernameToSession.get(userKey);
						s.getBasicRemote().sendText("GoBattle~" + Integer.toString(battleId) + "~1");
					}
					
					if(userKey.equals(secondPlayer)) {
						Session s = usernameToSession.get(userKey);
						s.getBasicRemote().sendText("GoBattle~" + Integer.toString(battleId) + "~2");
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
					System.out.println(message);
					s.getBasicRemote().sendText("UpdateBoard" + message);
				}
			}
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void sendGameOver(String message, String firstPlayer, String secondPlayer) {
		// tell clients to go to battle page
		String winnerStr = message.split("~")[1];
		int winner = Integer.parseInt(winnerStr);
		String winMsg = "";
		
		if(winner == 1) {
			winMsg = firstPlayer + " won a battle against " + secondPlayer + "!";
		} else {
			winMsg = secondPlayer + " won a battle against " + firstPlayer + "!";
		}
		
		try {
			for(String userKey : usernameToSession.keySet()) {
				Session s = usernameToSession.get(userKey);
				
				if(userKey.equals(firstPlayer) || userKey.equals(secondPlayer)) {
					System.out.println(message);
					s.getBasicRemote().sendText(message);
				} else {
					s.getBasicRemote().sendText(winMsg);
				}
			}
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	@OnClose
	public void close(Session session) throws IOException {
		//System.out.println("user has disconnected from server!");
		
		// handle client close
		for(String userKey : usernameToSession.keySet()) {
			if(usernameToSession.get(userKey) == session) {
				usernameToSession.remove(userKey);
				
				for(String key : usernameToSession.keySet()) {
					// send data back out to all clients
					Session s = usernameToSession.get(key);
					//s.getBasicRemote().sendText(userKey + " has left the fray!");
				}
			}
		}
		
		//sessionVector.remove(session);
	}
	
	@OnError
	public void onError(Throwable error) {
		error.printStackTrace();
		System.out.println("error");
	}
}
