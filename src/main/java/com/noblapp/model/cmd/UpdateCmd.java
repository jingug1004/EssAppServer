package com.noblapp.model.cmd;

import java.sql.Date;

import com.noblapp.model.support.AbstractCmd;

/**
 * @author juniverse
 */
public class UpdateCmd extends AbstractCmd {
	private String name;
	private int gender;			// 1: male, 0: female
	private Date birth;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public int getGender() {
		return this.gender;
	}
	
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	public Date getBirth() {
		return this.birth;
	}
}
