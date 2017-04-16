package base;

import java.util.ArrayList;

public class pet {
	private String name; 
	private String imageURL; 
	private int petID;
	private int currentLevel; 
	private int currentEXP; 
	private int requiredEXPToLevelUp; 
	private int maxHP; 
	private int currentHP; 
	private weapon equippedWeapon; 
	private int weaponID;
	
	public void setCurrentEXP(int currentEXP) {
		this.currentEXP = currentEXP;
	}
	public void setRequiredEXPToLevelUp(int requiredEXPToLevelUp) {
		this.requiredEXPToLevelUp = requiredEXPToLevelUp;
	}
	public void setEquippedWeapon(weapon equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}
	public pet()
	{
		//debug
		this.name = "p3t";
		this.maxHP = 30; 
		this.currentHP = this.maxHP;
		this.equippedWeapon = new weapon(); 
	}
	//GETTERS
	public int getPetID(){
		return petID;
	}
	public int getWeaponID(){
		return weaponID;
	}
	public void setWeaponID(int weaponID){
		this.weaponID=weaponID;
	}
	public String getName()
	{
		return name; 
	}
	
	public String getImageURL()
	{
		return imageURL; 
	}
	
	public int getCurrentLevel()
	{
		return currentLevel; 
	}
	
	public int getCurrentEXP()
	{
		return currentEXP; 
	}
	
	public int getRequiredEXPToLevelUp()
	{
		return requiredEXPToLevelUp;
	}
	
	public int getMaxHP()
	{
		return maxHP; 
	}
	
	public int getCurrentHP()
	{
		return currentHP; 
	}
	
	public weapon getEquippedWeapon()
	{
		return equippedWeapon; 
	}

	//SETTERS
	public void setPetID(int petID){
		this.petID=petID;
	}
	public void setName(String name)
	{
		this.name = name; 
	}
	
	public void setImageURL(String imageURL)
	{
		this.imageURL = imageURL;  
	}
	
	public void setCurrentLevel(int level)
	{
		currentLevel = level; 
	}
	public void setMaxHP(int hp)
	{
		maxHP = hp; 
	}
	public void setCurrentHP(int hp)
	{
		currentHP = hp;
	}
	public void setEquppedWeapon(weapon weapon)
	{
		equippedWeapon = weapon; 
	}
	
	//Battle Functions
	public void takeDamage(int damage)
	{
		currentHP -= damage; 
	}
	
	public int calculateDamage(String handName){
		//pass the hand to the weapon
		return equippedWeapon.returnDamage(handName);
	}
	
	public void levelUp()
	{
		currentLevel++; 
		currentEXP = 0;
		//Insert expereience formula her
	}
}
