//======================================================================================
//视图的事件，请不要在这里修改代码，修改的代码会被覆盖
//======================================================================================

/*==================================onclick='checkAlarm()'事件==================================*/
function checkAlarm(){
   alarmCheck()
}

/*==================================onclick='handleAlarm()'事件==================================*/
function handleAlarm(){
   alarmHandle()
}

/*==================================onclick='handleAlarm()'事件==================================*/
function checkAlarm(){
    alarmCheck()
}

/*==================================onclick='emerAction()'事件==================================*/
function emerAction(){
    enterAction()
}

/*==================================onclick='flushAlarm()'事件==================================*/
function flushAlarm(){
	ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmHandleList_almAlarmRecord_sdg").refreshDataByRequst({
    type: "post",
    url: "/msService/SESECD/alarmRecord/almAlarmRecord/alarmHandleList-query",
    param: {}
  });
}

/*==================================onclick='openView()'事件==================================*/
function openView(){
    openAlarmView()
}

/*==================================afterSave事件==================================*/


/*==================================afterSubmit事件==================================*/


/*==================================beforeSave事件==================================*/


/*==================================beforeSubmit事件==================================*/


/*==================================helpInfo事件==================================*/


/*==================================onload事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onload = function(){
	
}

/*==================================onsave事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onsave = function(){
	
}

