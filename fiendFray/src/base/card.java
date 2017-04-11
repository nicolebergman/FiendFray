package base;

import java.util.Random;

public class card implements Comparable<card>{
	
	private String info;
	private int value; 
	private String imgURL; 
	
	card() {
		Random rand = new Random(); 
		value = rand.nextInt(2);	// max is 13 for right now
		info=value+"";
	}
	
	public String getCardInfo() {
		return info;
	}
	
	public void setCardInfo(String info) {
		this.info = info;
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
