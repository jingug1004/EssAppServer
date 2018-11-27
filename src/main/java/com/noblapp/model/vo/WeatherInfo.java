package com.noblapp.model.vo;

import java.sql.Date;

import com.noblapp.model.support.Model;

public class WeatherInfo extends Model {
    private Date check_time;
	private String desc;
	private String dust_desc;
	private String dust_value;
	private int status;
	private String temp;
	
	public Date getCheck_time() {
		return check_time;
	}

	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDust_desc() {
		return dust_desc;
	}

	public void setDust_desc(String dust_desc) {
		this.dust_desc = dust_desc;
	}

	public String getDust_value() {
		return dust_value;
	}

	public void setDust_value(String dust_value) {
		this.dust_value = dust_value;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTemp() {
		return this.temp;
	}
	
	public void setTemp(String temp) {
		this.temp = temp;
	}
}
