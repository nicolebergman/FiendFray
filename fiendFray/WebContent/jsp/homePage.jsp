<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
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
			socket.send("Someone has entered the fray!");
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
</script>
 <link rel = "stylesheet" type = "text/css" href = "../css/homePage.css" />
</head>
<body onload="connectToServer();" background="../images/clean-pixel-landscape.jpg" style="background-color:#83c0ef;">
	<div id="container">
		<div id="petInfoContainer">
			<h3 style="width: 200px; padding: 10px; background-color: #ffedad;  border-radius: 20px 20px 20px 20px;"> Welcome Home! </h3>
			
			<div class="iconbox">
			    <div id="bow">
			        <img src="../images/pet3.png" />
			        <p>Sparky</p>
			    </div>
			    <div id="menus">
			        <button class="button" onclick="switchPage('battlePage')">Battle!</button>
			        <button class="button" onclick="switchPage('petShop')">Pet Shop</button>
			        <button class="button" onclick="switchPage('Leaderboard')">Leaderboard</button>
			    </div>
			</div>
					
			<br/><br/><br/><br/>
				
			<h3>Pet Name</h3>
			<h3>Level: </h3>
			<h3>Current EXP: </h3>
			<h3>CurrHP/MaxHP</h3>
			<h3>Weapon Card</h3>
			<h3>Weapon Card Damage</h3>
		</div>
		
		<div id="battleFeedContainer">
			<h3 style="width: 200px; padding: 10px; background-color: #ffedad;  border-radius: 20px 20px 20px 20px;"> Event Feed: </h3>
			<div id="feedtext"></div>
		</div>
		
		<div id="activeUsersContainer">
			<h3 style="width: 200px; padding: 10px; background-color: #ffedad;  border-radius: 20px 20px 20px 20px;"> Active Users </h3>
			<div id="usertext"></div>
		</div>
	</div>
</body>
</html>