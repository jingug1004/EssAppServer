package com.noblapp.model.vo;

import java.sql.Date;

import com.noblapp.model.support.Model;

public class TrekFameInfo extends Model {
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public Date getIssued_time() {
		return issued_time;
	}
	public void setIssued_time(Date issued_time) {
		this.issued_time = issued_time;
	}

	private int uid;
	private int fid;
	private Date issued_time;
}
