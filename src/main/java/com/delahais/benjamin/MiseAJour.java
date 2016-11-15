package com.delahais.benjamin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import javax.websocket.Session;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONStringer;


public class MiseAJour extends TimerTask {
	
	protected Session userSession;
	protected ArrayList<Compteur> compteurs;
	
	public MiseAJour(Session userSession) {
		
		this.userSession = userSession;
	}

	@Override
	public void run() {
		
		JSONObject json = new JSONObject();
		JSONArray jList = new JSONArray();
		
		for(Compteur c: CountDown.getItSelf().getCompteurs()){
			jList.put(c.toJSON(diff(c.getDeadLine())));
		}
		
		json.put("compteurs", jList);
						
		userSession.getAsyncRemote().sendText(json.toString());
	}

	private String diff(String theDate){
		String pattern = "dd/MM/yyyy HH:mm:ss";
		Date d2 = null;
		try {
			d2 = new SimpleDateFormat(pattern).parse(theDate);
		} catch (ParseException e) {
			return "server error...";
		}
		Date d1 = new Date();

		long diff = d2.getTime() - d1.getTime();

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays+" jour(s) "+diffHours+" heure(s) "+diffMinutes+" minute(s) "+diffSeconds+" seconde(s)";

	}
	
}
