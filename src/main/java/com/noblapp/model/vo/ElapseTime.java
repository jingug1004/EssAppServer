package com.noblapp.model.vo;

import java.util.Date;

import com.noblapp.model.support.Model;

public class ElapseTime extends Model {
	private long log_server_elapse_sn;
	private String path;
	private int elapsed_time;
	private Date elapse_reg_dt;

	public long getLog_server_elapse_sn() {
		return log_server_elapse_sn;
	}

	public void setLog_server_elapse_sn(long log_server_elapse_sn) {
		this.log_server_elapse_sn = log_server_elapse_sn;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getElapsed_time() {
		return elapsed_time;
	}

	public void setElapsed_time(int elapsed_time) {
		this.elapsed_time = elapsed_time;
	}

	public Date getElapse_reg_dt() {
		return elapse_reg_dt;
	}

	public void setElapse_reg_dt(Date elapse_reg_dt) {
		this.elapse_reg_dt = elapse_reg_dt;
	}

}
