package base;

import java.util.Random;

public class card implements Comparable<card>{
	
	private String info;
	private int value;
	private int suit; 
	private String imgURL; 
	
	card() {
		Random rand = new Random(); 
		value = rand.nextInt(13) + 1;	// max is 13 for right now
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
	
	public String valueToImage() {
		switch (value)
		{
		case 1: return "../images/card2.png"; 
		case 2: return "../images/card3.png";
		case 3: return "../images/card4.png"; 
		case 4: return "../images/card5.png"; 
		case 5: return "../images/card6.png"; 
		case 6: return "../images/card7.png";
		case 7: return "../images/card8.png"; 
		case 8: return "../images/card9.png"; 
		case 9: return "../images/card10.png"; 
		case 10: return "../images/card11.png"; 
		case 11: return "../images/card12.png";
		case 12: return "../images/card13.png"; 
		case 13: return "../images/card1.png";
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
		return this.value - otherCardValue; 
	}

}
