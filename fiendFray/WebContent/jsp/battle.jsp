<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Battle</title>
<link rel = "stylesheet" type = "text/css" href = "../css/battle.css" />
<%@ page import="base.*" %>
</head>
<%@ page import="java.util.*" %>

<body>
	<div id="container">
		<div id="opponentContainer">
			<div class="hpDisplay">
				<h2>HP: 30/30</h2>
			</div>
			<div class= "playerCards">
				<%
					for(int i=0; i<4; ++i)
					{
				%>
						<div class= "card"></div>
				<% 
					}
				%>
			</div>
		</div>
		<div id="cardContainer">
			<div id="cardArray">
				<%
					for(int i=0; i<5; ++i)
					{
				%>
						<div class="cardRow">
						
						</div>
				<%
						for(int j=0; j<5; ++j)
						{
				%>
							<div class="card">
								
							</div>
				
				<% 
						}
					}
				%>
			</div>
		</div>
		
		<div id="currentPlayerContainer">
			<div class="hpDisplay">
				<h2>HP: 30/30</h2>
			</div>
			<div class= "playerCards">
				<%
					for(int i=0; i<4; ++i)
					{
				%>
						<div class= "card"></div>
				<% 
					}
				%>
			</div>
		</div>
	
	</div>
</body>
</html>