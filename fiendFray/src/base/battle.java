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
		NOTHING,
		PAIR,
		TWOPAIR,
		THREEOFAKIND,
		FOUROFAKIND,
		FIVEOFAKIND,
		STRAIGHT, 
		FULLHOUSE
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
		madePokerHands = new ArrayList<pokerHand>(); 
		initialiseBoard(); 
		gameLoop(); 
	}
	public static void main(String[] args)
	{
		user user1 = new user(); 
		user user2 = new user(); 
		new battle(user1, user2); 
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
		
		for(int i=1; i<4; ++i)
		{
			for(int j=1; j<4; ++j)
			{
				board[i][j] = new card(); 
			}
		}
	}
	
	public card[][] getBoard(){
		return board;
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
	
	//returns true if a card is placed
	public boolean placeCard(int cardIndex, coordinate coord){
		//TO DO
		//Add placing card log
		user currentUser = allUsers.get(getCurrentPlayerIndex());
		System.out.println("X value: " + coord.x);
		System.out.println("Y value: "+ coord.y);
		System.out.println("Board value: "+board[coord.x][coord.y]);
		
		if(board[coord.x][coord.y] == null)
		{
			board[coord.x][coord.y] = currentUser.getCardAtIndex(cardIndex);
			return true; 
		}
		return false; 
	}
	/** DEBUG
	public int calcDamage(ArrayList<card> hand, user user1){
		return user1.playHand(hand);
	}
	**/
	public void gameLoop(){
		//TO DO
		//as long as both pets have current HP > 0, keep looping
		while(true)
		{
			drawCard();
			printBoard(); 
			//whole chunk below is just asking player to place 2 cards somewhere
			int cardIndex1= promptPlayerChooseCard();
			user currentUser = allUsers.get(getCurrentPlayerIndex());
			card cardToPlay1 = currentUser.getCardAtIndex(cardIndex1);	
			coordinate coord1 = promptToPlaceCard();
			if(!placeCard(cardIndex1, coord1))
			{
				System.out.println("Incorrect info given. Try again");
				continue;
			}
			user user = allUsers.get(this.getCurrentPlayerIndex());
			user.removeCardAtIndex(cardIndex1);
			
			printBoard(); 
			
			int cardIndex2= promptPlayerChooseCard();
			card cardToPlay2 = currentUser.getCardAtIndex(cardIndex2);	
			coordinate coord2 = promptToPlaceCard();
			if(!placeCard(cardIndex2, coord2))
			{
				System.out.println("Incorrect info given. Try again");
				continue;
			}
			
			user.removeCardAtIndex(cardIndex2);
			if(isBoardFull())
			{
				initialiseBoard();
			}
			//checks if the placed card creates any hands
			checkBoard(coord1, coord2); 
			determineHand(); 
			dealDamage();
			if(isBoardFull())
			{
				initialiseBoard();
			}
			
			if(!hasGameEnded())
			{
				endTurn(); 
			}
			else
			{
				break; 
			}
		}
		endGame(); 
		
	}
	
	coordinate promptToPlaceCard()
	{
		Scanner scan = new Scanner(System.in); 
		int x = -1; 
		int y= -1; 
		while(true)
		{
			System.out.println("Please enter a x coordinate");
			String temp = ""; 
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
			String temp = ""; 
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
		//all if statements are used to prevent double counting the same row/column/diagonal
		checkHorizontal(coord1); 
		if(coord1.y != coord2.y)
		{
			checkHorizontal(coord2); 
		}
		checkVertical(coord1); 
		if(coord1.x != coord2.x)
		{
			checkVertical(coord2); 
		}
		checkLeftDiagonal(coord1);
		if(coord1.x + coord2.x != 4)
		{
			checkLeftDiagonal(coord2);
		}
		
		checkRightDiagonal(coord1);
		if(coord1.x + coord2.x != 4)
		{
			checkRightDiagonal(coord2); 
		}
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
	void checkLeftDiagonal(coordinate coord)
	{
		boolean bTopLeft = coord.x == 0 && coord.y == 0; 
		boolean bBottomRight = coord.x == 4 && coord.y == 4; 
		boolean bShouldCheck = bTopLeft || bBottomRight; 
		if(!bShouldCheck)
		{
			return; 
		}
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
	
	void checkRightDiagonal(coordinate coord)
	{
		//only check right diag if it's placed in these 2 positions
		boolean bTopRight = coord.x == 0 && coord.y == 4; 
		boolean bBottomLeft = coord.x == 4 && coord.y == 0; 
		boolean bShouldCheck = bTopRight || bBottomLeft; 
		if(!bShouldCheck)
		{
			return; 
		}
		ArrayList hand = new ArrayList<card> (); 
		int i = 4; 
		int j = 0; 
		for(int count=0; count<5; count++)
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
			
			boolean b1 = checkHandIsStraight(hand); 
			boolean b2 = checkHandForSameValue(hand);
			//if both these are false. There is no valid hand. Add nothing to the madePokerHand array
			if(!b1 && !b2)
			{
				madePokerHands.add(pokerHand.NOTHING);
			}
		}
		
	}
	boolean checkHandIsStraight(ArrayList<card> hand)
	{
		Collections.sort(hand);
		System.out.print("Sorted Hand: ");
		for(int i=0; i<hand.size(); ++i)
		{
			System.out.print(hand.get(i).getValue() + " ");
		}
		System.out.println("");
		int prevValue = hand.get(0).getValue(); 
		for(int i=1; i<5; ++i)
		{
			if(hand.get(i).getValue() - prevValue != 1)
			{
				return false; 
			}
			prevValue = hand.get(i).getValue(); 
		}
		madePokerHands.add(pokerHand.STRAIGHT);
		return true;
	}
	
	//checks for pair 3 of a kind etc 
	boolean checkHandForSameValue(ArrayList<card> hand)
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
		int threeOfAKindCount = 0; 
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
				threeOfAKindCount++;;
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
		if(pairCount == 1 && threeOfAKindCount == 0)
		{
			madePokerHands.add(pokerHand.PAIR);
		}
		else if(pairCount == 1 && threeOfAKindCount ==1)
		{
			madePokerHands.add(pokerHand.FULLHOUSE);
		}
		else if(pairCount==2)
		{
			madePokerHands.add(pokerHand.TWOPAIR);
		}
		if(madePokerHands.size() == 0)
		{
			return false; 
		}
		return true;
	}
	
	void dealDamage()
	{
		if(madePokerHands.isEmpty())
		{
			return; 
		}
		user currentUser = allUsers.get(getCurrentPlayerIndex()); 
		int damage =0; 
		for(pokerHand hand : madePokerHands)
		{
			String handName = convertPokerHandToString(hand); 
			damage += currentUser.getUserPet().calculateDamage(handName);
		}
		//get the opponent's index by flipping whatever the current layer's index is
		int opponentIndex = getCurrentPlayerIndex();
		if(opponentIndex == 0)
		{
			opponentIndex = 1; 
		}
		else
		{
			opponentIndex = 0; 
		}
		user opponent = allUsers.get(opponentIndex); 
		opponent.takeDamage(damage);
	}
	
	String convertPokerHandToString(pokerHand hand)
	{
		switch(hand)
		{
	
		case NOTHING:
			return "nothing";
		case PAIR:
			return "onePair";
		case TWOPAIR:
			return "twoPair";
		case THREEOFAKIND:
			return "threeKind";
		case STRAIGHT:
			return "straight";
		case FOUROFAKIND:
			return "fourKind";
		case FIVEOFAKIND:
			return "fiveKind";
		case FULLHOUSE:
			return "fullHouse";
		default:
			return "";
		}
	
			
	}
	boolean isBoardFull()
	{
		for(int i=0; i<5; ++i)
		{
			for(int j=0; j<5; ++j)
			{
				if(board[i][j] == null)
				{
					return false; 
				}
			}
		}
		return true; 
	}
	//switch the turn of the players
	void endTurn()
	{
		bUser2Turn = !bUser2Turn;
		madePokerHands.clear();
		allHandCombos.clear();
		
	}
	//returns true if only 1 pet has HP left 
	boolean hasGameEnded()
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
		System.out.println("Game Ended.");
		
	}
	
	//DEBUG
	void printBoard()
	{
		
		for(int i=0; i<5; ++i)
		{
			for(int j=0; j<5; ++j)
			{
				card cardValue = board[i][j]; 
				if(cardValue != null)
				{
					int print = cardValue.getValue(); 
					String toPrint = "";
					if(print < 10)
					{
						toPrint = "0"+ Integer.toString(print);
					}
					else
					{
						toPrint = Integer.toString(print);
					}
					System.out.print(toPrint + " ");
				}
				else
				{
					System.out.print("xx ");
				}
			}
			System.out.println("");

		}
		
		int index = this.getCurrentPlayerIndex();
		user currentUser = allUsers.get(index); 
		ArrayList<card> userCurrentHand = currentUser.getCurrentHand(); 
		for(card card : userCurrentHand)
		{
			int print = card.getValue(); 
			String toPrint = "";
			if(print < 10)
			{
				toPrint = "0"+ Integer.toString(print);
			}
			else
			{
				toPrint = Integer.toString(print);
			}
			System.out.print(toPrint + " ");
		}
		System.out.println("");
		int opponentIndex = (this.getCurrentPlayerIndex() == 0) ? 1 : 0; 
		user opponent = allUsers.get(opponentIndex);
		
		int maxHP = currentUser.getUserPet().getMaxHP(); 
		int currentHP = currentUser.getUserPet().getCurrentHP();
		int maxHPOpponent = opponent.getUserPet().getMaxHP(); 
		int currentHPOpponent = opponent.getUserPet().getCurrentHP();	
		System.out.println("Current User HP: " + currentHP + "/" + maxHP);
		System.out.println("Opponent User HP: " + currentHPOpponent + "/" + maxHPOpponent);

	}
}
