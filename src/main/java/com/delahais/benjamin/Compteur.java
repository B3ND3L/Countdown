package com.delahais.benjamin;

import org.json.JSONObject;

public class Compteur {

	protected int id;
	protected String name;
	protected String deadLine;
	
	public Compteur(int id, String name, String deadLine) {
		super();
		this.id = id;
		this.name = name;
		this.deadLine = deadLine;
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
	
	public JSONObject toJSON(String diff){
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("name", this.name);
		json.put("deadline", this.deadLine);
		if (diff != null) { json.put("diff", diff); };
		return json;
	}
	
	public static Compteur JSONtoCompteur(String str){
		JSONObject json = new JSONObject(str);
		return new Compteur(json.getInt("id"),json.getString("name"),json.getString("deadline"));
	}
}
