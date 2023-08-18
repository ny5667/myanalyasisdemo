//======================================================================================
//视图的事件，请不要在这里修改代码，修改的代码会被覆盖
//======================================================================================

/*==================================afterSave事件==================================*/


/*==================================afterSubmit事件==================================*/


/*==================================beforeSave事件==================================*/


/*==================================beforeSubmit事件==================================*/


/*==================================onload事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onload = function(){
	var openLabel =ReactAPI.getComponentAPI("Checkbox").APIs("almAlarmRecord.openLabel").getValue().value;
if(openLabel==false){
     $($(".layout-comp-wrap")[3]).hide();
}else{
   	$($(".layout-comp-wrap")[3]).show();
}
$("div[id='tabs-15']").parent().hide();
//document.getElementById("operateBtn-save").style.display = "none";
// 临时处理地图未铺满
$('head').append('<style>.ant-spin-nested-loading{height:100%}.ant-spin-container{height:100%;}</style>');

//临时处理不显示点
const interval = setInterval(()=>{if(document.getElementsByTagName("iframe")&&document.getElementsByTagName("iframe")[0]){document.getElementsByTagName("iframe")[0].src = '/msService/SESGISConfig/themeConfig/sesGisApi/index.html?layerCode=warningLayer&mode=look&type=point&spatialId=SESECD_1.0.0_alarmRecord_AlmAlarmRecord_'+getUrlParams().id};clearInterval(interval)},200)
}

/*==================================onsave事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onsave = function(){
	
}

/*==================================afterSave事件==================================*/


/*==================================afterSubmit事件==================================*/


/*==================================beforeSave事件==================================*/


/*==================================beforeSubmit事件==================================*/


/*==================================onload事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onload = function(){
	
}

/*==================================onsave事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onsave = function(){
	
}

/*==================================afterSave事件==================================*/


/*==================================afterSubmit事件==================================*/


/*==================================beforeSave事件==================================*/


/*==================================beforeSubmit事件==================================*/


/*==================================onload事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onload = function(){
	
}

/*==================================onsave事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onsave = function(){
	
}

/*==================================afterSave事件==================================*/


/*==================================afterSubmit事件==================================*/


/*==================================beforeSave事件==================================*/


/*==================================beforeSubmit事件==================================*/


/*==================================onload事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onload = function(){
	
}

/*==================================onsave事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onsave = function(){
	
}

