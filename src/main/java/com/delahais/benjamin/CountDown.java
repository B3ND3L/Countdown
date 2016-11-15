package com.delahais.benjamin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CountDown extends HttpServlet {
	
	private WebSocketServer connector;
	private ArrayList<Compteur> al;
	private static CountDown CD;
	
	public CountDown() {
		
		connector = new WebSocketServer();
		al = new ArrayList<Compteur>();
		
		CD = this;
	}

	public ArrayList<Compteur> getCompteurs(){
		return al;
	}
	
	public void addCompteur(String json){
		al.add(new Compteur(6, "caca", "31/06/2017 12:50:34"));
		//al.add(Compteur.JSONtoCompteur(json));
	}
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response )
		throws ServletException, IOException {
		
		request.setAttribute("compteurs", al);
		
		//Cookie [] cookies = request.getCookies();
				
		this.getServletContext().getRequestDispatcher( "/WEB-INF/CountDownView.jsp" ).forward( request, response );
	}
	
	public static CountDown getItSelf(){
		
		return CD;
	}
}
