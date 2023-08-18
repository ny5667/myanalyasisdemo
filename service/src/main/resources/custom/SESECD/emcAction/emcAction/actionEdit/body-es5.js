var sectionIds = '';
var NOT_EXIST_ID = '99999999999';

//页面加载事件
function onLoad() {

    var accidentArray = ReactAPI.getComponentAPI("Reference").APIs("emcAction.eventId.accidentName").getValue();
    if (accidentArray.length > 0) {
        sectionIds = getSectionIds(accidentArray[0].id);
    } else {
        sectionIds = NOT_EXIST_ID;
    }
}

//接警改变事件
function accidentNameOnChange(obj) {
    debugger;
    if (obj.length > 0) {
        sectionIds = getSectionIds(obj[0].id);
    } else {
        sectionIds = NOT_EXIST_ID;
    }
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