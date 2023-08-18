package com.supcon.orchid.SESECD.entities;

import java.util.Date;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="SESECDTagConfigConvertor")
@XmlAccessorType(XmlAccessType.FIELD)
public class SESECDTagConfigConvertor {
	
	public SESECDTagConfigConvertor() {
	}
	
	public String getClassName() {
		return SESECDTagConfig.class.getName();
	}
	
	private Long id;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	private String tagName;
	
	public String getTagName(){
		return tagName;
	}
	
	public void setTagName(String tagName){
		this.tagName = tagName;
	}

	
	public SESECDTagConfigConvertor(String tagName, Long id) {
		super();
		this.tagName = tagName;
		this.id = id;
	}
	

	
}
