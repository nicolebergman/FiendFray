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
	hpList=(ArrayList<user>)newParser.getAllUsers().clone();
	gemsList=(ArrayList<user>)newParser.getAllUsers().clone();
	class HPComparator implements Comparator<user> {
	    @Override
	    public int compare(user a, user b) {
	        return a.getUserPet().getMaxHP() < b.getUserPet().getMaxHP() ? -1 : a.getUserPet().getMaxHP() == b.getUserPet().getMaxHP() ? 0 : 1;
	    }
	}
	class GemComparator implements Comparator<user> {
		@Override
	    public int compare(user a, user b) {
	        return a.getGems() < b.getGems() ? -1 : a.getGems() == b.getGems() ? 0 : 1;
	    }
	}
	Collections.sort(hpList, new HPComparator());
	Collections.sort(gemsList, new GemComparator());
%>
<table>
  <tr>
    <th>Most HP</th>
    <th>Most Gems</th>
  </tr>
<%
  	for(int i=gemsList.size()-1; i>=0; i--){
%>
	<tr>
    <td>
    <%=hpList.get(i).getUsername()%>
    </td>
    <td>
    <%=gemsList.get(i).getUsername()%>
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