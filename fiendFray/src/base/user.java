package base;

import java.util.ArrayList;

public class user {
	private String username;
	private String password;
	private Pet userPet;
	private int gems;
	private boolean isGuest;
	
	public user(String username, String password, Pet userPet){
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
		this.userPet = new Pet();
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

	public Pet getUserPet() {
		return userPet;
	}

	public void setUserPet(Pet userPet) {
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
