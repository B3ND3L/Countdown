package com.delahais.benjamin;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
 
@ServerEndpoint(value="/cws")
public class WebSocketServer {
    
    private Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());
    private Timer t= new Timer(true);
    /**
     * Callback hook for Connection open events. This method will be invoked when a 
     * client requests for a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        userSessions.add(userSession);
    }
    
    @OnError
    public void onError(Throwable t){
    	System.out.println("BIP !");
    }
     
    /**
     * Callback hook for Connection close events. This method will be invoked when a
     * client closes a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnClose
    public void onClose(Session userSession) {
        userSessions.remove(userSession);
        t.cancel();
    }
     
    /**
     * Callback hook for Message Events. This method will be invoked when a client
     * send a message.
     * @param message The text message
     * @param userSession The session of the client
     * @throws SQLException 
     */
    @OnMessage
    public void onMessage(String message, Session userSession) throws SQLException {
    	if(message.contains("£")){
    		
    		String [] messages = message.split("£");    		
    		CountDown.getItSelf().addCompteur(messages[0], messages[1]);
    	
    	} else if(message.contains("start")) {
    	
    		String [] messages = message.split("-");
    		t.schedule(new MiseAJour(userSession, messages[1]), 1000, 1000);
    	
    	} else if(message.contains("delete")) {
    	
			String [] messages = message.split("-");
			CountDown.getItSelf().removeCompteur(messages[1], messages[2]);
    	}
    }
}
