package server;

import java.sql.SQLException;

import base.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDriver {
	private Connection conn;
	private String selectName = "Select * FROM FACTORYORDERS WHERE NAME=?";
	private String addProduct = "INSERT INTO FACTORYORDERS(NAME, CREATED) VALUES(?, ?)";
	private String updateProduct = "UPDATE factoryorders SET created=? WHERE name=?";
	public MySQLDriver(){
		try{
			new com.mysql.jdbc.Driver();
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
	

	public void parse(parser newParser){
		
	}
	
	public void add(String productName){
		try{
			PreparedStatement ps = conn.prepareStatement(addProduct);
			ps.setString(1, productName);
			ps.setInt(2, 0);
			ps.executeUpdate();
			System.out.println("Adding product: " + productName+ " to table with count 0");
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void update(String productName, int number){
		try{
			PreparedStatement ps = conn.prepareStatement(updateProduct);
			ps.setString(2, productName);
			ps.setInt(1, number);
			ps.executeUpdate();
			System.out.println("Adding product: " + productName+ " to table with count "+number);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
