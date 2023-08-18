package com.supcon.orchid.SESECD.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;

public class SESECDEmEventLeveLEditEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String viewCode;

	private String modelName;

	private String operateType;

	private String ids2del;

	private Long linkId;

	private String taskDescription;

	private String activityName;

	private Long files_staffId;

	private String files_type;

	private List<Map<String, Object>> uploadFileFormMap;

	private Map<String, String> dgDeletedIds;

	private Map<String, String> dgList;

	private String viewSelect;

	private WorkFlowVar workFlowVar;


	private Long pendingId;

	private Long deploymentId;

	private Boolean webSignetFlag;

	private Boolean superEdit;

	private String pendingActivityType;

	public String getPendingActivityType() {
		return pendingActivityType;
	}

	public void setPendingActivityType(String pendingActivityType) {
		this.pendingActivityType = pendingActivityType;
	}

	public void setSuperEdit(Boolean superEdit) {
		this.superEdit = superEdit;
	}

	public Boolean getSuperEdit() {
		return superEdit;
	}

	public Boolean getWebSignetFlag() {
		return webSignetFlag;
	}

	public void setWebSignetFlag(Boolean webSignetFlag) {
		this.webSignetFlag = webSignetFlag;
	}

	public Long getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(Long deploymentId) {
		this.deploymentId = deploymentId;
	}

	public Long getPendingId() {
		return pendingId;
	}

	public void setPendingId(Long pendingId) {
		this.pendingId = pendingId;
	}

	public WorkFlowVar getWorkFlowVar() {
		return workFlowVar;
	}

	public void setWorkFlowVar(WorkFlowVar workFlowVar) {
		this.workFlowVar = workFlowVar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Map<String, String> getDgList() {
		return dgList;
	}

	public void setDgList(Map<String, String> dgList) {
		this.dgList = dgList;
	}

	public List<Map<String, Object>> getUploadFileFormMap() {
		return uploadFileFormMap;
	}

	public void setUploadFileFormMap(List<Map<String, Object>> uploadFileFormMap) {
		this.uploadFileFormMap = uploadFileFormMap;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Long getFiles_staffId() {
		return files_staffId;
	}

	public void setFiles_staffId(Long files_staffId) {
		this.files_staffId = files_staffId;
	}

	public String getIds2del() {
		return ids2del;
	}

	public void setIds2del(String ids2del) {
		this.ids2del = ids2del;
	}

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public String getFiles_type() {
		return files_type;
	}

	public void setFiles_type(String files_type) {
		this.files_type = files_type;
	}

	public Map<String, String> getDgDeletedIds() {
		return dgDeletedIds;
	}

	public void setDgDeletedIds(Map<String, String> dgDeletedIds) {
		this.dgDeletedIds = dgDeletedIds;
	}

	public String getViewSelect() {
		return viewSelect;
	}

	public void setViewSelect(String viewSelect) {
		this.viewSelect = viewSelect;
	}


	private SESECDEmEventLeveL emEventLeveL;

	public SESECDEmEventLeveL getEmEventLeveL(){
		return emEventLeveL;
	}

	public void setEmEventLeveL(SESECDEmEventLeveL emEventLeveL){
		this.emEventLeveL = emEventLeveL;
	}
}