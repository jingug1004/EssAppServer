package com.noblapp.model.cmd;

import com.noblapp.model.support.AbstractCmd;

public class MapInfoCmd extends AbstractCmd {
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
