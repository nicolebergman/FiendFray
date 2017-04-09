package base;

import java.util.ArrayList;

public class parser {
	private ArrayList<battle> currentBattles;
	private ArrayList<user> allUsers;
	private ArrayList<weapon> allWeapons;
	private shop currentShop;
	
	public parser(){
		currentBattles = new ArrayList<battle>();
		allUsers = new ArrayList<user>();
		allWeapons = new ArrayList<weapon>();
		currentShop = new shop();
	}
	
	public void addUser(user newUser){
		allUsers.add(newUser);
	}
	public ArrayList<user> getAllUsers(){
		return allUsers;
	}
	
	public void addWeapon(weapon newWeapon){
		allWeapons.add(newWeapon);
	}
	public ArrayList<weapon> getAllWeapons(){
		return allWeapons;
	}
	
	public void setShop(){
		currentShop.setBuyableWeapons(allWeapons);
	}
	public void printInfo(){
		for(int i=0; i<allUsers.size(); i++){
			System.out.println("-------------------------");
			System.out.println("USER: "+i);
			System.out.println(allUsers.get(i).getUsername());
			System.out.println(allUsers.get(i).getPassword());
			System.out.println(allUsers.get(i).getGems());
			System.out.println("PET:");
			System.out.println(allUsers.get(i).getUserPet().getName());
			System.out.println(allUsers.get(i).getUserPet().getCurrentLevel());
			System.out.println(allUsers.get(i).getUserPet().getMaxHP());
			System.out.println("WEAPON:");
			System.out.println(allUsers.get(i).getUserPet().getEquippedWeapon().getPrice());
		}
	}
}
