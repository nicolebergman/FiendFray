package base;

import java.util.ArrayList;
import java.util.Scanner;

public class battle {
	private ArrayList<user> allUsers;
	private ArrayList<user> onlineUsers;
	private card[][] board;
	private boolean bUser2Turn;
	private int winnerIndex;
	private ArrayList< ArrayList<card> > allHandCombos; 
	private class coordinate
	{
		public int x; 
		public int y; 
		public coordinate()
		{
			x=0; 
			y=0; 
		}
		public coordinate(int x, int y)
		{
			this.x = x; 
			this.y = y; 
		}
	};
	public battle(user user1, user user2){
		allUsers = new ArrayList<user>();
		allUsers.add(user1); 
		allUsers.add(user2); 
		onlineUsers = new ArrayList<user>();
		board= new card[5][5];
		//the player who initiates the battle starts first
		bUser2Turn = false;
		allHandCombos = new ArrayList< ArrayList<card> >(); 
	}
	//Draws 2 cards at the satrt of eqch round automatically 
	public void drawCard(){
		//TO DO
		//Randomly Draw a card
		//if bUser2Turn = true return 1, else return 0
		int currentUserIndex = bUser2Turn ? 1 : 0; 
		allUsers.get(currentUserIndex).addCardToHand(new card());
		allUsers.get(currentUserIndex).addCardToHand(new card());
	}
	
	public card[][] getBoard(){
		return board;
	}
	//returns true if a card is placed
	public boolean placeCard(card userCard, coordinate coord){
		//TO DO
		//Add placing card log
		if(board[coord.x][coord.y] != null)
		{
			board[x][y] = userCard; 
			return true; 
		}
		return false; 
		
	}
	
	public int calcDamage(ArrayList<card> hand, user user1){
		return user1.playHand(hand);
	}
	
	public void gameLoop(){
		//TO DO
		//as long as both pets have current HP > 0, keep looping
		while(!hasGameEnded())
		{
			drawCard();
			int cardIndex = promptPlayerChooseCard();
			user currentUser = allUsers.get(getCurrentPlayerIndex());
			card cardToPlay = currentUser.getCardAtIndex(cardIndex);	
			coordinate coord = promptToPlaceCard();
			if(!placeCard(cardToPlay, coord))
			{
				System.out.println("Incorrect info given. Try again");
				continue;
			}
			
			//deal damage
		}
		
	}
	
	coordinate promptToPlaceCard()
	{
		Scanner scan = new Scanner(System.in); 
		int x = -1; 
		int y= -1; 
		while(true)
		{
			System.out.println("Please enter a x coordinate");
			String temp; 
			if(scan.hasNextLine())
			{
				temp = scan.nextLine();
			}
			try
			{
				x = Integer.parseInt(temp); 
			}
			catch(Exception e)
			{
				System.out.println("Enter a number");
				continue; 
			}
			if(x < 0 || x > 5)
			{
				System.out.println("x coordinate out of range");
				continue; 
			}
			break; 
		}
		
		while(true)
		{
			System.out.println("Please enter a y coordinate");
			String temp; 
			if(scan.hasNextLine())
			{
				temp = scan.nextLine();
			}
			try
			{
				y = Integer.parseInt(temp); 
			}
			catch(Exception e)
			{
				System.out.println("Enter a number");
				continue; 
			}
			if(y < 0 || y > 5)
			{
				System.out.println("x coordinate out of range");
				continue; 
			}
			break; 
		}
		
		return new coordinate(x,y); 
	}
	
	int promptPlayerChooseCard()
	{
		Scanner scan = new Scanner(System.in);
		int cardIndex = -1; 
		while(true)
		{
			System.out.println("Please enter the index of hte card you want to play");
			String temp = ""; 
			if(scan.hasNextLine())
			{
				temp = scan.nextLine(); 
			}
			try
			{
				cardIndex = Integer.parseInt(temp); 
			}
			catch(Exception e)
			{
				System.out.println("Please enter a number");
				continue; 
			}
			int currentUserHandSize= allUsers.get(getCurrentPlayerIndex()).getSizeOfHand(); 
			if(cardIndex < 0 || cardIndex > currentUserHandSize)
			{
				System.out.print("Enter a card index in range");
				continue; 
			}
			break; 
		}
		return cardIndex; 
	}
	int getCurrentPlayerIndex()
	{
		int currentPlayer = bUser2Turn ? 1 : 0; 
		return currentPlayer; 
	}
	//Checks to see if I have a 5 card combo
	void checkBoard()
	{
		checkHorizontal(); 
		checkVertical(); 
		checkDiagonal(); 
	}
	
	void checkHorizontal()
	{
		for(int i=0; i<5; ++i)
		{
			ArrayList currentHand = new ArrayList<card>(); 
			for(int j=0, count =0 ; count<5; ++count)
			{
				currentHand.add(board[i][j]); 
			}
			if(!currentHand.contains(null))
			{
				allHandCombos.add(currentHand);
			}
		}
	}
	
	void checkVertical()
	{
		for(int i=0, count=0; ; count<5; ++count)
		{
			ArrayList currentHand = new ArrayList<card>(); 
			for(int j=0; j<5; ++j)
			{
				currentHand.add(board[i][j]); 
			}
			if(!currentHand.contains(null))
			{
				allHandCombos.add(currentHand);
			}
		}
	}
	
	void checkLeftDiagonal()
	{
		for(int i=0; i<5; ++i)
		{
			ArrayList currentHand = new ArrayList<card>(); 
			for(int j=0; j<5; ++j)
			{
				if(i == j)
				{
					currentHand.add(board[i][j]); 
				}
			}
			if(!currentHand.contains(null))
			{
				allHandCombos.add(currentHand);
			}
		}
	}
	
	void checkRightDiagonal()
	{
		for(int i=4; i>-1; --i)
		{
			ArrayList currentHand = new ArrayList<card>(); 
			for(int j=0; j<5; ++j)
			{
				if(i + j == 5)
				{
					currentHand.add(board[i][j]); 
				}
			}
			if(!currentHand.contains(null))
			{
				allHandCombos.add(currentHand);
			}
		}
	}
	//Check what the hand is. Pair, 2 pair, 3 of a kind etc
	void determineHand(ArrayList<card> hand )
	{
		
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
