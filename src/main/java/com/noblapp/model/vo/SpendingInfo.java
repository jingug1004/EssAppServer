package com.noblapp.model.vo;

import java.sql.Date;

import com.noblapp.model.support.Model;

public class SpendingInfo extends Model {
	public int getUs_id() {
		return us_id;
	}
	public void setUs_id(int us_id) {
		this.us_id = us_id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public Date getIssued_time() {
		return issued_time;
	}
	public void setIssued_time(Date issued_time) {
		this.issued_time = issued_time;
	}
	public Date getCommit_time() {
		return commit_time;
	}
	public void setCommit_time(Date commit_time) {
		this.commit_time = commit_time;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	private int us_id;
	private int uid;
	private int sid;
	private Date issued_time;
	private Date commit_time;
	private int point;
	private int status;
}
