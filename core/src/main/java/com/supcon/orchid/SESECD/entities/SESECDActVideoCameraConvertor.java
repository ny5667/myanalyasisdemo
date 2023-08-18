package com.supcon.orchid.SESECD.entities;

import java.util.Date;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="SESECDActVideoCameraConvertor")
@XmlAccessorType(XmlAccessType.FIELD)
public class SESECDActVideoCameraConvertor {
	
	public SESECDActVideoCameraConvertor() {
	}
	
	public String getClassName() {
		return SESECDActVideoCamera.class.getName();
	}
	
	private Long id;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	
	public SESECDActVideoCameraConvertor(Long id) {
		super();
		this.id = id;
	}
	
	

	
}
