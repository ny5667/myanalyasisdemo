package com.supcon.orchid.SESECD.entities;

import java.util.Date;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="SESECDEventDescribeConvertor")
@XmlAccessorType(XmlAccessType.FIELD)
public class SESECDEventDescribeConvertor {
	
	public SESECDEventDescribeConvertor() {
	}
	
	public String getClassName() {
		return SESECDEventDescribe.class.getName();
	}
	
	private Long id;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	private String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	public SESECDEventDescribeConvertor(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	

	
}
