package base;

import java.util.ArrayList;

public class battle {
	private ArrayList<user> allUsers;
	private ArrayList<user> onlineUsers;
	private card[][] board;
	
	public battle(user user1, user user2){
		allUsers = new ArrayList<user>();
		onlineUsers = new ArrayList<user>();
		board= new card[5][5];
	}
	
	public card drawCard(){
		//TO DO
		//Randomly Draw a card
		return new card();
	}
	
	public card[][] getBoard(){
		return board;
	}
	
	public void placeCard(card userCard){
		//TO DO
		//Add placing card log
	}
	
	public int calcDamage(ArrayList<card> hand, user user1){
		return user1.playHand(hand);
	}
	
	public boolean gameLoop(){
		//TO DO
		//Add gameLoop logic
		return true;
	}
	
	public void endGame(){
		//TO DO
		//Add EndGame stuff
	}
}
