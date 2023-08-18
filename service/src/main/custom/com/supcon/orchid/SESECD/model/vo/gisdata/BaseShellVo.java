package com.supcon.orchid.SESECD.model.vo.gisdata;

import java.util.List;

public class BaseShellVo {
	
	private List<HeadVo> head;
	private List<?> data;
	
	public List<HeadVo> getHead() {
		return head;
	}
	public void setHead(List<HeadVo> head) {
		this.head = head;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
}
