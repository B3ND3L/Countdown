package com.delahais.benjamin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

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
		
		al.add(new Compteur(0,"Guarana","12/12/2016 05:55:12"));
		al.add(new Compteur(1,"Guacamole","12/12/2016 05:55:12"));
		
		CD = this;
	}

	public ArrayList<Compteur> getCompteurs(){
		return al;
	}
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response )
		throws ServletException, IOException {
		
		request.setAttribute( "compteurs", al);
		
		//request.getCookies();
			
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/CountDownView.jsp" ).forward( request, response );
	}
	
	public static CountDown getItSelf(){
		
		return CD;
	}
}
