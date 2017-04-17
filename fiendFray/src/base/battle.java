package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import server.fiendFrayServer;

public class battle extends Thread{
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
		FULLHOUSE, 
		FLUSH, 
		STRAIGHTFLUSH, 
		ROYALFLUSH
	};
	
	private ArrayList<user> allUsers;
	private ArrayList<user> onlineUsers;
	private card[][] board;
	private boolean bUser2Turn;
	private int winnerIndex;
	private ArrayList< ArrayList<card> > allHandCombos; 
	private ArrayList<pokerHand> madePokerHands; 
	private Lock mLock;
	// count how many times players have sent 'StartGame' message
	private int recognitionCount;
	private fiendFrayServer server;
	private boolean bProcessCardInputs; 
	private List<coordinate> placedCardCoordinates; 
	private List<card> placedCards; 
	private List<Integer> removeCardIndices; 
	
	public battle(user user1, user user2, fiendFrayServer ffs){
		allUsers = new ArrayList<user>();
		allUsers.add(user1); 
		allUsers.add(user2); 
		onlineUsers = new ArrayList<user>();
		board= new card[5][5];
		//the player accepts the battle starts first
		bUser2Turn = true;
		allHandCombos = new ArrayList< ArrayList<card> >();
		madePokerHands = new ArrayList<pokerHand>(); 
		mLock = new ReentrantLock();
		recognitionCount = 0;
		server = ffs;
		this.start();
		//initialiseBoard(); 
		//gameLoop();
		bProcessCardInputs = false; 
		placedCardCoordinates = new ArrayList<coordinate>(); 
		placedCards = new ArrayList<card>(); 
		removeCardIndices = new ArrayList<Integer>(); 
		
	}
	
//	public static void main(String[] args)
//	{
//		user user1 = new user(); 
//		user user2 = new user(); 
//		new battle(user1, user2); 
//	}
	
	public void notifyServer(String message) {
		// notify server to send message
        System.out.println(message);
		server.sendMessage(message, allUsers.get(0).getUsername(), allUsers.get(1).getUsername());
	}
	
	public void notifyGameOver(String message) {
		System.out.println(message);
		server.sendGameOver(message, allUsers.get(0).getUsername(), allUsers.get(1).getUsername());
	}
	
	void joinBattle(user user)
	{
		allUsers.add(user); 
	}
	
	public void incrementRecognitionCount() {
		mLock.lock();
		try {
			recognitionCount++;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(recognitionCount);
			mLock.unlock();
		}
	}
	
	public void initialiseBoard()
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
		
		notifyServer(serverNotification());
	}
	
	public card[][] getBoard()
	{
		return board;
	}
	//Draws 2 cards at the start of eqch round automatically 
	public void drawCard()
	{
		//TO DO
		//Randomly Draw a card
		//if bUser2Turn = true return 1, else return 0
		int currentUserIndex = bUser2Turn ? 1 : 0; 
		allUsers.get(currentUserIndex).addCardToHand(new card());
		allUsers.get(currentUserIndex).addCardToHand(new card());
	}
	
	//returns true if a card is placed
	public boolean placeCard(int cardIndex, coordinate coord)
	{
		//TO DO
		//Add placing card log
		if( coord.x < 0 || coord.x > 4 ||
			coord.y < 0 || coord.y > 4)
		{
			return false; 
		}
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
	
	public void allowProcessInput()
	{
		this.bProcessCardInputs = true; 
	}
	public void gameLoop()
	{
		//TO DO
		//as long as both pets have current HP > 0, keep looping
		while(true)
		{
			//drawCard();
			/* All for console puposes
			printBoard(); 
			//whole chunk below is just asking player to place 2 cards somewhere
			int cardIndex1; 
			card cardToPlay1; 
			coordinate coord1;
			user currentUser; 
			while(true)
			{
				currentUser = allUsers.get(getCurrentPlayerIndex());
				cardIndex1= promptPlayerChooseCard();
				if(cardIndex1 < 0 || cardIndex1 > currentUser.getSizeOfHand()-1)
				{
					System.out.println("Invalid card index");
					printBoard(); 
					continue; 
				}
				cardToPlay1 = currentUser.getCardAtIndex(cardIndex1);	
				coord1 = promptToPlaceCard();
				if(!placeCard(cardIndex1, coord1))
				{
					System.out.println("Incorrect info given. Try again");
					printBoard(); 
					continue;
				}
				else 
				{
					break; 
				}
			}
			user user = allUsers.get(this.getCurrentPlayerIndex());
			user.removeCardAtIndex(cardIndex1);
			
			printBoard(); 
			
			
			int cardIndex2; 
			card cardToPlay2; 
			coordinate coord2; 
			while(true)
			{
				cardIndex2= promptPlayerChooseCard();
				if(cardIndex2 < 0 || cardIndex1 > currentUser.getSizeOfHand()-1)
				{
					System.out.println("Invalid card index");
					printBoard(); 
					continue; 
				}
				cardToPlay2 = currentUser.getCardAtIndex(cardIndex2);	
				coord2 = promptToPlaceCard();
				if(!placeCard(cardIndex2, coord2))
				{
					System.out.println("Incorrect info given. Try again");
					printBoard(); 
					continue;
				}
				else
				{
					break; 
				}
				
			}
			user.removeCardAtIndex(cardIndex2);
			*/
			while(!this.bProcessCardInputs)
			{}
			System.out.println("fuck me");
			this.bProcessCardInputs = false; 
			this.placeCard(this.removeCardIndices.get(0), this.placedCardCoordinates.get(0));
			this.placeCard(this.removeCardIndices.get(1), this.placedCardCoordinates.get(1));

			user currentUser = allUsers.get(this.getCurrentPlayerIndex()); 
			currentUser.removeCardAtIndex(this.removeCardIndices.get(0));
			currentUser.removeCardAtIndex(this.removeCardIndices.get(1));

			if(isBoardFull())
			{
				initialiseBoard();
			}
			if(isBoardFull())
			{
				initialiseBoard();
			}
			//checks if the placed card creates any hands
			//checkBoard(coord1, coord2); 
			checkBoard(this.placedCardCoordinates.get(0), this.placedCardCoordinates.get(1));
			determineHand(); 
			dealDamage();
			System.out.println(serverNotification());
			drawCard();
			// notify clients of interface updates
			notifyServer(serverNotification());
			clearInputs();
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
	
	void runOneGameLoop() {
		this.placeCard(this.removeCardIndices.get(0), this.placedCardCoordinates.get(0));
		this.placeCard(this.removeCardIndices.get(1), this.placedCardCoordinates.get(1));
		
		printBoard();

		user currentUser = allUsers.get(this.getCurrentPlayerIndex());
		
		// remove bigger index first
		if(this.removeCardIndices.get(0) > this.removeCardIndices.get(1)) {
			currentUser.removeCardAtIndex(this.removeCardIndices.get(0));
			currentUser.removeCardAtIndex(this.removeCardIndices.get(1));
		} else {
			currentUser.removeCardAtIndex(this.removeCardIndices.get(1));
			currentUser.removeCardAtIndex(this.removeCardIndices.get(0));
		}
		
//		currentUser.removeCardAtIndex(this.removeCardIndices.get(0));
//		currentUser.removeCardAtIndex(this.removeCardIndices.get(1) - 1);


		

		//checks if the placed card creates any hands
		//checkBoard(coord1, coord2); 
		checkBoard(this.placedCardCoordinates.get(0), this.placedCardCoordinates.get(1));
		determineHand(); 
		dealDamage();
		//System.out.println(serverNotification());
		drawCard();
		if(isBoardFull())
		{
			initialiseBoard();
		}
		clearInputs();
		if(!hasGameEnded())
		{
			endTurn(); 
			
			// notify clients of interface updates
			notifyServer(serverNotification());

		}
		else
		{
			// notify clients of interface updates
			notifyServer(serverNotification());
			
			// notify game over
			if(bUser2Turn) {
				notifyGameOver("GameOver~2");
			} else {
				notifyGameOver("GameOver~1");
			}
			
			endGame();
		}
	}
	
	public void receiveInputs(int card1Ind, int card1X, int card1Y, int card2Ind, int card2X, int card2Y) {
		coordinate coord1 = new coordinate(card1X, card1Y);
		coordinate coord2 = new coordinate(card2X, card2Y);
		
		this.placedCardCoordinates.add(coord1);
		this.placedCardCoordinates.add(coord2);
		
		this.removeCardIndices.add(card1Ind);
		this.removeCardIndices.add(card2Ind);
		
		//this.bProcessCardInputs = true;
		
		runOneGameLoop();
	}
	
	void clearInputs() {
		this.placedCardCoordinates.clear();
		this.removeCardIndices.clear();
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
			if(b1 == false)
			{
				checkHandIsFlush(hand, false); 
			}
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
		//checks for striaght flush and royal flush in event of striaght\
		
		if(checkHandIsFlush(hand, true))
		{
			if(!checkHandIsRoyalFlush(hand))
			{
				madePokerHands.add(pokerHand.STRAIGHTFLUSH);
			}
		}
		else
		{
			madePokerHands.add(pokerHand.STRAIGHT);
		}
		
		//madePokerHands.add(pokerHand.STRAIGHT);
		return true;
	}
	
	boolean checkHandIsFlush(ArrayList<card> hand, boolean bCheckFromStraight)
	{
		System.out.print("Sorted Hand: ");
		for(int i=0; i<hand.size(); ++i)
		{
			System.out.print(hand.get(i).getValue() + " ");
		}
		System.out.println("");
		for(
				int i=1; i<5; ++i)
		{
			//if the suit of the current card is not the same as the suit of the 
			//first card 
			if(hand.get(i).getSuit() != hand.get(0).getSuit())
			{
				return false; 
			}
		}
		if(!bCheckFromStraight)
		{
			madePokerHands.add(pokerHand.FLUSH);
		}
		return true;
	}
	

	boolean checkHandIsRoyalFlush(ArrayList<card> hand)
	{
		
		//hand should already be sorted 
		//so just check if the last card is 14, value of an Ace
		if(hand.get(hand.size()-1).getValue() != 13)
		{
			return false; 
		}
		madePokerHands.add(pokerHand.ROYALFLUSH);
		return true;
	}
	
	public void removeCardFromPlayerHand(int cardInd) {
		user currentUser = allUsers.get(this.getCurrentPlayerIndex());
		currentUser.removeCardAtIndex(cardInd);
	}
	
	//checks for pair 3 of a kind etc 
	boolean checkHandForSameValue(ArrayList<card> hand)
	{
		//if it alrady contains a flush, ignore 
		boolean bContainsFlush = madePokerHands.contains(pokerHand.FLUSH);
		boolean bContainsStraightFlush = madePokerHands.contains(pokerHand.STRAIGHTFLUSH);
		boolean bContainsRoyalFlush = madePokerHands.contains(pokerHand.ROYALFLUSH);
		int[] valueArray = new int[15];
		boolean bHandMade = false; 
		//initialise the count of each value to 0 
		for(int i=0; i<15; ++i)
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
				if(!bContainsStraightFlush && !bContainsRoyalFlush)
				{
					madePokerHands.add(pokerHand.FOUROFAKIND);
					bHandMade = true; 
				}
				break; 
			}
				case 5: 
			{
				if(!bContainsStraightFlush && !bContainsRoyalFlush)
				{
					madePokerHands.add(pokerHand.FIVEOFAKIND);
					bHandMade = true; 
				}
				break; 
			}
			default:
			{
				break; 
			}
			}
		}
		System.out.println("pairCount: " + pairCount);
		System.out.println("threeOfAKindCount: " + threeOfAKindCount);
		if(pairCount == 1 && threeOfAKindCount == 0)
		{
			if(!bContainsFlush)
			{
				madePokerHands.add(pokerHand.PAIR);
				bHandMade = true; 
			}
		}
		else if(pairCount == 1 && threeOfAKindCount ==1)
		{
			if(bContainsFlush)
			{
				madePokerHands.add(pokerHand.FULLHOUSE);
				madePokerHands.remove(pokerHand.FLUSH);
				bHandMade = true; 
			}
		}
		else if(pairCount==2)
		{
			if(!bContainsFlush)
			{
				madePokerHands.add(pokerHand.TWOPAIR);
				bHandMade = true; 
			}
		} 
		else if(threeOfAKindCount == 1 && pairCount == 0) {
			if(!bContainsFlush)
			{
				madePokerHands.add(pokerHand.THREEOFAKIND);
				bHandMade = true; 
			}
		}
		if(!bHandMade)
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
		case FLUSH:
			return "flush"; 
		case STRAIGHTFLUSH:
			return "straightFlush";
		case ROYALFLUSH: 
			return "royalFlush"; 
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
			if(allUsers.get(i).getUserPet().getCurrentHP() <= 0)
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
	
	String serverNotification()
	{
		String s = "";  
		for(int i=0; i<5; ++i)
		{
			for(int j=0; j<5; ++j)
			{
				s += "~";
				if(board[i][j] == null)
				{
					s+="../images/nocard.png";
				}
				else
				{
					s+=board[i][j].getImgURL();
				}
			}
		}
		s += "~"; 
		s+=allUsers.get(0).getUserPet().getCurrentHP(); 
		s+="~"; 
		s+=allUsers.get(1).getUserPet().getCurrentHP();
		user user1 = allUsers.get(0); 
		for(card card : user1.getCurrentHand())
		{
			s +="~"; 
			s += card.getImgURL();
		}
		user user2 = allUsers.get(1); 
		for(card card : user2.getCurrentHand())
		{
			s +="~"; 
			s += card.getImgURL();
		}
		if(bUser2Turn) {
			s += "~2";
		} else {
			s += "~1";
		}
		//Send current number of hearts to be parsed 
		for(int i=0; i<allUsers.size(); ++i)
		{
			user user = allUsers.get(i); 
			int currentHP = user.getUserPet().getCurrentHP(); 
			int maxHP = user.getUserPet().getMaxHP(); 
			double fractionHP = (double) currentHP / (double) maxHP;
			fractionHP *= 10; 
			int heartsToReturn = (int) fractionHP;
			s = s + "~" + Integer.toString(heartsToReturn); 
		}
		return s;
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
					toPrint = cardValue.getCardInfo(); 
					System.out.print(toPrint + " ");
				}
				else
				{
					System.out.print("xxx ");
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
			toPrint = card.getCardInfo(); 
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
	
	public void run() {
		// detect whether to start game state machine
		System.out.println("dicknipples");
		while(true) {
			System.out.println("recog count" + recognitionCount);
			if(recognitionCount == 2) {
				System.out.println("2 player starts received");
				user user1 = allUsers.get(0); 
				user user2 = allUsers.get(1); 
				for(int i=0; i<4; ++i)
				{
					user1.getCurrentHand().add(new card()); 
					user2.getCurrentHand().add(new card());
				}
				this.initialiseBoard();
				break;
				//this.gameLoop();
			}
		}
	}
}
