<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import="base.stringConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Sign Up</title>
	<link rel="stylesheet" href="../css/main.css">
	<link rel="stylesheet" href="../css/login.css">
	<link rel="stylesheet" href="../css/signUp.css">
	<link href="https://fonts.googleapis.com/css?family=Lato:700i" rel="stylesheet">
	<script src="../main.js" type="text/javascript"></script>
	<script>
  	function handleClick() {
   		document.getElementById('petImage1').src="../images/pet1H.png";
   		document.getElementById('petImage2').src="../images/pet2.png";
   		document.getElementById('petImage3').src="../images/pet3.png";
   		document.getElementById('tableTextId').value = "../images/pet1.png";
  	}
  	function handleClick1() {
   		document.getElementById('petImage1').src="../images/pet1.png";
   		document.getElementById('petImage2').src="../images/pet2H.png";
   		document.getElementById('petImage3').src="../images/pet3.png";
   		document.getElementById('tableTextId').value = "../images/pet2.png";
  	}
  	function handleClick2() {
   		document.getElementById('petImage1').src="../images/pet1.png";
   		document.getElementById('petImage2').src="../images/pet2.png";
   		document.getElementById('petImage3').src="../images/pet3H.png";
   		document.getElementById('tableTextId').value = "../images/pet3.png";
  	}
	</script>
</head>
<body>

	<div id = "title_container">
		Sign up
	</div>

	<div id = "welcome_text" style="top: 120px; left: 520px;">
		Enter your information.
	</div>

	<div id = "outer_container">
		<div id = "inner_container">
			<div id = "login_container">
				Username
				<br>
					<input type="text" class="box" name="<%= stringConstants.USERNAME%>" placeholder="Username" id = "<%=stringConstants.USERNAME%>">
				<br>
				Password
				<br>
					<input type="password" class="box" name="<%= stringConstants.PASSWORD%>" placeholder="Password" id = "<%=stringConstants.PASSWORD%>">
				<br><br>
				Select A Pet
				<div id="outer">
					<img id="petImage1" src="../images/pet1.png" style="float: left; width: 30%; margin-right: 1%; margin-bottom: 0.5em;"/>
					<img id="petImage2" src="../images/pet2.png" style="float: left;width: 30%; margin-right: 1%; margin-bottom: 0.5em;"/>
					<img id="petImage3" src="../images/pet3.png" style="float: left;width: 30%; margin-right: 1%; margin-bottom: 0.5em;"/>
					<input type="hidden" name="<%=stringConstants.PETIMAGEURL %>" id="tableTextId" />
				</div>
				<br>
				<br>
    			Pet's Name
    			<br>
    			<input type="text" name="<%=stringConstants.PETNAME %>" id="<%=stringConstants.PETNAME %>" placeholder="Pet's Name">
				<br>
				<input class="button" type="submit" value="Sign Up" class="entry" onclick = "return errorCheck('<%=stringConstants.SIGN_UP_SERVLET %>', '<%=stringConstants.HOME_PAGE_JSP %>', ['<%=stringConstants.USERNAME %>', '<%=stringConstants.PASSWORD%>', '<%=stringConstants.PETNAME%>', '<%=stringConstants.PETIMAGEURL %>'], 4, 'error_message')">
			</div>
			<div id = "error_message" class=error_message></div>
		</div>
	</div>	
</body>
<script>
	var tempElement = document.getElementById("petImage1");
	if(tempElement){
		tempElement.addEventListener("click", handleClick);
	}
	var tempElement1 = document.getElementById("petImage2");
	if(tempElement1){
		tempElement1.addEventListener("click", handleClick1);
	}
	var tempElement2 = document.getElementById("petImage3");
	if(tempElement2){
		tempElement2.addEventListener("click", handleClick2);
	}
</script>
</html>