//called in loginPage.jsp and signUo.jsp
//parameters:
//servletName -- servlet that the ajax call will go to
//jspName -- window location to navigate to if there was no error
//paramArgs -- array of ids of all the parameters that need to be included in ajax request
//numArgs -- number of elements in paramArgs
//errorDivName -- id of the error div
function errorCheck (servletName, jspName, paramArgs, numArgs, errorDivName){
	console.log(servletName);
	console.log(paramArgs);
	console.log(numArgs);
	var xhttp = new XMLHttpRequest();
	//gets the path
	var path = "/"+window.location.pathname.split("/")[1];
	//create url with first parameter from paramArgs
	var url = path + servletName;
	url = url +"?"+encodeURIComponent(paramArgs[0])+"="+encodeURIComponent(document.getElementById(paramArgs[0]).value);
	url = url +"&"+encodeURIComponent(paramArgs[1])+"="+encodeURIComponent(document.getElementById(paramArgs[1]).value);
	url = url +"&"+encodeURIComponent(paramArgs[2])+"="+encodeURIComponent(document.getElementById(paramArgs[2]).value);
	url = url +"&"+encodeURIComponent(paramArgs[3])+"="+encodeURIComponent(document.getElementById('tableTextId').value);
	//send synchronous ajax call to servlet
	xhttp.open("GET", url, false);
	xhttp.send();
	//if we got a response text, there must have been an error
	if (xhttp.responseText.trim().length > 0) {
		//set the repsonse text as the innerHTML of the error div
		document.getElementById(errorDivName).innerHTML = xhttp.responseText;
	}
	else{
		//otherwise navigate to the destination jsp
		window.location.href = path + "/jsp/"+jspName;
	}
	
	return false;
	
}