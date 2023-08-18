//关联态势
function initSituation(){
  	var id = ReactAPI.getParamsInRequestUrl().id;
	ReactAPI.request({
      type: "get",
      data: {},
      url: "/msService/SESECD/emcAction/CustomSESECDAlarmRecord/getRelationSituationById?id=" + id,
    },
    function(res) {
      if(res.data){
      	var arr = [];
        ReactAPI.getComponentAPI().SupDataGrid.APIs("SESECD_1.0.0_alarmRecord_alarmEventViewdg1615989219992").addLine(res.data)
      }
    });
}

//关联行动
function initAction(){
  var id = ReactAPI.getParamsInRequestUrl().id;
	ReactAPI.request({
      type: "get",
      data: {},
      url: "/msService/SESECD/emcAction/CustomSESECDAlarmRecord/getRelationActionById?id=" + id,
    },
    function(res) {
     if(res.data){
      	var arr = [];
        ReactAPI.getComponentAPI().SupDataGrid.APIs("SESECD_1.0.0_alarmRecord_alarmEventViewdg1615989227167").addLine(res.data)
      }
    });
}