package server;

import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.Statement;

import base.parser;
import base.pet;
import base.user;
import base.weapon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDriver {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public MySQLDriver(){
		try{
			new com.mysql.jdbc.Driver();
			ps = null;
			rs = null;
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void connect(){
		try{
			conn = DriverManager.getConnection("Jdbc:mysql://localhost:3306/fiendFrayDB?user=root&password=chrisnick&useSSL=false");
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void stop(){
		try{
			conn.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addUser(user newUser){
		
		String insertPet = "INSERT INTO pets (petID, petName, currentLevel, currentXP, requiredXPToLevelUp, maxHP, currentHP, weaponID) VALUES (?, ?, 1, 0, 100, 30, 30, 1);";
		try {
			ps = conn.prepareStatement(insertPet);
			ps.setInt(1, newUser.getUserPet().getPetID());
			ps.setString(2, newUser.getUserPet().getName());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String insertUser = "INSERT INTO users (username, pass, gems, isGuest, isOnline) VALUES (?, ?, 10, ?, ?)";
		try {
			ps = conn.prepareStatement(insertUser);
			ps.setString(1, newUser.getUsername());
			ps.setString(2, newUser.getPassword());
			ps.setBoolean(3, newUser.isGuest());
			ps.setBoolean(4, true);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateGems(user newUser){
		try {
			ps = conn.prepareStatement("UPDATE users SET gems=? WHERE username=?");
			ps.setInt(1, newUser.getGems());
			ps.setString(2, newUser.getUsername());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateEXP(user newUser){
		try {
			ps = conn.prepareStatement("UPDATE pets SET currentLevel=? WHERE petID=?");
			ps.setInt(1, newUser.getUserPet().getCurrentLevel());
			ps.setInt(2, newUser.getUserPet().getPetID());
			ps.executeUpdate();
			ps = conn.prepareStatement("UPDATE pets SET currentXP=? WHERE petID=?");
			ps.setInt(1, newUser.getUserPet().getCurrentEXP());
			ps.setInt(2, newUser.getUserPet().getPetID());
			ps.executeUpdate();
			ps = conn.prepareStatement("UPDATE pets SET requiredXPToLevelUp=? WHERE petID=?");
			ps.setInt(1, newUser.getUserPet().getRequiredEXPToLevelUp());
			ps.setInt(2, newUser.getUserPet().getPetID());
			ps.executeUpdate();
			ps = conn.prepareStatement("UPDATE pets SET maxHP=? WHERE petID=?");
			ps.setInt(1, newUser.getUserPet().getMaxHP());
			ps.setInt(2, newUser.getUserPet().getPetID());
			ps.executeUpdate();
			ps = conn.prepareStatement("UPDATE pets SET currentHP=? WHERE petID=?");
			ps.setInt(1, newUser.getUserPet().getMaxHP());
			ps.setInt(2, newUser.getUserPet().getPetID());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateUser(user newUser){
		try {
			ps = conn.prepareStatement("UPDATE users SET isOnline=? WHERE username=?");
			ps.setBoolean(1, newUser.isOnline);
			ps.setString(2, newUser.getUsername());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void userBought(user newUser, int userID){
		try {
			ps = conn.prepareStatement("UPDATE users SET gems=? WHERE username=?");
			ps.setInt(1, newUser.getGems());
			ps.setString(2, newUser.getUsername());
			ps.executeUpdate();
			ps = conn.prepareStatement("UPDATE pets SET weaponID=? WHERE id=?");
			ps.setInt(1, newUser.getUserPet().getEquippedWeapon().getWeaponID());
			ps.setInt(2, newUser.getID());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public parser parseDB(){
		parser newParser = new parser();
		try {
			ps = conn.prepareStatement("SELECT * FROM users");
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String pass = rs.getString("pass");
				int gems = rs.getInt("gems");
				boolean isGuest = rs.getBoolean("isGuest");
				boolean isOnline = rs.getBoolean("isOnline");
				user newUser = new user();
				newUser.setID(id);
				newUser.setUsername(username);
				newUser.setPassword(pass);
				newUser.setGuest(isGuest);
				newUser.setGems(gems);
				newUser.isOnline=isOnline;
				newParser.addUser(newUser);
				newParser.addToUserMap(username, newUser);
			}
			ps = conn.prepareStatement("SELECT * FROM weapons");
			rs = ps.executeQuery();
			while(rs.next()){
				int weaponID = rs.getInt("weaponID");
				int price = rs.getInt("price");
				String imgURL = "../images/weapon_"+weaponID+".png";
				weapon newWeapon = new weapon();
				newWeapon.setWeaponID(weaponID);
				newWeapon.setPrice(price);
				newWeapon.setImgURL(imgURL);
				newParser.addWeapon(newWeapon);
			}
			ps = conn.prepareStatement("SELECT * FROM handToDamage");
			rs = ps.executeQuery();
			while(rs.next()){
				int weaponID = rs.getInt("weaponID");
				int nothing = rs.getInt("nothing");
				int onePair = rs.getInt("onePair");
				int twoPair = rs.getInt("twoPair");
				int threeKind = rs.getInt("threeKind");
				int straight = rs.getInt("straight");
				int flush = rs.getInt("flushHand");
				int fullHouse = rs.getInt("fullHouse");
				int fourKind = rs.getInt("fourKind");
				int straightFlush = rs.getInt("straightFlush");
				int fiveKind = rs.getInt("fiveKind");
				int royalFlush = rs.getInt("royalFlush");
				HashMap<String, Integer> handToDamage  = new HashMap<String, Integer>();
				handToDamage.put("nothing", nothing);
				handToDamage.put("onePair", onePair);
				handToDamage.put("twoPair", twoPair);
				handToDamage.put("threeKind", threeKind);
				handToDamage.put("straight", straight);
				handToDamage.put("flush", flush);
				handToDamage.put("fullHouse", fullHouse);
				handToDamage.put("fourKind", fourKind);
				handToDamage.put("straightFlush", straightFlush);
				handToDamage.put("fiveKind", fiveKind);
				handToDamage.put("royalFlush", royalFlush);
				newParser.getAllWeapons().get(weaponID-1).setHandToDamage(handToDamage);
			}
			ps = conn.prepareStatement("SELECT * FROM pets");
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String petName = rs.getString("petName");
				int petID = rs.getInt("petID");
				int currentLevel = rs.getInt("currentLevel");
				int currentXP = rs.getInt("currentXP");
				int requiredXPToLevelUp = rs.getInt("requiredXPToLevelUp");
				int maxHP = rs.getInt("maxHP");
				int currentHP = rs.getInt("currentHp");
				int weaponID = rs.getInt("weaponID");
				String imgURL = "../images/pet"+petID+".png";
				pet newPet = new pet();
				newPet.setPetID(petID);
				newPet.setWeaponID(weaponID);
				newPet.setCurrentHP(currentHP);
				newPet.setCurrentLevel(currentLevel);
				newPet.setRequiredEXPToLevelUp(requiredXPToLevelUp);
				newPet.setCurrentEXP(currentXP);
				newPet.setMaxHP(maxHP);
				newPet.setName(petName);
				newPet.setImageURL(imgURL);
				newPet.setEquppedWeapon(newParser.getAllWeapons().get(weaponID-1));
				newParser.getAllUsers().get(id-1).setUserPet(newPet);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//newParser.printInfo();
		newParser.setShop();
		return newParser;
	}
	
//	MySQLDriver msql = new MySQLDriver();
//	msql.connect();
//	parser newParser = msql.parseDB();
	
	
//	public static void main(String[] args) {
//		MySQLDriver msql = new MySQLDriver();
//		msql.connect();
//		parser newParser = msql.parseDB();
//	}

}
