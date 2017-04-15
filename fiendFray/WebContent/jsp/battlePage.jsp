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
%>

<script>
//scope socket correctly
var socket;

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
		} else {
			document.getElementById("chatText").innerHTML += event.data + "<br />";
		}
	}
}

var firstCardPlaced = false;
var secondCardPlaced = false;
var firstCardCoords = "";
var secondCardCoords = "";

function _(id){
   return document.getElementById(id);	
}

function drag_start(event) {
    _('app_status').innerHTML = "Dragging the "+event.target.getAttribute('id');
    event.dataTransfer.dropEffect = "move";
    event.dataTransfer.setData("text", event.target.getAttribute('id') );
    event.dataTransfer.setData("imageURL", event.target.getAttribute('src') );
}

function drag_enter(event) {
    _('app_status').innerHTML = "You are dragging over the "+event.target.getAttribute('id');
}

function drag_leave(event) {
    _('app_status').innerHTML = "You left the "+event.target.getAttribute('id');
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
	    document.getElementById("chatText").innerHTML += "Dropped " + elem_id + " into " + event.target.getAttribute('id') + "<br/>";
	    
	    // record placement coordinates
	    if(firstCardPlaced == false) {
	    	firstCardPlaced = true;
	    	firstCardCoords = event.target.getAttribute('id');
	    } else {
	    	secondCardPlaced = true;
	    	secondCardCoords = event.target.getAttribute('id');
	    	// send input message to socket
	    	//FUCKING DO IT
	    }
	    
	    _(elem_id).removeAttribute("draggable");
	    _(elem_id).style.cursor = "default";
	} else {
		document.getElementById("chatText").innerHTML += "A card is already in that space! <br/>";
	}
}

function drag_end(event) {
    //_('app_status').innerHTML = "You let the "+event.target.getAttribute('id')+" go.";
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
	        <img src="../images/card1.png" />
	    </div>
	    <div id="card">
	        <img src="../images/card2.png" />
	    </div>
	    <div id="card">
	        <img src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img src="../images/card3.png" />
	    </div>
	</div>
</div>

<form name="chatform" onsubmit="return sendMessage();">
	<input type="text" name="message" />
	<input type="submit" name="submit" value="Chat!" /> <br/>
	<div id="chatText"></div>
</form>

<div class="yourCards">
	<div class="iconbox">
	    <div id="card">
	        <img id="card1" class="draggable" draggable="true" ondragstart="drag_start(event)" ondragend="drag_end(event)" src="../images/card1.png" />
	    </div>
	    <div id="card">
	        <img id="card2" class="draggable" draggable="true" ondragstart="drag_start(event)" src="../images/card2.png" />
	    </div>
	    <div id="card">
	        <img id="card3" class="draggable" draggable="true" ondragstart="drag_start(event)" src="../images/card3.png" />
	    </div>
	    <div id="card">
	        <img id="card4" class="draggable" draggable="true" ondragstart="drag_start(event)" src="../images/card3.png" />
	    </div>
	</div>
</div>

<h2 id="app_status">App status...</h2>

</body >
</html>