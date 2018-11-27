package com.noblapp.model.cmd;

public class StoreUpdateCmd extends StoreCmd {
	private String store_name;
    private String address;
    private double lat_x;
    private double long_y;
    private String phone;
    private String business_number;
    
    
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBusiness_number() {
		return business_number;
	}
	public void setBusiness_number(String business_number) {
		this.business_number = business_number;
	}
    
}
