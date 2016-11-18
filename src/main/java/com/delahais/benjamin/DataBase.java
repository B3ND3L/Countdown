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
		Class.forName("com.mysql.jdbc.Driver");
		this.conn = DriverManager.getConnection("jdbc:mysql://localhost/Countdown?user=username&password=password");
		stat = conn.createStatement();
			
		/*stat.executeUpdate("drop table if exists compteurs;");
		stat.executeUpdate("CREATE TABLE `Countdown`.`compteurs` "
				+ "( `userid` VARCHAR(25) NOT NULL , `id` INT NOT NULL , `name` VARCHAR(20) NOT NULL , "
				+ "`deadline` VARCHAR(20) NOT NULL ) ENGINE = InnoDB;");	*/
		
	}
	
	public void insert(String userid, int id, String nom, String deadline) throws SQLException{
		PreparedStatement prep = conn.prepareStatement(
		        "insert into compteurs values (?, ?, ?, ?);");
		
		prep.setString(1, userid);
		prep.setInt(2, id);
		prep.setString(3, nom);
		prep.setString(4, deadline);
		prep.executeUpdate();
	}
	
	public void insert(String userid, Compteur compteur) throws SQLException{
		PreparedStatement prep = conn.prepareStatement(
		        "insert into compteurs values (?, ?, ?, ?);");
		
		prep.setString(1, userid);
		prep.setInt(2, compteur.getId());
		prep.setString(3, compteur.getName());
		prep.setString(4, compteur.getDeadLine());
		prep.executeUpdate();
	}
	
	public void close() throws SQLException {
		conn.close();
	}

	public String getMaxId(){
		return "";
	}
	
	public ArrayList<Compteur> requeteCapteur(String userId) throws SQLException{
		
		ArrayList<Compteur> al = new ArrayList<Compteur>();
	    ResultSet rs = stat.executeQuery("select id, name, deadline from compteurs where userid='"+userId+"';");
	    
	    while (rs.next()) {
	       al.add(new Compteur(rs.getInt("id"), rs.getString("name"), rs.getString("deadLine")));
	    }
	    rs.close();
	    
		return al;
	}

	public void removeCompteur(String userid, String id) throws SQLException {
			String sql = "DELETE FROM compteurs WHERE userid=? AND id=?;";
			PreparedStatement prep = conn.prepareStatement(sql);
			
			prep.setString(1, userid);
			prep.setInt(2, Integer.parseInt(id));
			prep.executeUpdate();
	}
}
