package com.delahais.benjamin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class Compteur {

	protected int id;
	protected String name;
	protected String deadLine;
	protected String locale;
		
	public Compteur(int id, String name, String deadLine, String locale) {
		super();
		this.id = id;
		this.name = name;
		this.deadLine = deadLine;
		this.locale =  locale;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public String getLoc() throws ParseException{
		
		String pattern = "dd/MM/yyyy HH:mm:ss";
		Date date = new SimpleDateFormat(pattern).parse(deadLine);
		Locale l = new Locale(locale);
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.FULL, l);
		return dateFormat.format(date);
	}
	
	public JSONObject toJSON(String diff) throws JSONException, ParseException{
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("name", this.name);
		json.put("deadline", getLoc());
		json.put("locale", locale);
		if (diff != null) { json.put("diff", diff); };
		return json;
	}
	
	public static Compteur JSONtoCompteur(int id, String str){
		JSONObject json = new JSONObject(str);
		return new Compteur(id, json.getString("name"),json.getString("deadline"), json.getString("locale"));
	}
	
	public String toString(){
		return id+" "+name+" "+deadLine+" "+locale;
	}
}
