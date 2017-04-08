package base;

import java.util.ArrayList;

public class shop {
	ArrayList<weapon> buyableWeapons; //Remember to add a price variable to weapon
	
	public shop(){
		buyableWeapons = new ArrayList<weapon>();
	}
	
	public boolean buyWeapon(weapon buyableWeapon, user user1){
		if(buyableWeapon.getPrice()<=user1.getGems()){
			user1.getUserPet().setEquppedWeapon(buyableWeapon);
			user1.setGems(user1.getGems()-buyableWeapon.getPrice());
			return true;
		} else{
			return false;
		}
	}
	
	public void setBuyableWeapons(ArrayList<weapon> buyableWeapons){
		this.buyableWeapons = buyableWeapons;
	}
	
	public ArrayList<weapon> getBuyableWeapons(){
		return buyableWeapons;
	}
}
