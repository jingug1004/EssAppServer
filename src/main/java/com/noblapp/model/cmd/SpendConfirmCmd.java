package com.noblapp.model.cmd;


import java.util.Date;

import com.noblapp.model.support.AbstractCmd;

public class SpendConfirmCmd extends AbstractCmd {
	private int us_id;
	private int status;
	private Date commit_time;

	public int getUs_id() {
		return us_id;
	}
	public void setUs_id(int us_id) {
		this.us_id = us_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCommit_time() {
		return commit_time;
	}
	public void setCommit_time(Date commit_time) {
		this.commit_time = commit_time;
	}
}
