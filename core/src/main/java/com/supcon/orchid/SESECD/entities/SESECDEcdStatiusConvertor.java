package com.supcon.orchid.SESECD.entities;

import java.util.Date;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="SESECDEcdStatiusConvertor")
@XmlAccessorType(XmlAccessType.FIELD)
public class SESECDEcdStatiusConvertor {
	
	public SESECDEcdStatiusConvertor() {
	}
	
	public String getClassName() {
		return SESECDEcdStatius.class.getName();
	}
	
	private Long id;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	
	public SESECDEcdStatiusConvertor(Long id) {
		super();
		this.id = id;
	}
	
	

	
}
