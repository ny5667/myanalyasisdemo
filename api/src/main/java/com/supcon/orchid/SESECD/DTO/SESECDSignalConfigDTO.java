package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.DepartmentDTO;
import com.supcon.orchid.foundation.ws.entities.PositionDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_signalConfig_SignalConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 接口配置.
 */
public class SESECDSignalConfigDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcFullEntityDTO {
	private static final long serialVersionUID = 1L;
	private Integer alarmTime ; // 重复报警间隔(秒)
	private Boolean enable = false; // 是否启用
	private String ip ; // IP
	private Integer port ; // 端口
	private SystemCode signalProvider
;// 融合通信供应商
	private SystemCode signalType
;// 接口类型
	private Integer sourceTerminal ; // 源终端号码


	private Document document;

	private String bapAttachmentInfo;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getBapAttachmentInfo() {
		return bapAttachmentInfo;
	}

	public void setBapAttachmentInfo(String bapAttachmentInfo) {
		this.bapAttachmentInfo = bapAttachmentInfo;
	}


    public Integer getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Integer alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

	public SystemCode getSignalProvider() {
		return signalProvider;
	}

	public void setSignalProvider(SystemCode signalProvider) {
		this.signalProvider = (SystemCode) signalProvider;
	}

	public SystemCode getSignalType() {
		return signalType;
	}

	public void setSignalType(SystemCode signalType) {
		this.signalType = (SystemCode) signalType;
	}

    public Integer getSourceTerminal() {
        return sourceTerminal;
    }

    public void setSourceTerminal(Integer sourceTerminal) {
        this.sourceTerminal = sourceTerminal;
    }


	private Company company;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company =company;
	}


	private String virtualId;
	public String getVirtualId() {
		return virtualId;
	}

	public void setVirtualId(String virtualId) {
		this.virtualId = virtualId;
	}

	@Override
	protected String _getEntityName() {
		return null;
	}

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_signalConfig_SignalConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
