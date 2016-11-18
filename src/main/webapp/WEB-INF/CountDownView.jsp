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
	
	
	<script src="https://use.fontawesome.com/d608202644.js"></script>
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
			background-color: #DDD;
		}
		
		tr.pair{
			background-color : #EEE;
		}
		
		tr.impair{
			background-color : #CCC;
		}
		
		tr.pair a{
			color : #EEE;
			background-color : #F00;
		}
		
		tr.pair a:hover { background-color : #E00 }
		tr.impair a:hover { background-color : #E00 }
		tr.impair a{
			color: #EEE;
			background-color : #F00;
		}
		
	</style>
	<script>
		var socket = new WebSocket("ws://<% out.print(request.getAttribute("ip"));%>:8080/Countdown/cws");
		<% out.print("var userid = \""+request.getAttribute("userid")+"\""); %>
		
		socket.onopen = function(e){
			console.log(e);
			socket.send("start-"+userid);
		} /*on "écoute" pour savoir si la connexion vers le serveur websocket s'est bien faite */
		
		socket.onmessage = function(e){
			//on modifie les compteurs affichés
			var json = JSON.parse(e.data);
			json.compteurs.forEach( function (c) {
				if(c.diff !== 'undefined'){
					document.getElementById(c.id).innerHTML = "<td>"+c.name+"</td>"
					+"<td>"+c.deadline+"</td>"
					+"<td>"+c.diff+"</td>"
					+"<td><a href=\"#\" name=\""+userid+"-"+c.id+"\"class=\"button\"" 
					+"onclick=\"supprCompteur(this)\"><i class=\"fa fa-times fa-3x\"></i></a></td>";
				}
			});
		}
		
		socket.onclose = function(e){} 
		socket.onerror = function(e){alert('Connexion lost !');location.reload();}
		
		function nouveauCompteur(){
			var json = {
					name : document.getElementById("name").value, 
					deadline : document.getElementById("dpt").value,
				};
				
			socket.send(userid+"£"+JSON.stringify(json));
			location.reload();
		}
		
		function supprCompteur(elem){
			socket.send("delete-"+elem.name);
			console.log(elem.name);
			location.reload();
		}
	</script>
</head>

<body>
<% 
	ArrayList<Compteur> al = (ArrayList<Compteur>)request.getAttribute("compteurs");
	int i = 0;
	if(al.size() > 0){
		out.print("<div class=\"row\"><div class=\"small-12 columns\"><table>");
		for(Compteur c : al){
			
			out.println("<tr id=\""+ c.getId() +"\" class=\""+ ((i%2==0)?"pair":"impair") +"\">"
			+"<td>"+c.getName()+"</td>"
			+"<td>"+c.getDeadLine()+"</td>"
			+"</tr>");
			i++;
		}
		out.print("</table></div></div>");
	}
%>
	<div class="row"><div class="small-12 columns">
		<table><tr>
			<td><input type="text" placeholder="name" id="name"/></td>
			<td><input type="text" class="span2" value="<% out.print(request.getAttribute("date")); %>" id="dpt" /></td>
			<td><select>
				  <option value="fr">France</option>
				  <option value="en">USA</option>
			</select></td>
			<td><a href="#" style="color:#FFF" onclick ="nouveauCompteur()" class="button"><i class="fa fa-plus fa-3x"></i></a></td>
		</tr></table>
	</div></div>
	
<script>
$(function(){
  $('#dpt').fdatepicker({
		format: 'dd/mm/yyyy hh:ii:ss',
		disableDblClickSelection: true,
		language: 'vi',
		pickTime: true
	});
});
</script>
</body>
</html>
