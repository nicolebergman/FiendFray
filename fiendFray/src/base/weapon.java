package base;

import java.util.ArrayList;
import java.util.HashMap;

public class weapon {
	private String imgURL;
	private int damage;
	private HashMap<String, Integer> handToDamage;
	
	weapon() {
		setHandToDamage(new HashMap<String, Integer>());
	}
	
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	
	public String getImgURL() {
		return imgURL;
	}
	
	public int returnDamage(ArrayList<String> pokerHand) {	//returns a number based on pokerHand
		// to do 	
		return damage;
	}

	public HashMap<String, Integer> getHandToDamage() {
		return handToDamage;
	}

	public void setHandToDamage(HashMap<String, Integer> handToDamage) {
		this.handToDamage = handToDamage;
	}


}
