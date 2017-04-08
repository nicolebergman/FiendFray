<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pet Profile</title>
<script>
	// scope socket correctly
	var socket;
	function connectToServer() {
		// create connection to server
		socket = new WebSocket("ws://localhost:8080/fiendFray/fiendFrayServer");
		// overriding functions
		socket.onopen = function(event) {
			document.getElementById("mytext").innerHTML += "Connecting...<br />";
		}
		socket.onmessage = function(event) {
			document.getElementById("mytext").innerHTML += event.data + "<br />";
		}
		socket.onclose = function(event) {
			document.getElementById("mytext").innerHTML += "Closing...<br />";
		}
	}
	function sendMessage() {
		socket.send("Yuvan - " + document.chatform.message.value);
		// do not submit the form
		return false;
	}
</script>
 <link rel = "stylesheet" type = "text/css" href = "../css/homePage.css" />
</head>
<body onload="connectToServer();">
	<div id="container">
		<div id="petInfoContainer">
			<div id="petImage">
			
			</div>
			<h3>Pet Name</h3>
			<h3>Level: </h3>
			<h3>Current EXP: </h3>
			<h3> CurrHP/MaxHP</h3>
			<h3>Weapon Card</h3>
			<h3>Weapon Card Damage</h3>
		</div>
		
		<div id="battleFeedContainer">
			<h3>Battle Feed</h3>
			<div id="mytext"></div>
		</div>
		
		<div id="activeUsersContainer">
			<h3>Active Users</h3>
		</div>
	</div>
</body>
</html>