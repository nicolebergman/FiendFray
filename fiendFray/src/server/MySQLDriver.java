package server;

import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import base.parser;

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
				
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newParser;
	}
	
	public static void main(String[] args) {
		MySQLDriver msql = new MySQLDriver();
		msql.connect();
		parser newParser = msql.parseDB();
	}

}
