//======================================================================================
//视图的事件，请不要在这里修改代码，修改的代码会被覆盖
//======================================================================================

/*==================================onchange='callbackIncident(obj)'事件==================================*/
function callbackIncident(obj){
   isIncident(obj);
}

/*==================================onchange='onchangeTimeString(obj)'事件==================================*/
function onchangeTimeString(obj){
   incidentChange(obj);
}

/*==================================onchange='onchangeTimeString(obj)'事件==================================*/
function onchangeTimeString(obj){
   processStateChange(obj);
}

/*==================================afterSave事件==================================*/


/*==================================afterSubmit事件==================================*/


/*==================================beforeSave事件==================================*/


/*==================================beforeSubmit事件==================================*/


/*==================================onload事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onload = function(){
	onLoad();
}

/*==================================onsave事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onsave = function(){
	//enterEmeHandle()
}
