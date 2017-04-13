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
		
		String insertPet = "INSERT INTO pets (petName, currentLevel, currentXP, requiredXPToLevelUp, maxHP, currentHP, weaponID) VALUES (?, 1, 0, 100, 30, 30, 1);";
		try {
			ps = conn.prepareStatement(insertPet);
			ps.setString(1, newUser.getUserPet().getName());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String insertUser = "INSERT INTO users (username, pass, gems, isGuest) VALUES (?, ?, 10, ?)";
		try {
			ps = conn.prepareStatement(insertUser);
			ps.setString(1, newUser.getUsername());
			ps.setString(2, newUser.getPassword());
			ps.setBoolean(3, newUser.isGuest());
			rs = ps.executeQuery();
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
				int fullHouse = rs.getInt("fullHouse");
				int fourKind = rs.getInt("fourKind");
				int fiveKind = rs.getInt("fiveKind");
				HashMap<String, Integer> handToDamage  = new HashMap<String, Integer>();
				handToDamage.put("nothing", nothing);
				handToDamage.put("onePair", onePair);
				handToDamage.put("twoPair", twoPair);
				handToDamage.put("threeKind", threeKind);
				handToDamage.put("straight", straight);
				handToDamage.put("fullHouse", fullHouse);
				handToDamage.put("fourKind", fourKind);
				handToDamage.put("fiveKind", fiveKind);
				newParser.getAllWeapons().get(weaponID-1).setHandToDamage(handToDamage);
			}
			ps = conn.prepareStatement("SELECT * FROM pets");
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String petName = rs.getString("petName");
				int currentLevel = rs.getInt("currentLevel");
				int currentXP = rs.getInt("currentXP");
				int requiredXPToLevelUp = rs.getInt("requiredXPToLevelUp");
				int maxHP = rs.getInt("maxHP");
				int currentHP = rs.getInt("currentHp");
				int weaponID = rs.getInt("weaponID");
				String imgURL = "../images/pet"+id+".png";
				pet newPet = new pet();
				newPet.setCurrentHP(currentHP);
				newPet.setCurrentLevel(currentLevel);
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
