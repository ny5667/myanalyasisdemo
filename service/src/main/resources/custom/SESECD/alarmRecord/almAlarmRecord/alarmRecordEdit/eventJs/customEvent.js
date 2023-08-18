//======================================================================================
//视图的事件，请不要在这里修改代码，修改的代码会被覆盖
//======================================================================================

/*==================================onload事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onload = function(){
	document.getElementsByClassName("m-user-info")[0].style.display = "none"
}

/*==================================onsave事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onsave = function(){
	
}

/*==================================onclick='onclickDescribe()'事件==================================*/
function onclickDescribe(){
	describe();
}

/*==================================onAfterClear='afterClear(deleteObj)'事件==================================*/
function afterClear(deleteObj){
alarmIdEnenetrelAfterClear(deleteObj);
}

/*==================================onAfterSet='onAfterSetPosition(MneObj)'事件==================================*/
function onAfterSetPosition(MneObj){
alarmIdEnenetrelOnAfterSetPosition(MneObj);
}

/*==================================afterSave事件==================================*/


/*==================================afterSubmit事件==================================*/


/*==================================beforeSave事件==================================*/


/*==================================beforeSubmit事件==================================*/


/*==================================helpInfo事件==================================*/


/*==================================onload事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onload = function(){
	ReactAPI.getComponentAPI()
  .TextArea.APIs("almAlarmRecord.description")
  .renderSuffix('<i class="api-comp-icon search"></i>', {
    onClick: (e) => {
      describe();
    },
  });
customOnLoad();
}

/*==================================onsave事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onsave = function(){
	saveActionGridData();
}

