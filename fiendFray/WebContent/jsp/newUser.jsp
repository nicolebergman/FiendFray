<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Start Page</title>
<link rel="stylesheet" type="text/css" href="css1.css" />
<script>
	function validate() {
		var xhttp = new XMLHttpRequest();
		xhttp.open("GET", "validateSignup.jsp?fullname=" + document.usersignup.fullname.value + "&username=" +document.usersignup.username.value+ "&password=" +document.usersignup.password.value+ "&imageURL=" +document.usersignup.imageURL.value, false);
		xhttp.send();
		if (xhttp.responseText.trim().length > 0) {
			document.getElementById("error").innerHTML = xhttp.responseText;
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<div class="wrapper">
      <div id="title">
      	<h1>Fiend Fray</h1>
      </div>
      <div id="extra">
      	<p>Enter your information.</p>
      </div>
      <div id="inputStart">
      	<div id="input2">
      	  <form name="usersignup" method="GET" action="newUser" onsubmit="return validate();">
      	  <p></p><br />
      	  <input type="text" name="fullname" placeholder="Full Name" style="width:300px"/>
      	  <p></p><br />
      	  <input type="text" name="username" placeholder="Username" style="width:300px"/><br />
      	  <p></p><br />
		  <input type="password" name="password" placeholder="Password" style="width:300px"/><br />
		  <p></p><br />
		  <input type="text" name="imageURL" placeholder="Image URL" style="width:300px"/><br />
		  <p></p><br />
		  <input type="submit" name="submit" value="Signup" style="height:250px; width:305px; text-align: center"/>
		  </form>
		  <div id="error"></div>
      	</div>
      </div>
	</div>
</body>
</html>