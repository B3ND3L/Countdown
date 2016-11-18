package com.delahais.benjamin;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.sql.SQLException;
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
	private DataBase db;
	private SecureRandom random = new SecureRandom();

	public CountDown() throws ClassNotFoundException, SQLException {
		
		connector = new WebSocketServer();
		db = new DataBase();
		CD = this;
	}

	public ArrayList<Compteur> getCompteurs(String user) throws SQLException{
		//SELECTION BASE DE DONNEE
		return db.requeteCapteur(user);
	}
	
	public void addCompteur(String user, String json) throws SQLException{
		
		Compteur cpt = Compteur.JSONtoCompteur(json);
		//Envoi du compteur dans la BDD
		db.insert(user, cpt);
	}
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response )
		throws ServletException, IOException {
			
		String userId = "";
		Cookie [] cooks = request.getCookies();
		//Si des cookies existent, on les lit
		if(cooks != null && cooks.length >= 2){
			userId = cooks[1].getValue();
		//Sinon c'est que c'est la première connection du client
		} else {
			userId = nextSessionId();
			response.addCookie(new Cookie("userId", userId));
		}
		
		//RECUPERATION DES COMPTEURS PAR LA BDD
		try {
			request.setAttribute("compteurs", getCompteurs(userId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("userid", userId);
		request.setAttribute("ip", InetAddress.getLocalHost().getHostAddress());
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/CountDownView.jsp" ).forward( request, response );
	}
	
	public static CountDown getItSelf(){
		
		return CD;
	}
	
	 public String nextSessionId() {
		 String s = new BigInteger(130, random).toString(32);
		 s = s.substring(0, 10);
		 return s;
	 }
}
