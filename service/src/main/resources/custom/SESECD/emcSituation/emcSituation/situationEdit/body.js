function init(){
	//已发布不可编辑
	var urlParam = ReactAPI.getParamsInRequestUrl();
	var type = ReactAPI.getComponentAPI().SystemCode.APIs("emcSituation.situationType").getValue();
	if(urlParam.id && type && "SESECD_situation_type/001" == type.value){
		ReactAPI.getComponentAPI().Reference.APIs("emcSituation.reportPerson.name").setReadonly(true);
		ReactAPI.getComponentAPI().Reference.APIs("emcSituation.eventId.accidentName").setReadonly(true);
		ReactAPI.getComponentAPI().Reference.APIs("emcSituation.iconObj.name").setReadonly(true);
		ReactAPI.getComponentAPI().DatePicker.APIs("emcSituation.occursTime").setReadonly(true);
        ReactAPI.getComponentAPI().Reference.APIs("emcSituation.commonAddress.name").setReadonly(true);
		//ReactAPI.getComponentAPI().Input.APIs("emcSituation.position").setReadonly(true);
		ReactAPI.getComponentAPI().InputNumber.APIs("emcSituation.woundedPerson").setReadonly(true);
		ReactAPI.getComponentAPI().InputNumber.APIs("emcSituation.deathPerson").setReadonly(true);
		ReactAPI.getComponentAPI().Map.APIs("emcSituation.point").setReadonly(true);
		ReactAPI.getComponentAPI().Multfiles.APIs("emcSituation.enclosure").setReadonly(true);
		ReactAPI.getComponentAPI().TextArea.APIs("emcSituation.describtion").setReadonly(true);
		ReactAPI.getComponentAPI().SystemCode.APIs("emcSituation.situationType").setReadonly(true);
		ReactAPI.getComponentAPI().SystemCode.APIs("emcSituation.source").setReadonly(true);
	}
}

 var layerCode = "emeSituationLayer";
function positionOnchange(obj){
    debugger;
      console.info(obj);
  //后台请求获取 获取常用地址的坐标信息
  ReactAPI.request({
        type: "get",
        url: "/msService/SESECD/emcAction/CustomSESECDAlarmRecord/getDevicePositionMap?id=" + obj[0].id,
    }, function (res) {
       console.info(res);
       if(res.code ==200){
          var position = res.data;
          position.layerCode = layerCode;
          position.oldLayerCode = layerCode;
          console.info(position);
          debugger
          ReactAPI.getComponentAPI().Map.APIs('emcSituation.point').setValue(position);
        
       }
    });

}

function positionOnchange2(obj){
   debugger;
    //var sss = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmEventRef_almAlarmRecord_sdg").getSelecteds()[0];
  var  id =   obj[0].locationIncident.id;
  var  name =   obj[0].locationIncident.name;
    debugger;
      console.info(obj);
  //后台请求获取 获取常用地址的坐标信息
  ReactAPI.request({
        type: "get",
        url: "/msService/SESECD/emcAction/CustomSESECDAlarmRecord/getDevicePositionMap?id=" + obj[0].locationIncident.id,
    }, function (res) {
       console.info(res);
       if(res.code ==200){
          var position = res.data;
          position.layerCode = layerCode;
          position.oldLayerCode = layerCode;
          console.info(position);
          debugger
          ReactAPI.getComponentAPI().Map.APIs('emcSituation.point').setValue(position);
        ReactAPI.getComponentAPI().Reference.APIs("emcSituation.eventId.accidentName").setValue({
  id: id,
  name: name,
}
  );
       }
    });

}





function drillPlan(obj){
	if(!obj || obj.length == 0){
		return;
	}
	ReactAPI.getComponentAPI().Input.APIs("emcSituation.position").setValue(obj[0].position);
}

//参照页面
function describe(){
	var url = '/msService/SESECD/situationSentence/sentence/sentenceRef';
	ReactAPI.createDialog("newDialog",{
		title:"态势描述参照",
		width:"800px",
		height:"600px",
		url: url,
		isRef: true, // 是否开启参照
		buttons: [
			{
				text:"选择",
				type: "primary",
				style: { color: "#fff", background: "#3fa1a6" },
				onClick: function(event) {
					console.log(event)
                  	if(event.length > 0){
                    	ReactAPI.getComponentAPI().TextArea.APIs("emcSituation.describtion").setValue(event[0].name);
                    }
					ReactAPI.destroyDialog("newDialog");
				}
			},
			{
				text: ReactAPI.international.getText("Button.text.cancel"),
				onClick: function () {
					ReactAPI.destroyDialog("newDialog");
				}
			}
		]
	})
}