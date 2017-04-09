<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel = "stylesheet" type = "text/css" href = "../css/petShop.css" />
<title>Pet Shop</title>
<script>
	// scope socket correctly
	var socket;
	function connectToServer() {
		// create connection to server
		socket = new WebSocket("ws://localhost:8080/fiendFray/fiendFrayServer");
	}
	
	// notify other user's that weapon was bought
	function buy(weapon) {
		socket.send("Someone bought a " + weapon + "!");
		return false;
	}
</script>
</head>
<body onload="connectToServer();" background="../images/clean-pixel-landscape.jpg" style="background-color:#83c0ef;">

<div id="title">
	<h1>Welcome to the Pet Shop!</h1>
</div>

<div id="shopTitle">
	<h2>Take a look around!</h2>
</div>

<div id="currentMoney">
	<h3>Currently held gems: 50</h3>
</div>

<div class="iconbox">
    <div id="bow">
        <img src="../images/weapon_1.png" />
        <p>Bow and Arrow</p>
        <p>Price: 10</p>
        <button class="button" onclick="buy('bow and arrow')">Buy</button>
    </div>
    <div id="club">
        <img src="../images/weapon_2.png" />
        <p>Club</p>
        <p>Price: 10</p>
        <button class="button" onclick="buy('club')">Buy</button>
    </div>
    <div id="hammer">
        <img src="../images/weapon_3.png" />
        <p>Hammer</p>
        <p>Price: 10</p>
        <button class="button" onclick="buy('hammer')">Buy</button>
    </div>
</div>

<br/><br/><br/>

<div class="iconbox">
    <div id="swords">
        <img src="../images/weapon_4.png" />
        <p>Dual Swords</p>
        <p>Price: 10</p>
        <button class="button" onclick="buy('dual sword')">Buy</button>
    </div>
    <div id="staff">
        <img src="../images/weapon_5.png" />
        <p>Staff</p>
        <p>Price: 10</p>
        <button class="button" onclick="buy('staff')">Buy</button>
    </div>
    <div id="lasergun">
        <img src="../images/weapon_6.png" />
        <p>Laser Gun</p>
        <p>Price: 10</p>
        <button class="button" onclick="buy('laser gun')">Buy</button>
    </div>
</div>
	
</body>
</html>