<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, com.delahais.benjamin.Compteur"  %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>CountDown</title>
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/foundation/6.2.4/foundation.min.css">
	<script src="https://cdn.jsdelivr.net/foundation/6.2.4/foundation.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	
	<style>
		blockquote {
			font-style: italic;
			padding: 20px;
		}
	
		blockquote footer{
			color:#555;
			font-weight: bold
		}
		body{
			background-color: #AAA;
		}
	</style>
	<script>
		var socket = new WebSocket("ws://localhost:8080/Countdown/cws");
		
		socket.onopen = function(e){
			console.log(e);
		} /*on "écoute" pour savoir si la connexion vers le serveur websocket s'est bien faite */
		
		socket.onmessage = function(e){
			//on modifie les compteurs affichés
			console.log("NEW MESSAGE")
			console.log(e);
		} 
		
		socket.onclose = function(e){} 
		
		socket.onerror = function(e){}
		
	</script>
</head>

<body>

<% 
	ArrayList<Compteur> al = (ArrayList<Compteur>)request.getAttribute("compteurs");
	int i = 0;
	for(Compteur c : al){
		
		out.println("<div class=\"row\"><div class=\"small-12 columns\">"
		+"<table><tr style=\"background-color:"+ ((i%2==0)?"#EEE":"#CCC") +"\"><td>"+c.getName()+"</td><td>"+c.getDeadLine()+"</td></tr></table>"
		+"</div></div>");
		i++;
	}
%>
</body>
</html>
