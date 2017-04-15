<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Leaderboard</title>
<link rel = "stylesheet" type = "text/css" href = "../css/Leaderboard.css" />
</head>
<body style="background-color:#83c0ef;" background="../images/clean-pixel-landscape.jpg">
<div id="title">
	<h1>Leaderboards</h1>
</div>
<%@ page import="base.stringConstants" %>
<%@ page import="base.user" %>
<%@ page import="base.parser" %>
<%@ page import="server.MySQLDriver" %>
<%@ page import="java.util.*" %>
<% 
	// connect to database
	MySQLDriver msql = new MySQLDriver();
	msql.connect();
	parser newParser = msql.parseDB();
	ArrayList<user> hpList = new ArrayList<user>();
	ArrayList<user> gemsList = new ArrayList<user>();
	for(int i=0; i<newParser.getAllUsers().size(); i++){
		if(i==0){
			hpList.add(newParser.getAllUsers().get(i));
			gemsList.add(newParser.getAllUsers().get(i));
		}
		else{
			for(int j=0; j<hpList.size(); j++){
				if(newParser.getAllUsers().get(i).getUserPet().getMaxHP()>=hpList.get(j).getUserPet().getMaxHP()){
					hpList.add(j, newParser.getAllUsers().get(i));
					break;
				}
				if(j+1==hpList.size()){
					hpList.add(newParser.getAllUsers().get(i));
					break;
				}
			}
			for(int j=0; j<gemsList.size(); j++){
				if(newParser.getAllUsers().get(i).getGems()>=gemsList.get(j).getGems()){
					gemsList.add(j, newParser.getAllUsers().get(i));
					break;
				}
				if(j+1==gemsList.size()){
					gemsList.add(newParser.getAllUsers().get(i));
					break;
				}
			}
		}
	}
%>
<table>
  <tr>
    <th>Most HP</th>
    <th>Most Gems</th>
  </tr>
<%
  	for(int i=0; i<gemsList.size(); i++){
%>
	<tr>
    <td>
    <%=gemsList.get(i).getUsername()%>
    </td>
    <td>
    <%=hpList.get(i).getUsername()%>
    </td>
    </tr>
<%
  	}
%>
</table>

<div class="iconbox">
    <div id="swords">
        <img src="../images/pet1.png" />
    </div>
    <div id="staff">
        <img src="../images/pet2.png" />
    </div>
    <div id="lasergun">
        <img src="../images/pet3.png" />
    </div>
</div>

</body>
</html>