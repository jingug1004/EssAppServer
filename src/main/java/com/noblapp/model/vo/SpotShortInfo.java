package com.noblapp.model.vo;

import com.noblapp.model.support.Model;

public class SpotShortInfo extends Model {
	private int sp_id;
    private String category;
    private String name;
    private String image_list;
    private String text_info;
    private int status;
    private double lat;
    private double lng;

    public SpotShortInfo() {}
    
    public SpotShortInfo(int sp_id, String name, int status) {
    	this.sp_id = sp_id;
    	this.name = name;
    	this.status = status;
    }

	public int getSp_id() {
		return sp_id;
	}
	public void setSp_id(int sp_id) {
		this.sp_id = sp_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage_list() {
		return image_list;
	}
	public void setImage_list(String image_list) {
		this.image_list = image_list;
	}
	public String getText_info() {
		return text_info;
	}
	public void setText_info(String text_info) {
		this.text_info = text_info;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
}
