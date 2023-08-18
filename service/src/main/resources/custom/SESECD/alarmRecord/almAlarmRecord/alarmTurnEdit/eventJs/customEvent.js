//======================================================================================
//视图的事件，请不要在这里修改代码，修改的代码会被覆盖
//======================================================================================

/*==================================afterSave事件==================================*/


/*==================================afterSubmit事件==================================*/


/*==================================beforeSave事件==================================*/


/*==================================beforeSubmit事件==================================*/


/*==================================onload事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onload = function(){
	
}

/*==================================onsave事件==================================*/
var SESECD_alarmRecord_almAlarmRecord_onsave = function(){
	var data = ReactAPI.getSaveData();
var accidentLevel = data.almAlarmRecord.accidentLevel.id;
var accidentClass = data.almAlarmRecord.accidentClass.id;
var accidentReason = data.almAlarmRecord.accidentReason.id;
var id = data.almAlarmRecord.alarmIds;
var staffId = ReactAPI.getUserInfo().staff.id + "";
var uploadFileFormMap = data.uploadFileFormMap;
for(var i=0;i<uploadFileFormMap.length;i++){
	uploadFileFormMap[i]["enclosureFiles.type"] = "SESWssAM_accidentAnalyse_AccidentAnalys";
	for(var j=0;j<uploadFileFormMap[i]["enclosureFiles.propertyCode"].length;j++){
		uploadFileFormMap[i]["enclosureFiles.propertyCode"][j] = "SESWssAM_1.0.0_accidentAnalyse_AccidentAnalys_enclosure";
	}
}
var rData = {
	operateType: "save", 
	deploymentId:null,
	accidentAnalys:{
		alarmId:Number(id),
		accidentReason:{
			id:accidentReason
		},
		accidentLevel:{
			id:accidentLevel
		},
		accidentType:{
			id:accidentClass
		},
		version:0
	},
	uploadFileFormMap,
	ids2del:"",
	files_staffId:staffId,
	viewCode:"SESWssAM_1.0.0_accidentAnalyse_accidentAnalyseEdit"
}
ReactAPI.request(
   {
	 type: "post",
	 data:rData,
	 url: "/msService/SESWssAM/accidentAnalyse/accident/alarmTurnAccidentAnalys/submit",
   },
   function(res) {
	 if(res.success && res.data=="SUCCESS"){
		console.log(res.data)
		ReactAPI.showMessage("s", ReactAPI.international.getText("SESECD.custom.randon1577103321584"));
		window.parent.ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmEventList_almAlarmRecord_sdg").refreshDataByRequst({
		  type: "post",
		  url: "/msService/SESECD/alarmRecord/almAlarmRecord/alarmEventList-query",
		  param: {}
		});//重新刷新应急事件列表
		window.parent.ReactAPI.destroyDialog("alarmTurnEventDialog");
	 }else{
		 ReactAPI.showMessage("w", res.msg);
	 }
   }
 );
return false;
}

