package com.noblapp.support;

/**
 * 노블앱용 익셉션 클래스
 * 
 * @author juniverse
 */
public class NoblappException extends RuntimeException {

	private static final long serialVersionUID = 1L;

//	private Logger logger = LoggerFactory.getLogger(NoblappException.class);
	
	private int code;
//	private String message;
	
	public NoblappException(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
}
