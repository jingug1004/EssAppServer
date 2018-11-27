package com.noblapp.model.vo;

import com.noblapp.model.support.Model;

public class CategoryInfo extends Model {
	
	private int mn_id;
	private String path;
	private String asset_path;
	private String title;
	private int status;

	public int getMn_id() {
		return mn_id;
	}
	public void setMn_id(int mn_id) {
		this.mn_id = mn_id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAsset_path() {
		return asset_path;
	}
	public void setAsset_path(String asset_path) {
		this.asset_path = asset_path;
	}
	
}
