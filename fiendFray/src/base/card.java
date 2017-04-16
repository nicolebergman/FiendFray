package base;

import java.util.Random;

public class card implements Comparable<card>{
	
	private String info;
	private int value;
	private int suit; 
	private String imgURL; 
	
	card() {
		Random rand = new Random(); 
		value = rand.nextInt(5) + 1;	// max is 13 for right now
		suit = rand.nextInt(4) + 1; // 1 Diamond, 2Club, 3 Hearts, 4 Spades
		//appends the card's suit to the info 
		String s = ""; 
		//appends the correct string based on the suite 
		switch(suit)
		{
		case 1: 
		{
			s += "d"; 
			break; 
		}
		case 2: 
		{
			s += "c"; 
			break; 
		}
		case 3: 
		{
			s += "h";
			break; 
		}
		case 4: 
		{
			s += "s"; 
			break; 
		}
		}
		if(value > 9)
		{
			info = s + Integer.toString(value);  
		}
		else
		{
			info = s + "0" + Integer.toString(value);
		}
		imgURL = valueToImage();
	}
	
	public String getCardInfo() {
		return info;
	}
	
	public void setCardInfo(String info)
	{
		this.info = info; 
	}
	
	public String valueToImage()
	{
		switch(suit)
		{
		case 1: return valueToDiamondImage(); 
		case 2: return valueToClubImage(); 
		case 3: return valueToHeartImage(); 
		case 4: return valueToSpadeImage(); 
		default: return "Shit don't exist";
		}
	}
	public String valueToSpadeImage() {
		switch (value)
		{
		case 1: return "../images/spades/card2.png"; 
		case 2: return "../images/spades/card3.png";
		case 3: return "../images/spades/card4.png"; 
		case 4: return "../images/spades/card5.png"; 
		case 5: return "../images/spades/card6.png"; 
		case 6: return "../images/spades/card7.png";
		case 7: return "../images/spades/card8.png"; 
		case 8: return "../images/spades/card9.png"; 
		case 9: return "../images/spades/card10.png"; 
		case 10: return "../images/spades/card11.png"; 
		case 11: return "../images/spades/card12.png";
		case 12: return "../images/spades/card13.png"; 
		case 13: return "../images/spades/card1.png";
		default: return "";
		}
	}
	
	public String valueToHeartImage() {
		switch (value)
		{
		case 1: return "../images/hearts/card2.png"; 
		case 2: return "../images/hearts/card3.png";
		case 3: return "../images/hearts/card4.png"; 
		case 4: return "../images/hearts/card5.png"; 
		case 5: return "../images/hearts/card6.png"; 
		case 6: return "../images/hearts/card7.png";
		case 7: return "../images/hearts/card8.png"; 
		case 8: return "../images/hearts/card9.png"; 
		case 9: return "../images/hearts/card10.png"; 
		case 10: return "../images/hearts/card11.png"; 
		case 11: return "../images/hearts/card12.png";
		case 12: return "../images/hearts/card13.png"; 
		case 13: return "../images/hearts/card1.png";
		default: return "";
		}
	}
	
	public String valueToClubImage() {
		switch (value)
		{
		case 1: return "../images/clubs/card2.png"; 
		case 2: return "../images/clubs/card3.png";
		case 3: return "../images/clubs/card4.png"; 
		case 4: return "../images/clubs/card5.png"; 
		case 5: return "../images/clubs/card6.png"; 
		case 6: return "../images/clubs/card7.png";
		case 7: return "../images/clubs/card8.png"; 
		case 8: return "../images/clubs/card9.png"; 
		case 9: return "../images/clubs/card10.png"; 
		case 10: return "../images/clubs/card11.png"; 
		case 11: return "../images/clubs/card12.png";
		case 12: return "../images/clubs/card13.png"; 
		case 13: return "../images/clubs/card1.png";
		default: return "";
		}
	}
	
	public String valueToDiamondImage() {
		switch (value)
		{
		case 1: return "../images/diamonds/card2.png"; 
		case 2: return "../images/diamonds/card3.png";
		case 3: return "../images/diamonds/card4.png"; 
		case 4: return "../images/diamonds/card5.png"; 
		case 5: return "../images/diamonds/card6.png"; 
		case 6: return "../images/diamonds/card7.png";
		case 7: return "../images/diamonds/card8.png"; 
		case 8: return "../images/diamonds/card9.png"; 
		case 9: return "../images/diamonds/card10.png"; 
		case 10: return "../images/diamonds/card11.png"; 
		case 11: return "../images/diamonds/card12.png";
		case 12: return "../images/diamonds/card13.png"; 
		case 13: return "../images/diamonds/card1.png";
		default: return "";
		}
	}
	public String getImgURL() {
		return imgURL;
	}
	
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	
	public int getValue()
	{
		return value; 
	}
	
	public void setValue(int value)
	{
		this.value = value; 
	}
	
	public int getSuit()
	{
		return suit; 
	}
//	public int randomCardValue() { //RNG to get a random card 
//		// to do
//		Random rand = new Random(); 
//		int value = rand.nextInt(50);	// max is 50
//		return value;
//	}
	
	public int compareTo(card otherCard)
	{
		int otherCardValue = otherCard.getValue(); 
		System.out.println("this card value: " + this.value);
		System.out.println("other card value: " + otherCardValue);
		return this.value - otherCardValue; 
	}

}
