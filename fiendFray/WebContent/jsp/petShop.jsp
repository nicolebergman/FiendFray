<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel = "stylesheet" type = "text/css" href = "../css/petShop.css" />
<title>Pet Shop</title>
<%@ page import="base.stringConstants" %>
<%@ page import="base.user" %>
<%@ page import="base.parser" %>
<%@ page import="server.MySQLDriver" %>
<%@ page import="java.util.*" %>
<script src="../main2.js" type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<% 
	// connect to database
	MySQLDriver msql = new MySQLDriver();
	msql.connect();
	parser newParser = msql.parseDB();
	
	// get current user's name
	user currUser = (user) session.getAttribute(stringConstants.USER);
	String username = currUser.getUsername();
	System.out.println("current user: " + username);
	int currGems = currUser.getGems();
%>
<script>
	// scope socket correctly
	var socket;
	function connectToServer() {
		// create connection to server
		socket = new WebSocket("ws://localhost:8080/fiendFray/fiendFrayServer");
		
		// overriding functions
		socket.onopen = function(event) {
			socket.send("AddToServer~" + "<%= username %>");
		}
	}
	
	// notify other user's that weapon was bought
	function buy(weapon) {
		var name = "<%= username %>";
		socket.send("Buy~" + name + " bought a " + weapon + "!");
		return false;
	}
	
	function switchPage(page) {
		window.location = page + ".jsp";
		socket.send("SwitchPage~" + "<%= username %>");
		return false;
	}
	
	function hidePopup(){
		document.getElementById('shim').style.display=document.getElementById('msgbx').style.display ="none";
	}
	function showPopup(){
		document.getElementById('shim').style.display=document.getElementById('msgbx').style.display ="block";
	}
	

	function errorCheck1 (servletName, errorDivName, value, name){
		var xhttp = new XMLHttpRequest();
		//gets the path
		var path = "/"+window.location.pathname.split("/")[1];
		//create url with first parameter from paramArgs
		var url = path + servletName+"?value="+value+"&name="+name;
		//send synchronous ajax call to servlet
		xhttp.open("GET", url, false);
		xhttp.send();
		//if we got a response text, there must have been an error
		if (xhttp.responseText.trim().length > 0) {
			//set the repsonse text as the innerHTML of the error div
			var response = xhttp.responseText;
			var first = response.split("~")[0];
			var second = response.split("~")[1];
			document.getElementById('shim').style.display=document.getElementById('msgbx').style.display ="block";
			document.getElementById("messageText").innerHTML = "<br>"+"<br>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+second;
			document.getElementById("currentGems").innerHTML = first;
			
			// check whether weapon was successfully bought
			var success = second.split(" ")[1];
			if(success == "successfully") {
				var weaponName = second.split(" ")[4];
				buy(weaponName);
			}
		}
		
		return false;
		
	}
</script>
</head>
<body onload="connectToServer();" background="../images/night.png" style="background-color:#83c0ef;">

<button class="homeButton" onclick="switchPage('homePage')">Go Home</button>

<div id="title">
	<h1>Welcome to the Pet Shop!</h1>
</div>
<div id="shim"></div>
<div id="msgbx">
	<p id="messageText"></p>
	<div id="closer" class="popup-closer">X</div>
</div>
<div id="info">
	<div id="shopTitle">
	<h2>Take a look around!</h2>
	<h3>You have: 
	</div>
	
	<div id="currentMoney">
		<h3>
		<div id="currentGems"><%= currGems %></div>
		<img src="../images/gem.png" style="width:40px; height:40px; margin-top: -34px; margin-right: -64px;">
		</h3>
	</div>
</div>
<div class="iconbox">
    <div id="bow">
        <img src="../images/weapon_1.png" />
        <p>Bow and Arrow (1x)</p>
        <p>Price: 10</p>
        <button class="button" onclick="return errorCheck1('/buyServlet', 'error_message', 1, 'bow and arrow')">Buy</button>
    </div>
    <div id="club">
        <img src="../images/weapon_2.png" />
        <p>Club (2x)</p>
        <p>Price: 20</p>
        <button class="button" onclick="return errorCheck1('/buyServlet', 'error_message', 2, 'club')">Buy</button>
    </div>
    <div id="hammer">
        <img src="../images/weapon_3.png" />
        <p>Hammer (3x)</p>
        <p>Price: 40</p>
        <button class="button" onclick="return errorCheck1('/buyServlet', 'error_message', 3, 'hammer')">Buy</button>
    </div>
</div>

<br/><br/><br/>

<div class="iconbox">
    <div id="swords">
        <img src="../images/weapon_4.png" />
        <p>Dual Swords (4x)</p>
        <p>Price: 80</p>
        <button class="button" onclick="return errorCheck1('/buyServlet', 'error_message', 4, 'dual swords')">Buy</button>
    </div>
    <div id="staff">
        <img src="../images/weapon_5.png" />
        <p>Staff (5x)</p>
        <p>Price: 160</p>
        <button class="button" onclick="return errorCheck1('/buyServlet', 'error_message', 5, 'staff')">Buy</button>
    </div>
    <div id="lasergun">
        <img src="../images/weapon_6.png" />
        <p>Laser Gun (6x)</p>
        <p>Price: 320</p>
        <button class="button" onclick="return errorCheck1('/buyServlet', 'error_message', 6, 'lasergun')">Buy</button>
    </div>
</div>
	<input type="hidden" id="weapon" value="null"/>
</body>
<script>
var tempElement = document.getElementById("closer");
if(tempElement){
	tempElement.addEventListener("click", hidePopup);
}
</script>
</html>