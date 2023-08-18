//======================================================================================
//视图的事件，请不要在这里修改代码，修改的代码会被覆盖
//======================================================================================

/*==================================onload事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onload = function(){
	var url = ReactAPI.getParamsInRequestUrl();
var lon = Number(url.lon);
var lat = Number(url.lat);
var height = Number(url.height);
if(lon && lat && height){
	var point = {"lon":lon,"lat":lat,"hei":height}
	var pointInfo = {"coordinates":[point],"buildingPatchId":null,"floor":null,"layerCode":"incidentLayer"}
	ReactAPI.getComponentAPI("Input").APIs("almAlarmRecord.pointInfo").setValue(ReactAPI.international.getText("SESECD.custom.randon1590742974412"))
	ReactAPI.getComponentAPI("Input").APIs("almAlarmRecord.point").setValue(JSON.stringify(pointInfo))
}
}

