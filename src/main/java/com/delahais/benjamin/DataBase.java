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
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.conn = DriverManager.getConnection("jdbc:mysql://localhost/Countdown?user=Countdown");
		stat = conn.createStatement();
	}
		
	public void insert(String userid, Compteur compteur) throws SQLException{
		PreparedStatement prep = conn.prepareStatement(
		        "insert into compteurs values (?, ?, ?, ?, ?, ?);");
		
		prep.setString(1, userid);
		prep.setInt(2, compteur.getId());
		prep.setString(3, compteur.getName());
		prep.setString(4, compteur.getDeadLine());
		prep.setString(5, compteur.getLangue());
		prep.setString(6, compteur.getLocale());
		prep.executeUpdate();
	}
	
	public void close() throws SQLException {
		conn.close();
	}
	
	public ArrayList<Compteur> requeteCapteur(String userId) throws SQLException{
		
		ArrayList<Compteur> al = new ArrayList<Compteur>();
	    ResultSet rs = stat.executeQuery("select id, name, deadline, langue, locale from compteurs where userid='"+userId+"';");
	    
	    while (rs.next()) {
	       al.add(new Compteur(rs.getInt("id"), rs.getString("name"), rs.getString("deadLine"), rs.getString("langue"), rs.getString("locale")));
	    }
	    rs.close();
		return al;
	}

	public int getMaxId(String userid) throws SQLException{
		ResultSet rs = stat.executeQuery("select max(id) from compteurs where userid='"+userid+"';");
		int i = 0; 
		while (rs.next()) {
		      i = rs.getInt("Max(id)");
		 }
		 rs.close();
		return i;
	}
	
	public void removeCompteur(String userid, String id) throws SQLException {
			String sql = "DELETE FROM compteurs WHERE userid=? AND id=?;";
			PreparedStatement prep = conn.prepareStatement(sql);
			
			prep.setString(1, userid);
			prep.setInt(2, Integer.parseInt(id));
			prep.executeUpdate();
	}
}
