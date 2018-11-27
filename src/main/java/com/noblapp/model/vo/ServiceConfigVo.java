package com.noblapp.model.vo;

import com.noblapp.model.support.Model;

public class ServiceConfigVo extends Model {

	private long id;
	private String name;
	private String the_value;
	private String description;
	
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getThe_value() {
		return the_value;
	}
	public int getThe_valueAsInt() throws Exception {
		return Integer.parseInt(the_value);
	}
	public double getThe_valueAsDouble() throws Exception {
		return Double.parseDouble(the_value);
	}
	public void setThe_value(String the_value) {
		this.the_value = the_value;
	}

}
