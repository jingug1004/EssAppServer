package com.noblapp.model.vo;

public class StoreInfo extends ShortStoreInfo {

	public int getMaster_uid() {
		return master_uid;
	}
	public void setMaster_uid(int master_uid) {
		this.master_uid = master_uid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getBusiness_number() {
		return business_number;
	}
	public void setBusiness_number(String business_number) {
		this.business_number = business_number;
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
	
	private int master_uid;
	private String address;
	private double lat_x;
	private double long_y;
	private String business_number;
	private int point;
	private int status;

}
