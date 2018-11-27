package com.noblapp.model.cmd;

import com.noblapp.model.support.AbstractCmd;

/**
 * @author juniverse
 */
public class JoinCmd extends AbstractCmd {
	private String id;
	private String password;
	private String name;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
