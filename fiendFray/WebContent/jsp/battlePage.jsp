<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Battle Page</title>
<link rel = "stylesheet" type = "text/css" href = "../css/battlePage.css" />
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
	
	// get current user attributes
	user currUser = (user) session.getAttribute(stringConstants.USER);
	String username = currUser.getUsername();
	String battleIdStr = (String) session.getAttribute("battleId");
	int battleId = Integer.parseInt(battleIdStr);
	String userIdStr = (String) session.getAttribute("userId");
	int userId = Integer.parseInt(userIdStr);
	int currUserMaxHP = currUser.getUserPet().getMaxHP();
%>

<script>
//scope socket correctly
var socket;
var firstCardPlaced = false;
var secondCardPlaced = false;
var myTurn = false;
var updated = false;
var firstCardIndex = "";
var secondCardIndex = "";
var firstCardCoords = "";
var secondCardCoords = "";
var maxHealthDetected = false;
var yourMaxHealth = 0;
var opponentMaxHealth = 0;
var yourPrevHealth = 0;
var opponentPrevHealth = 0;

function connectToServer() {
	// create connection to server
	socket = new WebSocket("ws://localhost:8080/fiendFray/fiendFrayServer");
	// overriding functions
	socket.onopen = function(event) {
		document.getElementById("chatText").innerHTML += "Moderator: Let the battle begin! <br/>";
		
		// notify other users of entry
		socket.send("AddToServer~" + "<%= username %>");
		socket.send("StartGame~" + <%= battleIdStr %>);
	}
	socket.onmessage = function(event) {
		var commands = event.data.split("~");
		if(commands[0] == "UpdateBoard") {
			document.getElementById("00").src = commands[1];
			document.getElementById("01").src = commands[2];
			document.getElementById("02").src = commands[3];
			document.getElementById("03").src = commands[4];
			document.getElementById("04").src = commands[5];
			document.getElementById("10").src = commands[6];
			document.getElementById("11").src = commands[7];
			document.getElementById("12").src = commands[8];
			document.getElementById("13").src = commands[9];
			document.getElementById("14").src = commands[10];
			document.getElementById("20").src = commands[11];
			document.getElementById("21").src = commands[12];
			document.getElementById("22").src = commands[13];
			document.getElementById("23").src = commands[14];
			document.getElementById("24").src = commands[15];
			document.getElementById("30").src = commands[16];
			document.getElementById("31").src = commands[17];
			document.getElementById("32").src = commands[18];
			document.getElementById("33").src = commands[19];
			document.getElementById("34").src = commands[20];
			document.getElementById("40").src = commands[21];
			document.getElementById("41").src = commands[22];
			document.getElementById("42").src = commands[23];
			document.getElementById("43").src = commands[24];
			document.getElementById("44").src = commands[25];
			
			var userID = <%= userId %>;
			var userIDStr = userID.toString();
			if(userID == 1) {				
				// show correct number of hearts
				var yourCurrHealth = parseInt(commands[26]);
				var opponentCurrHealth = parseInt(commands[27]);
				
				if(!maxHealthDetected) {
					yourMaxHealth = yourCurrHealth;
					yourPrevHealth = yourCurrHealth;
					opponentMaxHealth = opponentCurrHealth;
					opponentPrevHealth = opponentCurrHealth;
					maxHealthDetected = true;
				}
				
				// mark health
				document.getElementById("yourHealth").innerHTML = "Your Health: " + commands[26] + "/" + yourMaxHealth;
				document.getElementById("opponentHealth").innerHTML = "Opponent Health: " + commands[27] + "/" + opponentMaxHealth;
				
				// announce damage
				if(yourCurrHealth != yourPrevHealth) {
					var damage = yourPrevHealth - yourCurrHealth;
					document.getElementById("chatText").innerHTML += "Moderator: You received " + damage + " damage! <br/>";
					yourPrevHealth -= damage;
				}
				
				if(opponentCurrHealth != opponentPrevHealth) {
					var damage = opponentPrevHealth - opponentCurrHealth;
					document.getElementById("chatText").innerHTML += "Moderator: You dealt " + damage + " damage! <br/>";
					opponentPrevHealth -= damage;
				}
				
				var yourNumHearts = Math.floor( (yourCurrHealth * 10) / yourMaxHealth );
				for(i = 1; i <= yourNumHearts; i++) {
					var heartId = "yourHeart" + i.toString();
					document.getElementById(heartId).style.visibility = 'visible';
				}
				for(i = yourNumHearts + 1; i <= 10; i++) {
					var heartId = "yourHeart" + i.toString();
					document.getElementById(heartId).style.visibility = 'hidden';
				}
				
				var opponentNumHearts = Math.floor( (opponentCurrHealth * 10) / opponentMaxHealth );
				for(i = 1; i <= opponentNumHearts; i++) {
					var heartId = "opponentHeart" + i.toString();
					document.getElementById(heartId).style.visibility = 'visible';
				}
				for(i = opponentNumHearts + 1; i <= 10; i++) {
					var heartId = "opponentHeart" + i.toString();
					document.getElementById(heartId).style.visibility = 'hidden';
				}
				
				// fill hand with cards
				document.getElementById("yourCard1").src = commands[28];
				document.getElementById("yourCard2").src = commands[29];
				document.getElementById("yourCard3").src = commands[30];
				document.getElementById("yourCard4").src = commands[31];
				
				document.getElementById("opponentCard1").src = commands[32];
				document.getElementById("opponentCard2").src = commands[33];
				document.getElementById("opponentCard3").src = commands[34];
				document.getElementById("opponentCard4").src = commands[35];
			} else {
				// show correct number of hearts
				var yourCurrHealth = parseInt(commands[27]);
				var opponentCurrHealth = parseInt(commands[26]);
				
				if(!maxHealthDetected) {
					yourMaxHealth = yourCurrHealth;
					yourPrevHealth = yourCurrHealth;
					opponentMaxHealth = opponentCurrHealth;
					opponentPrevHealth = opponentCurrHealth;
					maxHealthDetected = true;
				}
				
				document.getElementById("yourHealth").innerHTML = "Your Health: " + commands[27] + "/" + yourMaxHealth;
				document.getElementById("opponentHealth").innerHTML = "Opponent Health: " + commands[26] + "/" + opponentMaxHealth;
				
				// announce damage
				if(yourCurrHealth != yourPrevHealth) {
					var damage = yourPrevHealth - yourCurrHealth;
					document.getElementById("chatText").innerHTML += "Moderator: You received " + damage + " damage! <br/>";
					yourPrevHealth -= damage;
				}
				
				if(opponentCurrHealth != opponentPrevHealth) {
					var damage = opponentPrevHealth - opponentCurrHealth;
					document.getElementById("chatText").innerHTML += "Moderator: You dealt " + damage + " damage! <br/>";
					opponentPrevHealth -= damage;
				}
								
				var yourNumHearts = Math.floor( (yourCurrHealth * 10) / yourMaxHealth );
				for(i = 1; i <= yourNumHearts; i++) {
					var heartId = "yourHeart" + i.toString();
					document.getElementById(heartId).style.visibility = 'visible';
				}
				for(i = yourNumHearts + 1; i <= 10; i++) {
					var heartId = "yourHeart" + i.toString();
					document.getElementById(heartId).style.visibility = 'hidden';
				}
				
				var opponentNumHearts = Math.floor( (opponentCurrHealth * 10) / opponentMaxHealth );
				for(i = 1; i <= opponentNumHearts; i++) {
					var heartId = "opponentHeart" + i.toString();
					document.getElementById(heartId).style.visibility = 'visible';
				}
				for(i = opponentNumHearts + 1; i <= 10; i++) {
					var heartId = "opponentHeart" + i.toString();
					document.getElementById(heartId).style.visibility = 'hidden';
				}
				
				// fill hand with cards
				document.getElementById("opponentCard1").src = commands[28];
				document.getElementById("opponentCard2").src = commands[29];
				document.getElementById("opponentCard3").src = commands[30];
				document.getElementById("opponentCard4").src = commands[31];
				
				document.getElementById("yourCard1").src = commands[32];
				document.getElementById("yourCard2").src = commands[33];
				document.getElementById("yourCard3").src = commands[34];
				document.getElementById("yourCard4").src = commands[35];
			}
			
			updated = true;
		} else if(commands[0] == "GameOver") {
			var userID = <%= userId %>;
			var userIDStr = userID.toString();
			document.getElementById('shim').style.display=document.getElementById('msgbx').style.display ="block";
			if(userIDStr == commands[1]) {
				document.getElementById("chatText").innerHTML += "You win! <br />";
				document.getElementById("winnerText").style.display="block";
				document.getElementById("winnerButton").style.display="block";
			} else {
				document.getElementById("chatText").innerHTML += "You lose! <br />";
				document.getElementById("loserText").style.display="block";
				document.getElementById("loserButton").style.display="block";
			}
		} else {
			document.getElementById("chatText").innerHTML += event.data + "<br />";
		}
		
		// tell user they can place
		if(commands[36] == userIDStr) {
			myTurn = true;
			firstCardPlaced = false;
			secondCardPlaced = false;
			
			if(updated) {
				document.getElementById("chatText").innerHTML += "Moderator: It's your turn! <br />";
				updated = false;
			}
		} else {
			myTurn = false;
			firstCardPlaced = false;
			secondCardPlaced = false;
			
			if(updated) {
				document.getElementById("chatText").innerHTML += "Moderator: Your opponent is making their move... <br />";
				updated = false;
			}
		}
	}
}

function _(id){
   return document.getElementById(id);	
}

function drag_start(event) {
	if(myTurn) {
	    event.dataTransfer.dropEffect = "move";
	    event.dataTransfer.setData("text", event.target.getAttribute('id') );
	    event.dataTransfer.setData("imageURL", event.target.getAttribute('src') );
	}
}

function drag_enter(event) {

}

function drag_leave(event) {

}

function drag_drop(event) {	
	if(event.target.getAttribute('src') == "../images/nocard.png") {
		event.preventDefault();
	    var elem_id = event.dataTransfer.getData("text");
	    var image_src = event.dataTransfer.getData("imageURL");
	    
	    // swap images
	    document.getElementById(event.target.getAttribute('id')).src = image_src;
	    document.getElementById(elem_id).src = "../images/nocard.png";
	   
	    // debug
	    //document.getElementById("chatText").innerHTML += "Dropped " + elem_id + " into " + event.target.getAttribute('id') + "<br/>";
	    
	    // record placement coordinates
	    if(firstCardPlaced == false) {
	    	firstCardPlaced = true;
	    	firstCardIndex = elem_id;
	    	firstCardCoords = event.target.getAttribute('id');
	    } else {
	    	secondCardPlaced = true;
	    	secondCardIndex = elem_id;
	    	secondCardCoords = event.target.getAttribute('id');
	    	
	    	// send process input message to server
	    	//document.getElementById("chatText").innerHTML += "process inputs <br/>";
	    	var processInputRequest = "ProcessInputs~" + "<%= battleIdStr %>" + "~" + firstCardIndex + "~" + firstCardCoords + "~" + secondCardIndex + "~" + secondCardCoords;
	   		socket.send(processInputRequest);
	    }
	    
	    _(elem_id).removeAttribute("draggable");
	    _(elem_id).style.cursor = "default";
	} else {
		document.getElementById("chatText").innerHTML += "A card is already in that space! <br/>";
	}
}

function drag_end(event) {

}

function readDropZone(){
    for(var i=0; i < _("drop_zone").children.length; i++){
        alert(_("drop_zone").children[i].id+" is in the drop zone");
    }
}

function sendMessage() {
	socket.send("ChatMessage~" + "<%= currUser.getUsername() %>" + ": " + document.chatform.message.value);
	// do not submit the form
	return false;
}

function winGame() {
	// url + params --> <SERVLETNAME>?<PARAMNAME>=<param>&<PARAMNAME>=<param>
	var url = "../WinnerServlet?username=" + "<%= username %>" + "&winner=" + "true";
	
	// create AJAX request
	var req = new XMLHttpRequest();
	req.open("GET", url, true);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) {
			// once response returned from servlet
			socket.send("SwitchPage~" + "<%= username %>");
			window.location = "homePage.jsp";
		}
	}
	req.send(null);
}

function loseGame() {
	// url + params --> <SERVLETNAME>?<PARAMNAME>=<param>&<PARAMNAME>=<param>
	var url = "../WinnerServlet?username=" + "<%= username %>" + "&winner=" + "false";
	
	// create AJAX request
	var req = new XMLHttpRequest();
	req.open("GET", url, true);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) {
			// once response returned from servlet
			socket.send("SwitchPage~" + "<%= username %>");
			window.location = "homePage.jsp";
		}
	}
	req.send(null);
}
</script>

</head>
<body onload="connectToServer();">
<div class="board">
	<div class="iconbox">
    <div id="card">
        <img id="00" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" />
    </div>
    <div id="card">
        <img id="01" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" />
    </div>
    <div id="card">
        <img id="02" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" />
    </div>
    <div id="card">
        <img id="03" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" />
    </div>
    <div id="card">
        <img id="04" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" />
    </div>
	</div>
	<div class="iconbox">
	    <div id="card">
	        <img id="10" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card1.png" />
	    </div>
	    <div id="card">
	        <img id="11" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card2.png" />
	    </div>
	    <div id="card">
	        <img id="12" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="13" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="14" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	</div>
	<div class="iconbox">
	    <div id="card">
	        <img id="20" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card1.png" />
	    </div>
	    <div id="card">
	        <img id="21" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card2.png" />
	    </div>
	    <div id="card">
	        <img id="22" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="23" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="24" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	</div>
	<div class="iconbox">
	    <div id="card">
	        <img id="30" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card1.png" />
	    </div>
	    <div id="card">
	        <img id="31" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card2.png" />
	    </div>
	    <div id="card">
	        <img id="32" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="33" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="34" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	</div>
	<div class="iconbox">
	    <div id="card">
	        <img id="40" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card1.png" />
	    </div>
	    <div id="card">
	        <img id="41" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card2.png" />
	    </div>
	    <div id="card">
	        <img id="42" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="43" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="44" ondragenter="drag_enter(event)" ondrop="drag_drop(event)" ondragover="return false" ondragleave="drag_leave(event)" src="../images/nocard.png" src="../images/card3.png" />
	    </div>
	</div>
</div>

<div class="opponentCards">
	<div class="iconbox">
	    <div id="card">
	        <img id="opponentCard1" src="../images/card1.png" />
	    </div>
	    <div id="card">
	        <img id="opponentCard2"  src="../images/card2.png" />
	    </div>
	    <div id="card">
	        <img id="opponentCard3" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="opponentCard4" src="../images/card3.png" />
	    </div>
	</div>
</div>

<form name="chatform" onsubmit="return sendMessage();">
	<div id="chatbox">
		<input id="typeHere" type="text" name="message" />
		<input type="submit" name="submit" value="Chat!" /> <br/>
	</div>
	<div id="chatText"></div>
</form>

<div class="yourCards">
	<div class="iconbox">
	    <div id="card">
	        <img id="yourCard1" class="draggable" draggable="true" ondragstart="drag_start(event)" ondragend="drag_end(event)" src="../images/card1.png" />
	    </div>
	    <div id="card">
	        <img id="yourCard2" class="draggable" draggable="true" ondragstart="drag_start(event)" src="../images/card2.png" />
	    </div>
	    <div id="card">
	        <img id="yourCard3" class="draggable" draggable="true" ondragstart="drag_start(event)" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="yourCard4" class="draggable" draggable="true" ondragstart="drag_start(event)" src="../images/card3.png" />
	    </div>
	</div>
</div>

<div class="iconbox" id="opponentHearts">
	<p id="opponentHealth"></p>
	<img class="heart" id="opponentHeart1" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="opponentHeart2" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="opponentHeart3" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="opponentHeart4" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="opponentHeart5" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="opponentHeart6" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="opponentHeart7" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="opponentHeart8" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="opponentHeart9" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="opponentHeart10" src="../images/beatingheart.GIF" style="display: visible;" />
</div>

<div class="iconbox" id="yourHearts">
	<p id="yourHealth"></p>
	<img class="heart" id="yourHeart1" src="../images/beatingheart.GIF" style="visibility: visible;" />
	<img class="heart" id="yourHeart2" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="yourHeart3" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="yourHeart4" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="yourHeart5" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="yourHeart6" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="yourHeart7" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="yourHeart8" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="yourHeart9" src="../images/beatingheart.GIF" style="display: visible;" />
	<img class="heart" id="yourHeart10" src="../images/beatingheart.GIF" style="display: visible;" />
</div>

<img src=<%=currUser.getUserPet().getImageURL()%> style="margin-left: 1093px; margin-top: 320px;">
<div id="shim"></div>
<div id="msgbx">
	<h3 id="winnerText" style="display:none; text-align: center;">You win!</h3>
	<button id="winnerButton" style="display:none; margin-left: 103px;" class="returnHomeButton" onclick="winGame()">Finish</button>
	<h3 id="loserText" style="display:none; text-align: center;">You Lose!</h3>
	<button id="loserButton" style="display:none; margin-left: 103px;" class="returnHomeButton" onclick="loseGame()">Finish</button>
</div>
</body >
</html>