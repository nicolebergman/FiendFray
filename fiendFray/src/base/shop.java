package base;

import java.util.ArrayList;

public class shop {
	ArrayList<weapon> buyableWeapons; //Remember to add a price variable to weapon
	
	public shop(){
		buyableWeapons = new ArrayList<weapon>();
	}
	
	public boolean buyWeapon(weapon buyableWeapon, user user1){
		//TO DO
		//Add logic whether user can buy weapon or not
		return true;
	}
	
	public void setBuyableWeapons(ArrayList<weapon> buyableWeapons){
		this.buyableWeapons = buyableWeapons;
	}
	
	public ArrayList<weapon> getBuyableWeapons(){
		return buyableWeapons;
	}
}
