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
	<link href="myAwesomeStyle.css" rel="stylesheet">
	
	<script src="https://use.fontawesome.com/d608202644.js"></script>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/foundation/6.2.4/foundation.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/foundation-datepicker/1.5.5/js/foundation-datepicker.min.js"></script>
	<script>
		var socket = new WebSocket("ws://<% out.print(request.getAttribute("ip"));%>:8080/Countdown/cws");
		<% out.print("var userid = \""+request.getAttribute("userid")+"\""); %>
		
		var langues = { "Français":"fr","English":"en","Deutsch":"de","Español":"es","العربية":"ar",
				"Italiano":"it","português":"pt","中国":"zh","日本の":"ja"};
				
		socket.onopen = function(e){
			console.log(e);
			socket.send("start-"+userid);
		} /*on "écoute" pour savoir si la connexion vers le serveur websocket s'est bien faite */
		
		socket.onmessage = function(e){
			//on modifie les compteurs affichés
			var json = JSON.parse(e.data);
			json.compteurs.forEach( function (c) {
				document.getElementById(c.id).innerHTML = "<td>"+c.name+"</td>"
				+"<td>"+c.deadline+"</td>"
				+"<td>"+c.locale+"</td>"
				+"<td>"+c.diff+"</td>"
				+"<td>"+getPays(c.langue)+"</td>"
				+"<td><a href=\"#\" name=\""+userid+"-"+c.id+"\"class=\"button\"" 
				+"onclick=\"supprCompteur(this)\"><i class=\"fa fa-times fa-2x\"></i></a></td>";
			});
		}
		
		socket.onclose = function(e){} 
		socket.onerror = function(e){alert('Connexion lost !');location.reload();}
		
		function getPays(langue){
			for(var pays in langues){
				if (langues[pays] === langue) return pays;
			}
		}
		
		function nouveauCompteur(){
			var json = {
					name : document.getElementById("name").value, 
					deadline : document.getElementById("dpt").value,
					langue : document.getElementById("langue").value,
					locale : document.getElementById("locale").value,
				};
			socket.send(userid+"£"+JSON.stringify(json));
			setTimeout(function(){location.reload();}, 250);
		}
		
		function supprCompteur(elem){
			socket.send("delete-"+elem.name);
			console.log(elem.name);
			location.reload();
		}
	</script>
</head>

<body>
	<div class=\"row\">
		<div class=\"small-12 columns\">
			<table>
				<tr>
					<th>Name</th>
					<th>DeadLine</th>
					<th>Fuseau Horaire</th>
					<th>Countdown</th>
					<th>Langue</th>
					<th>Close</th>
				</tr>
<% 
	ArrayList<Compteur> al = (ArrayList<Compteur>)request.getAttribute("compteurs");
	int i = 0;
	for(Compteur c : al){
		out.println("<tr id=\""+ c.getId() +"\" class=\""+ ((i%2==0)?"pair":"impair") +"\">"
		+"<td hidden=\"true\">"+c.getName()+"</td>"
		+"<td hidden=\"true\">"+c.getDeadLine()+"</td>"
		+"<td hidden=\"true\">"+c.getLocale()+"</td>"
		+"<td hidden=\"true\">"+c.getLangue()+"</td>"
		+"</tr>");
		i++;
	}
%>
			</table>
		</div>
	</div>
	<div class="row"><div class="small-12 columns">
		<table><tr>
			<td><input type="text" placeholder="name" id="name"/></td>
			<td><input type="text" class="span2" value="<% out.print(request.getAttribute("date")); %>" id="dpt" /></td>
			<td><select id="langue">
				<option value="fr">Choisir une langue</option>
			</select></td>
			<td><select id="continent" onchange="show()">
				<option value="none">Choisir un continent</option>
<%
	String prevName = "";
	for(String f : (String[])request.getAttribute("fuseaux")){
		String[] mode = f.split("/");
		if(mode.length == 2 && (!(mode[0].equals("Etc")||mode[0].equals("SystemV"))) && (!mode[0].equals(prevName))){
			out.print("<option>"+mode[0]+"</option>");
			prevName = mode[0];
		}
	}
%>
			</select></td>
			<td><select id="locale" disabled>
				<option>Choisir un fuseau horaire</option>
			</select></td>
			<td><a href="#" style="color:#FFF" onclick ="nouveauCompteur()" class="button"><i class="fa fa-plus fa-2x"></i></a></td>
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
	
	for(var pays in langues)
	{
	 	var loc = langues[pays];
	 	document.getElementById("langue").innerHTML += '<option value='+loc+'>'+pays+'</option>';
	}
<%
	prevName = "";
	for(String f : (String[])request.getAttribute("fuseaux")){
		String[] mode = f.split("/");
		if(mode.length == 2 && (!(mode[0].equals("Etc")||mode[0].equals("SystemV"))) && (!mode[0].equals(prevName))){
			out.print("var "+mode[0]+"=[];");
			prevName = mode[0];
		}
	}
	
	for(String f : (String[])request.getAttribute("fuseaux")){
		String[] mode = f.split("/");
		if(mode.length == 2 && (!(mode[0].equals("Etc")||mode[0].equals("SystemV")))){
			out.print(mode[0]+".push(\""+mode[mode.length-1]+"\");");
		}
	}
	
	out.print("var arrayFuseaux={");
	prevName = "";
	for(String f : (String[])request.getAttribute("fuseaux")){
		String[] mode = f.split("/");
		if(mode.length == 2 && (!(mode[0].equals("Etc")||mode[0].equals("SystemV"))) && (!mode[0].equals(prevName))){
			out.print("\""+mode[0]+"\":"+mode[0]+",");
			prevName=mode[0];
		}
	}
	out.print("};");
%>
function show(){
	var continent = document.getElementById("continent");
	var select = document.getElementById("locale");
	if(continent.value === 'none'){
		select.disabled = true;
	} else {
		select.innerHTML = "<option>Choisir un fuseau horaire</option>";
		arrayFuseaux[continent.value].forEach(function(p){
			select.innerHTML += "<option value=\""+continent.value+"/"+p+"\">"+p+"</option>";
		});
		select.disabled = false;
	}
}
</script>
</body>
</html>
