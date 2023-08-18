var sectionIds = '';
var NOT_EXIST_ID = '99999999999';

//页面加载事件
function onLoad() {

    var accidentArray = ReactAPI.getComponentAPI("Reference").APIs("alarmAction.alarmId.id").getValue();
    if (accidentArray.length > 0) {
        sectionIds = getSectionIds(accidentArray[0].id);
    } else {
        sectionIds = NOT_EXIST_ID;
    }
}

function isIncident(obj) {
    if (!obj || obj.length == 0) {
        return;
    }
    debugger;
    ReactAPI.getComponentAPI().Reference.APIs("drillEvaluate.drillAddress").setValue(obj[0].drillLocation);
    ReactAPI.getComponentAPI().Reference.APIs("drillEvaluate.drillDept.name").setValue(obj[0].drillOrg);
    ReactAPI.getComponentAPI().Reference.APIs("drillEvaluate.mainPerson.name").setValue(obj[0].mainPerson.name);
    ReactAPI.getComponentAPI().DatePicker.APIs("drillEvaluate.startTime").setValue(obj[0].begTime);
    ReactAPI.getComponentAPI().DatePicker.APIs("drillEvaluate.endTime").setValue(obj[0].begTime);
}

//获取通讯组Ids
function getSectionIds(alarmRecordId) {

    var res = NOT_EXIST_ID;

    var result = ReactAPI.request({
        type: "get",
        data: {},
        url: "/msService/SESECD/emcAction/CustomSESECDAlarmRecord/getSectionIdListByAlarmRecordId?alarmRecordId=" + alarmRecordId,
        async: false
    });

    if (result.data.length > 0) {
        res = result.data.join(',');
    }

    return res;
}