package base;

import java.util.ArrayList;

public class user {
	private String username;
	private String password;
	private pet userPet;
	private int gems;
	private boolean isGuest;
	
	public user(String username, String password, pet userPet){
		this.username = username;
		this.password = password;
		this.userPet = userPet;
		this.gems = 10;
		this.isGuest = false;
	}
	
	public user() {
		// TODO: add automatic guest name
		this.username = "Guest";
		this.password = "";
		this.userPet = new pet();
		this.gems = 10;
		this.isGuest = true;
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
}
