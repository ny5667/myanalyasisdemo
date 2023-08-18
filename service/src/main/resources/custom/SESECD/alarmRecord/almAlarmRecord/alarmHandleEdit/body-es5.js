function onLoad() {

    if (ReactAPI.getComponentAPI('Select').APIs('almAlarmRecord.isIncident').getValue() === false) {
        ReactAPI.getComponentAPI('Select').APIs('almAlarmRecord.isIncident').setValue(false);
    }

    var processState = ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.processState").getValue().value;
    if (processState === 'SESECD_processState/004' || processState === 'SESECD_processState/005') {
        ReactAPI.getComponentAPI('Select').APIs('almAlarmRecord.isIncident').setValue(false);
        ReactAPI.getComponentAPI('Select').APIs('almAlarmRecord.isIncident').setReadonly(true);
    }
    ReactAPI.getComponentAPI().Label.APIs("almAlarmRecord-alarmIdPlanObj-label").hide();
    ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.alarmIdPlanObj").hide();

    ReactAPI.getComponentAPI("Checkbox").APIs("almAlarmRecord.voiceConfigFlag").setValue(true);
    $("div[data-key='almAlarmRecord.voiceConfigFlag']").hide();

    $("div[data-key='almAlarmRecord.isIncident']").hide();
    $("div[data-key='almAlarmRecord.drillPlanId.name']").hide();
    $("div[data-key='almAlarmRecord.isEmergency']").hide();
    $("div[data-key='almAlarmRecord.alarmRecordIdPersonId']").hide();
    $("div[data-key='almAlarmRecord-alarmRecordIdPersonId-label']").hide();

    var e = ReactAPI.getComponentAPI().SystemCode.APIs("almAlarmRecord.eventType").getValue();
    if (e) {
        isIncident(e.value);
    }
}

function enterEmeHandle() {
    debugger;
    debugger;
    var id = window.parent.ReactAPI.getComponentAPI().SupDataGrid.APIs('SESECD_1.0.0_alarmRecord_alarmHandleList_almAlarmRecord_sdg').getSelecteds()[0].id;
    var flag = ReactAPI.getComponentAPI().Checkbox.APIs("almAlarmRecord.isEmergency").getValue().value;
    if (flag) {
        var code = 'SESECD_1.0.0_ecdPanel_gisEcd_self';
        var __pc__ = ReactAPI.getPowerCode(code);
        var url = "http://" + window.location.host + "/msService/SESECD/ecdPanel/ecdCommom/gisEcd?__pc__=" + __pc__[code] + "&eventId=" + id;
        window.open(url, "_blank");
    }
}

function isIncident(obj) {
    if (!obj || obj.length == 0) {
        $("div[data-key='almAlarmRecord.drillPlanId.name']").hide();
        $("div[data-key='almAlarmRecord.isEmergency']").hide();
        ReactAPI.getComponentAPI().SystemCode.APIs("almAlarmRecord.processState").setValue("");
        ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.processState").setReadonly(false);
        return;
    }
    debugger;
    if (obj == "SESECD_eventType/001") {
        ReactAPI.getComponentAPI().Select.APIs("almAlarmRecord.isIncident").setValue(true);
        ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.drillPlanId.name").setRequired(false);
        ReactAPI.getComponentAPI().Label.APIs("almAlarmRecord.drillPlanId.name").setNullableStyle(false);
        $("div[data-key='almAlarmRecord.drillPlanId.name']").hide();
        ReactAPI.getComponentAPI().SystemCode.APIs("almAlarmRecord.processState").setValue("SESECD_processState/001");
        ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.processState").setReadonly(true);
        $("div[data-key='almAlarmRecord.isEmergency']").show();
        ReactAPI.getComponentAPI().Checkbox.APIs("almAlarmRecord.isEmergency").setValue(true);
    } else if (obj == "SESECD_eventType/002") {
        ReactAPI.getComponentAPI().Select.APIs("almAlarmRecord.isIncident").setValue(true);
        $("div[data-key='almAlarmRecord.drillPlanId.name']").show();
        ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.drillPlanId.name").setRequired(false);
        ReactAPI.getComponentAPI().Label.APIs("almAlarmRecord.drillPlanId.name").setNullableStyle(false);
        ReactAPI.getComponentAPI().SystemCode.APIs("almAlarmRecord.processState").setValue("SESECD_processState/001");
        ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.processState").setReadonly(true);
        $("div[data-key='almAlarmRecord.isEmergency']").show();
        ReactAPI.getComponentAPI().Checkbox.APIs("almAlarmRecord.isEmergency").setValue(true);
    } else {
        ReactAPI.getComponentAPI().Select.APIs("almAlarmRecord.isIncident").setValue(false);
        ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.drillPlanId.name").setRequired(false);
        ReactAPI.getComponentAPI().Label.APIs("almAlarmRecord.drillPlanId.name").setNullableStyle(false);
        $("div[data-key='almAlarmRecord.drillPlanId.name']").hide();
        ReactAPI.getComponentAPI().SystemCode.APIs("almAlarmRecord.processState").setValue("");
        ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.processState").setReadonly(false);
        $("div[data-key='almAlarmRecord.isEmergency']").hide();
        ReactAPI.getComponentAPI().Checkbox.APIs("almAlarmRecord.isEmergency").setValue(false);
    }
}

//是否应急事件
function incidentChange(obj) {

    if (obj === "true") {
        ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.processState").setValue("SESECD_processState/001");
        ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.processState").setReadonly(true);
        //        ReactAPI.getComponentAPI("TextArea").APIs("almAlarmRecord.process").setValue(ReactAPI.international.getText("SESECD.custom.randon1586875215336"));
        ReactAPI.getComponentAPI().Label.APIs("almAlarmRecord-alarmIdPlanObj-label").show();
        ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.alarmIdPlanObj").show();
    } else {

        ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.processState").setValue();
        ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.processState").setReadonly(false);
        ReactAPI.getComponentAPI("TextArea").APIs("almAlarmRecord.process").setValue();
        ReactAPI.getComponentAPI().Label.APIs("almAlarmRecord-alarmIdPlanObj-label").hide();
        ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.alarmIdPlanObj").hide();
    }
}

//处理状态改变
function processStateChange(obj) {

    if (obj === 'SESECD_processState/004' || obj === 'SESECD_processState/005') {
        ReactAPI.getComponentAPI('Select').APIs('almAlarmRecord.isIncident').setValue(false);
        ReactAPI.getComponentAPI('Select').APIs('almAlarmRecord.isIncident').setReadonly(true);
    } else {
        ReactAPI.getComponentAPI('Select').APIs('almAlarmRecord.isIncident').setReadonly(false);
    }
}