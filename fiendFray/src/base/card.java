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
		//appends the card's suite to the info 
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
		info = s + Integer.toString(value);  
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
		case 1: return ""; 
		case 2: return "";
		case 3: return ""; 
		case 4: return ""; 
		case 5: return ""; 
		case 6: return "";
		case 7: return ""; 
		case 8: return ""; 
		case 9: return ""; 
		case 10: return ""; 
		case 11: return "";
		case 12: return ""; 
		case 13: return "";
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
