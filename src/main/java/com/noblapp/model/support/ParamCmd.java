package com.noblapp.model.support;

public class ParamCmd extends Model {
	private String module;
	private String parameter;
	
	public void setModule(String module) {
		this.module = module;
	}
	
	public String getModule() {
		return this.module;
	}
	
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	public String getParameter() {
		return this.parameter;
	}
}
