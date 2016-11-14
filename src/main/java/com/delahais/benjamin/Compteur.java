package com.delahais.benjamin;

public class Compteur {

	protected int id;
	protected String name;
	protected String diff;
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
}
