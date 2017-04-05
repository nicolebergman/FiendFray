package base;

import java.util.Random;

public class card {
	
	private String info;
	private String imgURL; 
	
	card() {
		
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
	
	public int randomCardValue() { //RNG to get a random card 
		// to do
		Random rand = new Random(); 
		int value = rand.nextInt(50);	// max is 50
		return value;
	}

}
