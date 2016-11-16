package com.delahais.benjamin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {
	
	protected Connection conn;
	protected Statement stat;
	
	public DataBase() throws SQLException, ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		this.conn = DriverManager.getConnection("jdbc:sqlite:compteurs.db");
		stat = conn.createStatement();
			
		stat.executeUpdate("drop table if exists compteurs;");
		stat.executeUpdate("create table compteurs (userId, id, name, deadline);");	    
	}
	
	public void insert(String userId, String id, String nom, String deadline) throws SQLException{
		PreparedStatement prep = conn.prepareStatement(
		        "insert into compteurs values (?, ?, ?, ?);");
		
		prep.setString(1, userId);
		prep.setString(2, id);
		prep.setString(3, nom);
		prep.setString(4, deadline);
		prep.addBatch();
		
		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);
	}
	
	public void close() throws SQLException {
		conn.close();
	}

	public String getMaxId(){
		return "";
	}
	
	public ArrayList<Compteur> requeteCapteur(String userId) throws SQLException{
		
		ArrayList<Compteur> al = new ArrayList<Compteur>();
	    ResultSet rs = stat.executeQuery("select id, name, deadline from compteurs where userId='"+userId+"';");
	    
	    
	    while (rs.next()) {
	       al.add(new Compteur(rs.getString("name"), rs.getString("deadLine")));
	    }
	    rs.close();
	    
		return null;
	}
}