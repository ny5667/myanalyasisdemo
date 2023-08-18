package com.supcon.orchid.SESECD.entities;

import java.util.Date;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="SESECDEmerExpertsConvertor")
@XmlAccessorType(XmlAccessType.FIELD)
public class SESECDEmerExpertsConvertor {
	
	public SESECDEmerExpertsConvertor() {
	}
	
	public String getClassName() {
		return SESECDEmerExperts.class.getName();
	}
	
	private Long id;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	
	public SESECDEmerExpertsConvertor(Long id) {
		super();
		this.id = id;
	}
	
	

	
}
