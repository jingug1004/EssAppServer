package com.noblapp.model.support;

/**
 * 내부 서비스 인터페이스용 데이터 구조체
 * @author juniverse
 */
public class ParamVo extends Model {
	private long txtime;
	private String cmd;
//	private int uid;
//	private String access_token;

//	public int getUid() {
//		return uid;
//	}
//
//	public void setUid(int uid) {
//		this.uid = uid;
//	}
//
//	public String getAccess_token() {
//		return access_token;
//	}
//
//	public void setAccess_token(String access_token) {
//		this.access_token = access_token;
//	}

	public ParamVo() {
		this.txtime = System.currentTimeMillis();
	}

	public long getTxtime() {
		if (this.txtime == 0l)
			this.txtime = System.currentTimeMillis();
		return this.txtime;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public String getCmd() {
		return this.cmd;
	}

}
