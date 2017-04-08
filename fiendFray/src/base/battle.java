package base;

import java.util.ArrayList;

public class battle {
	private ArrayList<user> allUsers;
	private ArrayList<user> onlineUsers;
	private card[][] board;
	private boolean bUser2Turn;
	private int winnerIndex; 
	public battle(user user1, user user2){
		allUsers = new ArrayList<user>();
		allUsers.add(user1); 
		allUsers.add(user2); 
		onlineUsers = new ArrayList<user>();
		board= new card[5][5];
		//the player who initiates the battle starts first
		bUser2Turn = false; 
	}
	
	public void drawCard(){
		//TO DO
		//Randomly Draw a card
		//if bUser2Turn = true return 1, else return 0
		int currentUserIndex = bUser2Turn ? 1 : 0; 
		allUsers.get(currentUserIndex).addCardToHand(new card());
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
	
	public void gameLoop(){
		//TO DO
		//as long as both pets have current HP > 0, keep looping
		while(!hasGameEnded())
		{
			//draw card
			//place hand 
			//deal damage
		}
		
	}
	
	//returns true if only 1 pet has HP left 
	public boolean hasGameEnded()
	{
		for(int i=0; i<allUsers.size(); ++i)
		{
			if(allUsers.get(i).getUserPet().getCurrentHP() < 0)
			{
				//if i = 0, allUsers[1] is the winner, else allUsers[0] is the winner
				winnerIndex = (i==0) ? 1 : 0; 
				return true; 
			}
		}
		return false; 
	}
	public void endGame(){
		//TO DO
		//Add EndGame stuff
	}
}
