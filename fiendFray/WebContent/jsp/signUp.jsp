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
	<script type="text/javascript">
  	function handleClick(clickedId) {
  		console.log(clickedId);
   		if(clickedId == "pet1") {
   			document.getElementById('tableTextId').value = "../images/pet1.png";
   		}
     	else if (clickedId == "pet2") {
     		document.getElementById('tableTextId').value = "../images/pet2.png";
     	}	
     	else document.getElementById('tableTextId').value = "../images/pet3.png";
  	}
	</script>
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
				<form name="signupform" method="GET" action="<%=stringConstants.SIGN_UP_SERVLET %>">
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
						<div id="outer">
							<label>
								<img src="../images/pet1.png" style="float: left; 
									width: 30%; margin-right: 1%; margin-bottom: 0.5em;"/>
								<div class="inner"><input type="radio" id="pet1" name="pet" 
									value = "../images/pet1.png" checked = "checked" onclick="handleClick(this.id);"></div>
							</label>
							
							<label>
								<img src="../images/pet2.png" style="float: left;
									width: 30%; margin-right: 1%; margin-bottom: 0.5em;"/>
								<div class="inner"><input type="radio" id="pet2" name="pet" 
									value = "../images/pet2.png" onclick="handleClick(this.id);" ></div>
							</label>
							
							 <label>
								 <img src="../images/pet3.png" style="float: left;
									width: 30%; margin-right: 1%; margin-bottom: 0.5em;"/>
								<div class="inner"><input type="radio" id="pet3" name="pet" 
									value = "../images/pet3.png" onclick="handleClick(this.id);"></div> 
							 </label> 
							 <input type="hidden" name="<%=stringConstants.PETIMAGEURL %>" id="tableTextId" />
						</div>
						<br>
						<br>
    					Pet's Name
    					<br>
    					<input type="text" name="<%=stringConstants.PETNAME %>" id="<%=stringConstants.PETNAME %>" placeholder="Pet's Name">
						<br>
						<div class = "submit_button">
							<input type="submit" style="width:60px;height:100px;" onclick = "return errorCheck('<%=stringConstants.SIGN_UP_SERVLET %>', '<%=stringConstants.HOME_PAGE_JSP %>', ['<%=stringConstants.USERNAME %>', '<%=stringConstants.PASSWORD %>', '<%=stringConstants.PETNAME %>'], 3, 'errorDiv')" value="Sign Up">
						</div>
					</div>
				</form>
			</div>
		</div>
		<div id = "errorDiv" class=error_message></div>
	</div>	
</body>
</html>