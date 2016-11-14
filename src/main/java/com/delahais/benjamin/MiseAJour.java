package com.delahais.benjamin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;


public class MiseAJour extends TimerTask {
	
	protected WebSocketServer connector;
	protected ArrayList<Compteur> compteurs;
	
	public MiseAJour(WebSocketServer connector, ArrayList<Compteur> compteurs) {
		
		this.connector = connector;
		this.compteurs = compteurs;
	}

	@Override
	public void run() {
				
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
