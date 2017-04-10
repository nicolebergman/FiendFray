package base;

import java.util.ArrayList;
import java.util.HashMap;

public class parser {
	private ArrayList<battle> currentBattles;
	private ArrayList<user> allUsers;
	private ArrayList<weapon> allWeapons;
	private HashMap<String, user> usersMap;
	
	private shop currentShop;
	
	public parser(){
		currentBattles = new ArrayList<battle>();
		allUsers = new ArrayList<user>();
		allWeapons = new ArrayList<weapon>();
		usersMap = new HashMap<String, user>();
		currentShop = new shop();
	}
	
	public void addUser(user newUser){
		allUsers.add(newUser);
	}
	
	public void addToUserMap(String username, user newUser) {
		usersMap.put(username, newUser);
	}
	
	public HashMap<String, user> getUsersMap() {
		return usersMap;
	}
	
	public ArrayList<user> getAllUsers(){
		return allUsers;
	}
	
	
	
	// validation functions
	// check is username is valid
	 public boolean validUsername(String username) {
		 return allUsers.contains(username);
	 }
	 // check if password is valid
	 public boolean correctPassword(String username, String password) {
		 return usersMap.get(username).getPassword().equals(password); 
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
