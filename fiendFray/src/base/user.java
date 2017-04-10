package base;

import java.util.ArrayList;

public class user {
	private String username;
	private String password;
	private ArrayList<card> currentHand;
	private pet userPet;
	private int gems;
	private boolean isGuest;
	
	//debug
	
	public user(String username, String password, pet userPet){
		this.username = username;
		this.password = password;
		this.userPet = userPet;
		this.gems = 10;
		this.isGuest = false;
		this.currentHand = new ArrayList<card>();
	}
	
	public user() {
		// TODO: add automatic guest name
		this.username = "Guest";
		this.password = "";
		this.userPet = new pet();
		this.gems = 10;
		this.isGuest = true;
		this.currentHand = new ArrayList<card>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public pet getUserPet() {
		return userPet;
	}

	public void setUserPet(pet userPet) {
		this.userPet = userPet;
	}

	public int getGems() {
		return gems;
	}

	public void setGems(int gems) {
		this.gems = gems;
	}

	public boolean isGuest() {
		return isGuest;
	}

	public void setGuest(boolean isGuest) {
		this.isGuest = isGuest;
	} 
	
	//battle functions
	public void addCardToHand(card newCard){
		//Do not add more cards to hand if you're maxed at 4
		if(currentHand.size() == 4)
		{
			return; 
		}
		this.currentHand.add(newCard);
	}
	
	public void emptyHand(){
		this.currentHand.clear(); 
	}
	
	public card chooseCardFromHand(int index)
	{
		card chosenCard = null; 
		if(index >= 0 && index < currentHand.size())
		{
			chosenCard = currentHand.get(index); 
			currentHand.remove(index);
		}
		return chosenCard; 
	}
	
	public int getSizeOfHand()
	{
		return currentHand.size(); 
	}
	public card getCardAtIndex(int index)
	{
		return currentHand.get(index);
	}
	
	public void removeCardAtIndex(int index)
	{
		currentHand.remove(index);
	}
	public ArrayList<card> getCurrentHand()
	{
		return currentHand; 
	}
	
	public void takeDamage(int damage)
	{
		userPet.takeDamage(damage);
	}
}
