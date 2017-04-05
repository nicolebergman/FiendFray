package base;

public class pet {
	private String name; 
	private String imageURL; 
	private int currentLevel; 
	private int currentEXP; 
	private int requiredEXPToLevelUp; 
	private int maxHP; 
	private int currentHP; 
	private weapon equippedWeapon; 
	
	public pet()
	{
		
	}
	//GETTERS
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
	public void SetName(String name)
	{
		this.name = name; 
	}
	
	public void SetImageURL(String imageURL)
	{
		this.imageURL = imageURL;  
	}
	
	public void setCurrentLevel(int level)
	{
		currentLevel = level; 
	}
	private int currentEXP; 
	private int requiredEXPToLevelUp; 
	public void setMaxHP(int hp)
	{
		maxHP = hp; 
	}
	public int currentHP(int hp)
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
	
	public void levelUp()
	{
		currentLevel++; 
		currentEXP = 0;
		//Insert expereience formula her
	}
}
