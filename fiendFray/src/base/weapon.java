package base;

import java.util.ArrayList;
import java.util.HashMap;

public class weapon {
	private String imgURL;
	private int damage;
	private int price;
	private HashMap<String, Integer> handToDamage;
	private int weaponID;
	//String for HashMap are
	//nothing
	//onePair
	//twoPair
	//threeKind
	//straight
	//fourKind
	//fiveKind
	public weapon() {
		handToDamage = new HashMap<String, Integer>();
		//debug 
		handToDamage.put("nothing", 1);
		handToDamage.put("onePair", 2);
		handToDamage.put("twoPair", 3);
		handToDamage.put("threeKind", 4);
		handToDamage.put("straight", 5);
		handToDamage.put("flush", 6);
		handToDamage.put("fullHouse", 7);
		handToDamage.put("fourKind", 8);
		handToDamage.put("fiveKind", 30);
		handToDamage.put("straightFlush", 40);
		handToDamage.put("royalFlush", 50);





	}
	
	public void setWeaponID(int weaponID){
		this.weaponID=weaponID;
	}
	public int getWeaponID(){
		return this.weaponID;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	
	public String getImgURL() {
		return imgURL;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int returnDamage(String handName) {
		//TODO:
		//figure out what kind of pokerHand it is
		//pass it to our HashMap
		//get back the damage and return it
		System.out.println("Hash Map Value: "+ handToDamage);
		System.out.println("Hand Name: "+ handName);
		return handToDamage.get(handName);
	}

	public HashMap<String, Integer> getHandToDamage() {
		return handToDamage;
	}

	public void setHandToDamage(HashMap<String, Integer> handToDamage) {
		this.handToDamage = handToDamage;
	}


}
