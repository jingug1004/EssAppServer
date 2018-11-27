package com.noblapp.model.vo;

import com.noblapp.model.support.Model;

public class StringInfo extends Model {

	private int st_id;
	private String cname;
	private String str;
	
	public int getSt_id() {
		return st_id;
	}
	public void setSt_id(int st_id) {
		this.st_id = st_id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
}
