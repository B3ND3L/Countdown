package com.delahais.benjamin;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;

import javax.websocket.Session;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONStringer;


public class MiseAJour extends TimerTask {
	
	protected Session userSession;
	protected String userid;
	protected ArrayList<Compteur> compteurs;
	
	protected HashMap<String, String> jours;
	protected HashMap<String, String> heures;
	protected HashMap<String, String> minutes;
	protected HashMap<String, String> secondes;
	
	public MiseAJour(Session userSession, String userid) {
		
		this.userSession = userSession;
		this.userid = userid;
		
		jours= new HashMap<>();
		jours.put("en", "days");jours.put("fr", "jours");jours.put("de", "Tage");jours.put("es", "días");
		jours.put("pt", "dias");jours.put("ar", "أيام");jours.put("zh", "天");jours.put("ja", "日");
		jours.put("it", "giorni");
		
		heures= new HashMap<>();
		heures.put("en", "hours");heures.put("fr", "heures");heures.put("de", "Stunden");heures.put("es", "días");
		heures.put("pt", "dias");heures.put("ar", "أيام");heures.put("zh", "小时");heures.put("ja", "営業時間");
		heures.put("it", "giorni");
		
		minutes= new HashMap<>();
		minutes.put("en", "minutes");minutes.put("fr", "minutes");minutes.put("de", "Minuten");minutes.put("es", "días");
		minutes.put("pt", "dias");minutes.put("ar", "أيام");minutes.put("zh", "分钟");minutes.put("ja", "分");
		minutes.put("it", "giorni");
		
		secondes= new HashMap<>();
		secondes.put("en", "seconds");secondes.put("fr", "secondes");secondes.put("de", "Sekunden");secondes.put("es", "días");
		secondes.put("pt", "dias");secondes.put("ar", "أيام");secondes.put("zh", "秒");secondes.put("ja", "秒");
		secondes.put("it", "giorni");
		
	}

	@Override
	public void run() {
		
		JSONObject json = new JSONObject();
		JSONArray jList = new JSONArray();
		
		try {
			for(Compteur c: CountDown.getItSelf().getCompteurs(userid)){
				jList.put(c.toJSON(diff(c.getDeadLine(), c.getLocale())));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		json.put("compteurs", jList);
						
		userSession.getAsyncRemote().sendText(json.toString());
	}

	private String diff(String theDate, String lang){
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
		return diffDays+" "+jours.get(lang)+" "+diffHours+" "+heures.get(lang)+" "+diffMinutes+" minute(s) "+diffSeconds+" seconde(s)";

	}
	
}
