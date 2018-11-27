package com.noblapp.model.vo;

import java.sql.Date;

import com.noblapp.model.support.Model;

public class TrekCertInfo extends Model {
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getTc_id() {
		return tc_id;
	}
	public void setTc_id(int tc_id) {
		this.tc_id = tc_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public Date getIssued_time() {
		return issued_time;
	}
	public void setIssued_time(Date issued_time) {
		this.issued_time = issued_time;
	}

	private int cid;
	private int uid;
	private int tc_id;
	private String course_name;
	private Date issued_time;
}
