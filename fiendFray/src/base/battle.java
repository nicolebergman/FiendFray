package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class battle {
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
	
	private enum pokerHand
	{
		PAIR,
		TWOPAIR,
		THREEOFAKIND,
		FOUROFAKIND,
		FIVEOFAKIND,
		STRAIGHT
	};
	
	private ArrayList<user> allUsers;
	private ArrayList<user> onlineUsers;
	private card[][] board;
	private boolean bUser2Turn;
	private int winnerIndex;
	private ArrayList< ArrayList<card> > allHandCombos; 
	private ArrayList<pokerHand> madePokerHands; 
	public battle(user user1, user user2){
		allUsers = new ArrayList<user>();
		allUsers.add(user1); 
		allUsers.add(user2); 
		onlineUsers = new ArrayList<user>();
		board= new card[5][5];
		initialiseBoard(); 
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
	void initialiseBoard()
	{
		for(int i=0; i< 5; ++i)
		{
			for(int j=0; j<5; ++j)
			{
				board[i][j] = null; 
			}
		}
		
		for(int i=1; i<3; ++i)
		{
			for(j=1; j<3; ++j)
			{
				board[i][j] = new card(); 
			}
		}
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
			//whole chunk below is just asking player to place 2 cards somewhere
			int cardIndex1= promptPlayerChooseCard();
			user currentUser = allUsers.get(getCurrentPlayerIndex());
			card cardToPlay1 = currentUser.getCardAtIndex(cardIndex1);	
			coordinate coord1 = promptToPlaceCard();
			if(!placeCard(cardToPlay1, coord1))
			{
				System.out.println("Incorrect info given. Try again");
				continue;
			}
			int cardIndex2= promptPlayerChooseCard();
			card cardToPlay2 = currentUser.getCardAtIndex(cardIndex2);	
			coordinate coord2 = promptToPlaceCard();
			if(!placeCard(cardToPlay2, coord2))
			{
				System.out.println("Incorrect info given. Try again");
				continue;
			}
			//checks if the placed card creates any hands
			checkBoard(coord1, coord2); 
			determineHand(); 
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
	void checkBoard(coordinate coord1, coordinate coord2)
	{
		//ust checks if the placed cards were in the corners
		checkHorizontal(coord1); 
		checkHorizontal(coord2); 
		checkVertical(coord1); 
		checkVertical(coord2); 
		checkLeftDiagonal(); 
		checkRightDiagonal(); 
	}
	
	void checkHorizontal(coordinate coord)
	{
		int y = coord.y;
		ArrayList hand = new ArrayList<card>(); 
		for(int i=0; i<5; ++i)
		{
			hand.add(board[i][y]); 
		}
		if(!hand.contains(null))
		{
			allHandCombos.add(hand); 
		}
	}
	
	void checkVertical(coordinate coord)
	{
		ArrayList hand = new ArrayList<card> (); 
		int x = coord.x; 
		for(int i =0; i<5; ++i)
		{
			hand.add(board[x][i]);
		}
		if(!hand.contains(null))
		{
			allHandCombos.add(hand); 
		}
	}
	void checkLeftDiagonal()
	{
		ArrayList hand = new ArrayList<card> ();
		for(int i=0; i<5; ++i)
		{
			for(int j=0; j<5; ++j)
			{
				if(i ==j)
				{
					hand.add(board[i][j]);
				}
			}
		}
		if(!hand.contains(null))
		{
			allHandCombos.add(hand);
		}
	}
	
	void checkRightDiagonal()
	{
		ArrayList hand = new ArrayList<card> (); 
		int i = 4; 
		int j = 0; 
		for(int count=0; count<4; count++)
		{
			hand.add(board[i][j]);
			i--; 
			j++; 
		}
		if(!hand.contains(null))
		{
			allHandCombos.add(hand);
		}
	}
	//Check what the hand is. Pair, 2 pair, 3 of a kind etc
	void determineHand()
	{
		if(allHandCombos.isEmpty())
		{
			return; 
		}
		for(ArrayList<card> hand : allHandCombos)
		{
			checkHandIsStraight(hand); 
			checkHandForSameValue(hand); 
		}
		
	}
	void checkHandIsStraight(ArrayList<card> hand)
	{
		Collections.sort(hand);
		int prevValue = hand.get(0).getValue(); 
		for(int i=1; i<5; ++i)
		{
			if(hand.get(i).getValue() - prevValue != 1)
			{
				return; 
			}
		}
		madePokerHands.add(pokerHand.STRAIGHT);  
	}
	
	//checks for pair 3 of a kind etc 
	void checkHandForSameValue(ArrayList<card> hand)
	{
		int[] valueArray = new int[13];
		//initialise the count of each value to 0 
		for(int i=0; i<13; ++i)
		{
			valueArray[i] = 0; 
		}
		//increment the index
		for(card card : hand)
		{
			valueArray[card.getValue()] += 1; 
		}
		int pairCount =0; 
		for(int i=0; i<13; ++i)
		{
			switch(valueArray[i])
			{
			case 2:
			{
				pairCount++;
				break; 
			}
			case 3:
			{
				madePokerHands.add(pokerHand.THREEOFAKIND);
				break; 
			}
			case 4:
			{
				madePokerHands.add(pokerHand.FOUROFAKIND);
				break; 
			}
			case 5: 
			{
				madePokerHands.add(pokerHand.FIVEOFAKIND);
			}
			default:
			{
				break; 
			}
			}
		}
		if(pairCount == 1)
		{
			madePokerHands.add(pokerHand.PAIR);
		}
		else if(pairCount==2)
		{
			madePokerHands.add(pokerHand.TWOPAIR);
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
