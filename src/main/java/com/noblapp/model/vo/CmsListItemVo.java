package com.noblapp.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmsListItemVo {

	private long id;
	private String name;
	private String checked;
	private Object object;
	private int cnt;
	private String tableType;

	public CmsListItemVo(long id, String name, int status, Object object) {
		this.id = id;
		this.name = name;
		if (status == 0)
			this.checked = "";
		else
			this.checked = "checked";
		this.object = object;
	}

	public CmsListItemVo(long id, String name, int cnt, Object object, String type) {
		this.id = id;
		this.name = name;
		this.cnt = cnt;
		this.checked = "";
		this.object = object;
		if(type.equals("P")) {
			tableType = "장소";
		}else if(type.equals("S")) {
			tableType = "지도 장소";
		}
	}
}
