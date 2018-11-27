package com.noblapp.model.cmd;

import com.noblapp.model.support.Model;

public class ValueTableVo extends Model {
	private String vkey;
	private String ko;
	private String en;
	private String cn;
	private String jp;
	
	public String getVkey() {
		return vkey;
	}
	public void setVkey(String vkey) {
		this.vkey = vkey;
	}
	public String getKo() {
		return ko;
	}
	public void setKo(String ko) {
		this.ko = ko;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getJp() {
		return jp;
	}
	public void setJp(String jp) {
		this.jp = jp;
	}
	
	public void applyValue(String lang, String value) {
		if (lang.equals("ko"))
			this.ko = value;
		else if (lang.equals("en"))
			this.en = value;
		else if (lang.equals("cn"))
			this.cn = value;
		else if (lang.equals("jp"))
			this.jp = value;
	}
}
