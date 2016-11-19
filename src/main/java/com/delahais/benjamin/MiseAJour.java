package com.delahais.benjamin;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
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
	protected HashMap<String, String> reste;
	
	public MiseAJour(Session userSession, String userid) {
		
		this.userSession = userSession;
		this.userid = userid;
		
		jours= new HashMap<>();
		jours.put("en", "days");jours.put("fr", "jours");jours.put("de", "Tage");jours.put("es", "días");
		jours.put("pt", "dias");jours.put("ar", "أيام");jours.put("zh", "天");jours.put("ja", "日");
		jours.put("it", "giorni");
		
		heures= new HashMap<>();
		heures.put("en", "hours");heures.put("fr", "heures");heures.put("de", "Stunden");heures.put("es", "horas");
		heures.put("pt", "horas");heures.put("ar", "ساعات");heures.put("zh", "点钟");heures.put("ja", "時");
		heures.put("it", "ore");
		
		minutes= new HashMap<>();
		minutes.put("en", "minutes");minutes.put("fr", "minutes");minutes.put("de", "Minuten");minutes.put("es", "minutos");
		minutes.put("pt", "minutos");minutes.put("ar", "دقيقة");minutes.put("zh", "分钟");minutes.put("ja", "分");
		minutes.put("it", "minuti");
		
		secondes= new HashMap<>();
		secondes.put("en", "seconds");secondes.put("fr", "secondes");secondes.put("de", "Sekunden");secondes.put("es", "segundos");
		secondes.put("pt", "segundos");secondes.put("ar", "ثواني");secondes.put("zh", "秒");secondes.put("ja", "秒");
		secondes.put("it", "secondi");
		
		reste = new HashMap<>();
		reste.put("en", "left");reste.put("fr", "restants");reste.put("de", "Rest");reste.put("es", "restantes");
		reste.put("pt", "restantes");reste.put("ar", "المتبقية");reste.put("zh", "剩馀");reste.put("ja", "残り");
		reste.put("it", "rimanenti");
	}

	@Override
	public void run() {
		
		JSONObject json = new JSONObject();
		JSONArray jList = new JSONArray();
		
		try {
			for(Compteur c: CountDown.getItSelf().getCompteurs(userid)){
				jList.put(c.toJSON(diff(c.getDeadLine(), c.getLangue(), c.getLocale())));
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

	private String diff(String theDate, String lang, String locale){
		String pattern = "dd/MM/yyyy HH:mm:ss";
		Date d2 = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setTimeZone(TimeZone.getTimeZone(locale));
			d2 = sdf.parse(theDate);
		} catch (ParseException e) {
			return "server error...";
		}
		Date d1 = new Date();

		long diff = d2.getTime() - d1.getTime();

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		
		return (lang.equals("ar")?reste.get(lang)+" ":"")+diffDays+" "+jours.get(lang)
				+" "+diffHours+" "+heures.get(lang)+" "+diffMinutes+" "+minutes.get(lang)
				+" "+diffSeconds+" "+secondes.get(lang)+" "+(!lang.equals("ar")?reste.get(lang):"");
		
	}
	
}
