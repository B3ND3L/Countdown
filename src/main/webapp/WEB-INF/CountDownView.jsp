<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, com.delahais.benjamin.Compteur"  %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>CountDown</title>
	
	<link href="https://cdn.jsdelivr.net/foundation/6.2.4/foundation.min.css" rel="stylesheet">
	<link href="https://netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/foundicons/3.0.0/foundation-icons.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/foundation-datepicker/1.5.5/css/foundation-datepicker.min.css" rel="stylesheet">
	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/foundation/6.2.4/foundation.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/foundation-datepicker/1.5.5/js/foundation-datepicker.min.js"></script>
	
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
			var json = JSON.parse(e.data);
			json.compteurs.forEach( function (c) {
				if(c.diff !== 'undefined'){
					document.getElementById(c.id).innerHTML = "<td>"+c.name+"</td><td>"+c.deadline+"</td><td>"+c.diff+"</td>";
				}
			});
		} 
		
		socket.onclose = function(e){} 
		
		socket.onerror = function(e){}
		
		function nouveauCompteur(){
			var json = {
					id : document.getElementById("id").value,
					name : document.getElementById("name").value, 
					deadline : document.getElementById("dpt").value,
				};
				
			socket.send(json);
				
			//Create cookies
			document.cookie = "compteurs="+JSON.stringify(json); 
			console.log(document.cookie);
		}
				
	</script>
</head>

<body>
<% 
	ArrayList<Compteur> al = (ArrayList<Compteur>)request.getAttribute("compteurs");
	int i = 0;
	for(Compteur c : al){
		
		out.println("<div class=\"row\"><div class=\"small-10 columns\">"
		+"<table><tr id=\""+ c.getId() +"\" style=\"background-color:"+ ((i%2==0)?"#EEE":"#CCC") +"\"><td>"+c.getName()+"</td><td>"+c.getDeadLine()+"</td></tr></table>"
		+"</div></div>");
		i++;
	}
%>
	<div class="row">
		<div class="small-2 columns">
			<input type="text" placeholder="id" id="id" />
		</div>
		<div class="small-3 columns">
			<input type="text" placeholder="name" id="name"/>
		</div>
		<div class="small-3 columns">
			<input type="text" class="span2" value="<% out.print(request.getAttribute("date")); %>" id="dpt" />
		</div>
		<div class="small-2 columns">
			<a href="#" onclick ="nouveauCompteur()" class="button">Nouveau</a>
		</div>
		<div class="small-2 columns">
		</div>
	</div>
	
<script>
$(function(){
  $('#dpt').fdatepicker({
		format: 'mm-dd-yyyy hh:ii',
		disableDblClickSelection: true,
		language: 'vi',
		pickTime: true
	});
});
</script>
</body>
</html>
