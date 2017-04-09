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

</head>
<body>

	<div id = "title_container">
		Sign up
	</div>

	<div id = "welcome_text">
		Enter your information.
	</div>

	<div id = "outer_container">
		<div id = "inner_container">
			<div id = "login_container">
				Username
				<br>
				<input type="text" name="<%=stringConstants.USERNAME %>" id="<%=stringConstants.USERNAME %>" placeholder="Username">
				<br>
				Password
				<br>
				<input type="text" name="<%=stringConstants.PASSWORD %>" id="<%=stringConstants.PASSWORD %>" placeholder="Password">
				<br>
				<br>
				<div id="pets">
					<img src="../images/pet1.png" style="float: left; 
					width: 30%; margin-right: 1%; margin-bottom: 0.5em;"/>
					
					<img src="../images/pet2.png" style="float: left;
					 width: 30%; margin-right: 1%; margin-bottom: 0.5em;"/>
					 
					 <img src="../images/pet3.png" style="float: left;
					 width: 30%; margin-right: 1%; margin-bottom: 0.5em;"/>
					 
					 <div id="outer">
					 	<form action="">
					 		<div class="inner"><input type="radio" name="<%=stringConstants.PETIMAGEURL %>" value="pet1"> Pet 1</div>
				        	&nbsp;
				        	&nbsp;
				        	&nbsp;
				        	&nbsp;
				        	<div class="inner"><input type="radio" name="<%=stringConstants.PETIMAGEURL %>" value="pet2"> Pet 2</div>
				        	&nbsp;
				        	&nbsp;
				        	&nbsp;
				        	&nbsp;
				        	<div class="inner"><input type="radio" name="<%=stringConstants.PETIMAGEURL %>" value="pet3"> Pet 3</div>
					 	</form>
    				</div> 
    				<br>
    				Pet's Name
    				<br>
    				<input type="text" name="<%=stringConstants.PETNAME %>" id="<%=stringConstants.PETNAME %>" placeholder="Pet's Name">
					 
				<br>
				<div class = "submit_button">
					<!-- call the javascript function which makes an ajax call to the servlet
					we pass in the servlet name, the jsp page name of where we will navigate if there is no error,
					an array of id strings of all the parameters needed in the ajax call
					the number of parameters in the array, and finally, the id string of the error div -->
					<input type="submit" style="width:60px;height:100px;" onclick = "return errorCheck('<%=stringConstants.SIGN_UP_SERVLET %>', '<%=stringConstants.HOME_PAGE_JSP %>', ['<%=stringConstants.FULL_NAME %>', '<%=stringConstants.USERNAME %>', '<%=stringConstants.PASSWORD %>'], 3, 'errorDiv')" value="Sign Up">
				</div>
				<div id = "errorDiv" class=error_message></div>
			</div>
		</div>
	</div>
</body>
</html>