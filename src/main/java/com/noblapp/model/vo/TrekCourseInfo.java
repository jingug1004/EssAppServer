package com.noblapp.model.vo;

import com.noblapp.model.support.Model;

public class TrekCourseInfo extends Model {
	private int tc_id;
	private String course_name;
	private int sector_count;
	private int status;

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
	public int getSector_count() {
		return sector_count;
	}
	public void setSector_count(int sector_count) {
		this.sector_count = sector_count;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
