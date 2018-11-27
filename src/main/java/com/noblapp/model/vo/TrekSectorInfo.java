package com.noblapp.model.vo;

import java.sql.Date;

import com.noblapp.model.support.Model;

public class TrekSectorInfo extends Model {

	public int getTc_id() {
		return tc_id;
	}
	public void setTc_id(int tc_id) {
		this.tc_id = tc_id;
	}
	public int getTcs_id() {
		return tcs_id;
	}
	public void setTcs_id(int tcs_id) {
		this.tcs_id = tcs_id;
	}
	public String getSector_group_name() {
		return sector_group_name;
	}
	public void setSector_group_name(String sector_group_name) {
		this.sector_group_name = sector_group_name;
	}
	public String getSector_class() {
		return sector_class;
	}
	public void setSector_class(String sector_class) {
		this.sector_class = sector_class;
	}
	public double getLat_x() {
		return lat_x;
	}
	public void setLat_x(double lat_x) {
		this.lat_x = lat_x;
	}
	public double getLong_y() {
		return long_y;
	}
	public void setLong_y(double long_y) {
		this.long_y = long_y;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	private int tc_id;
	private int tcs_id;
	private String sector_group_name;
	private String sector_class;
	private double lat_x;
	private double long_y;
	private int status;
	private double distance;
	
	
	
	private int uid;
	private Date issued_time;
	private int point;
	private int cmplt;

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public Date getIssued_time() {
		return issued_time;
	}
	public void setIssued_time(Date issued_time) {
		this.issued_time = issued_time;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getCmplt() {
		return cmplt;
	}
	public void setCmplt(int cmplt) {
		this.cmplt = cmplt;
	}
}
