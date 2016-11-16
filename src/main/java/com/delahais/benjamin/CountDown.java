package com.delahais.benjamin;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CountDown extends HttpServlet {
	
	private WebSocketServer connector;
	private static CountDown CD;
	private HashMap<String, ArrayList<Cookie>> usersCookies;
	private HashMap<String, ArrayList<Compteur>> users;
	private SecureRandom random = new SecureRandom();

	public CountDown() {
		
		connector = new WebSocketServer();
		
		users = new HashMap<String,ArrayList<Compteur>>();
		
		usersCookies = new HashMap<String,ArrayList<Cookie>>(); 
		
		CD = this;
	}

	public ArrayList<Compteur> getCompteurs(String user){
		return users.get(user);
	}
	
	public void addCompteur(String user, String json){
		
		Compteur cpt = Compteur.JSONtoCompteur(json);
		
		Cookie cookie = new Cookie("compteurs"+cpt.getId(), cpt.toJSON(null).toString());
		cookie.setMaxAge(60*60*24*365); //Store cookie for 1 year
		usersCookies.get(user).add(cookie);
		
		System.out.println("creation du nouveau compteur");
		users.get(user).add(cpt);
	}
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response )
		throws ServletException, IOException {
			
		String userId = "";
		Cookie [] cooks = request.getCookies();
		//Si des cookies existent, on les lit
		if(cooks != null && cooks.length >= 2){
			userId = cooks[1].getValue();
			for(int i = 2; i<cooks.length;i++){
				users.get(userId).add(Compteur.JSONtoCompteur(cooks[i].getValue()));
			}
		//Sinon c'est que c'est la première connection du client
		} else {
			userId = nextSessionId();
			users.put(userId, new ArrayList<Compteur>());
			usersCookies.put(userId, new ArrayList<Cookie>());
			response.addCookie(new Cookie("userId", userId));
		}
				
		//Pour tous cookies préparés on les envoie au navigateur
		if(usersCookies.get(userId) != null){
			for(Cookie c : usersCookies.get(userId)){
				System.out.println(c);
				response.addCookie(c);
			}
			usersCookies.get(userId).clear();
		}
		
		request.setAttribute("compteurs", users.get(userId));
		request.setAttribute("userId", userId);
		request.setAttribute("ip", InetAddress.getLocalHost().getHostAddress());
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/CountDownView.jsp" ).forward( request, response );
	}
	
	public static CountDown getItSelf(){
		
		return CD;
	}
	
	 public String nextSessionId() {
		    return new BigInteger(130, random).toString(32);
		  }
}
