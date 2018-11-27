package com.noblapp.model.cmd;

import java.util.Date;

public class SpendReqCmd extends StoreCmd {
	private int point;
	private Date issued_time;

	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public Date getIssued_time() {
		return issued_time;
	}
	public void setIssued_time(Date issued_time) {
		this.issued_time = issued_time;
	}
}
