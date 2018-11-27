package com.noblapp.model.cmd;

import com.noblapp.model.support.AbstractCmd;

public class StoreCmd extends AbstractCmd {
	private int sid;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
}
