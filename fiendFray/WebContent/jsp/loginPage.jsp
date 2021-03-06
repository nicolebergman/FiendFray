<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="base.stringConstants" %>
<html>
<head>
	<title>Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
	<link href="https://fonts.googleapis.com/css?family=Lato:700i" rel="stylesheet">
	<script src="../main2.js" type="text/javascript"></script>
</head>
<body background="../images/clean-pixel-landscape.jpg">
	<img id="welcome" src="../images/welcometo.png"></img>
	<img id="fiendfray" src="../images/fiendfray.png"></img>
	
	
	<div id = "welcome_text">
		Please log in, register, or sign in as a guest:
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
				
				<div id="outer">
					<div class="inner"><input class="button" type="submit" value="Log In" class="entry" 
				  		onclick = "return errorCheck('<%=stringConstants.LOGIN_SERVLET %>', '<%=stringConstants.HOME_PAGE_JSP %>', ['<%=stringConstants.USERNAME %>', '<%=stringConstants.PASSWORD %>'], 2, 'error_message')"></div>
				 	<form action = "${pageContext.request.contextPath}/jsp/signUp.jsp">
				 		<div class="inner"><input class="button" type="submit" value="Register" class="register" ></div>
				  	</form>
				  	<div class="inner"><input class="button" type="submit" value="Log In As Guest" class="entry" 
				  		onclick = "return errorCheck('<%=stringConstants.GUEST_LOGIN_SERVLET %>', '<%=stringConstants.HOME_PAGE_JSP %>', ['<%=stringConstants.USERNAME %>', '<%=stringConstants.PASSWORD %>'], 2, 'error_message')"></div>
				</div>
				<div class=error_message id = "error_message"> </div>
			</div>
		</div>
	</div>
</body>
</html>