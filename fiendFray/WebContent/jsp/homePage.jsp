<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
<%@ page import="base.stringConstants" %>
<%@ page import="base.user" %>
<%@ page import="base.parser" %>
<%@ page import="server.MySQLDriver" %>
<%@ page import="java.util.*" %>

<% 
	//connect to database
	MySQLDriver msql = new MySQLDriver();
	msql.connect();
	parser newParser = msql.parseDB();
	List<user> allUsers = newParser.getAllUsers();
	
	// get current user attributes
	user currUser = (user) session.getAttribute(stringConstants.USER);
	String username = currUser.getUsername();
	String petName = currUser.getUserPet().getName();
	String petImage = currUser.getUserPet().getImageURL();
	String petLevel = Integer.toString(currUser.getUserPet().getCurrentLevel());
	String maxHP = Integer.toString(currUser.getUserPet().getMaxHP());
	String petExp = Integer.toString(currUser.getUserPet().getCurrentEXP());
	String weaponImage = currUser.getUserPet().getEquippedWeapon().getImgURL();
	String weaponName = "";
	
	// match weapon image with name
	switch(weaponImage) {
	case "../images/weapon_1.png":
		weaponName = "Bow and Arrow";
		break;
	case "../images/weapon_2.png":
		weaponName = "Club";
		break;
	case "../images/weapon_3.png":
		weaponName = "Hammer";
		break;
	case "../images/weapon_4.png":
		weaponName = "Dual Swords";
		break;
	case "../images/weapon_5.png":
		weaponName = "Staff";
		break;
	case "../images/weapon_6.png":
		weaponName = "Laser Gun";
		break;
	}
%>
<script>
	// scope socket correctly
	var socket;
	
	function connectToServer() {
		// create connection to server
		socket = new WebSocket("ws://localhost:8080/fiendFray/fiendFrayServer");
		// overriding functions
		socket.onopen = function(event) {
			document.getElementById("feedtext").innerHTML += "Welcome to Fiend Fray!<br />";
			
			// notify other users of entry
			socket.send("UserEnter~" + "<%= username %>" + " has entered the fray!");
		}
		socket.onmessage = function(event) {
			document.getElementById("feedtext").innerHTML += event.data + "<br />";
		}
	}
	
	function switchPage(page) {
		window.location = page + ".jsp";
		socket.send("SwitchPage~");
		return false;
	}
	
	function Logout(page) {
		window.location = page + ".jsp";
		socket.send("Logout~" + "<%= username %>");
<<<<<<< Updated upstream
=======
		<% 
			/* currUser.isOnline = false;
			msql.updateUser(currUser);  */
		%>
>>>>>>> Stashed changes
		return false;
	}
</script>
 <link rel = "stylesheet" type = "text/css" href = "../css/homePage.css" />
</head>
<body onload="connectToServer();" background="../images/clean-pixel-landscape.jpg" style="background-color:#83c0ef;">
	<div id="container">
		<div id="petInfoContainer">
			<h3 style="width: 200px; padding: 10px; background-color: #ffedad;  border-radius: 20px 20px 20px 20px;"> Welcome <%= username %>! </h3>
			
			<img class="frame" src="../images/frame.png">
			
			<img class="petIcon" src="<%= petImage %>">
			
			<p class="petName"><%= petName %></p>
						
			<button class="petShopButton" onclick="switchPage('petShop')">Pet Shop</button>
			
			<button class="leaderboardButton" onclick="switchPage('Leaderboard')">Leaderboard</button>
					
			<button class="logoutButton" onclick="Logout('loginPage')">Logout</button>
			
			<br/><br/><br/><br/>
			
			<div class="stats">
				<h3>Level: <%= petLevel %></h3>
				<h3>Current EXP: <%= petExp %></h3>
				<h3>Max HP: <%= maxHP %></h3>
				<h3>Weapon Card: <%= weaponName %></h3>
				<h3>Weapon Card Multiplier: TODO</h3>
			</div>
		</div>
		
		<div id="battleFeedContainer">
			<h3 style="width: 200px; padding: 10px; background-color: #ffedad;  border-radius: 20px 20px 20px 20px;"> Event Feed: </h3>
			<div id="feedtext"></div>
		</div>
		
		<div id="activeUsersContainer">
			<h3 style="width: 200px; padding: 10px; background-color: #ffedad;  border-radius: 20px 20px 20px 20px;"> Active Users </h3>
			<div id="usertext">
				<% 
					for(user x : allUsers) {
						if(x.isOnline && !x.getUsername().equals(currUser.getUsername())) {
							%> 
							<input type="submit" class="sendBattleRequestButton" onclick="sendBattleRequest('<%= x.getUsername() %>')" value="<%= x.getUsername() %>"/> <br/> 
							<%
						}
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>