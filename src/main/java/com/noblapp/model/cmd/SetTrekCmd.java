package com.noblapp.model.cmd;

import java.sql.Time;

public class SetTrekCmd extends TrekCourseCmd {

	public Time getIssued_date() {
		return issued_date;
	}
	public void setIssued_date(Time issued_date) {
		this.issued_date = issued_date;
	}
	public Time getIssued_time() {
		return issued_time;
	}
	public void setIssued_time(Time issued_time) {
		this.issued_time = issued_time;
	}
	public String getRecent_sector_class() {
		return recent_sector_class;
	}
	public void setRecent_sector_class(String recent_sector_class) {
		this.recent_sector_class = recent_sector_class;
	}
	public String getChange_course() {
		return change_course;
	}
	public void setChange_course(String change_course) {
		this.change_course = change_course;
	}
	public String getSkip() {
		return skip;
	}
	public void setSkip(String skip) {
		this.skip = skip;
	}

	private Time issued_date;
	private Time issued_time;
	private String recent_sector_class;
	private String change_course;
	private String skip;
}

