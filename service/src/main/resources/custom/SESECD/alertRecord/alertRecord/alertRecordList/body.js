//重复报警
function repeatAlarm(){
	updateAlartStatus("2");
	
}


//误报
function misinformation(){
	updateAlartStatus("1");
}

//生成接警
function generateAlarm(){
	updateAlartStatus("3");
}

var updateAlartUrl = "/msService/SESECD/alertRecord/alertRecord/updateAlertRecordStatus";

function updateAlartStatus(status){
	var data = ReactAPI.getComponentAPI().SupDataGrid.APIs("SESECD_1.0.0_alertRecord_alertRecordList_alertRecord_sdg").getSelecteds();
	if(!data || data.length == 0){
		ReactAPI.showMessage("w","请选择一条数据!");
		return false;
	}
	var ids = [];
   var cid = ReactAPI.getUserInfo().company.id;
	for(var o of data){
      	if(cid != o.cid){
          var index = o.rowIndex + 1;
		  ReactAPI.showMessage('w',"第 " + index +" 为非本公司数据，不允许执行操作!");
		  return false;
		}
		if(!o.alarmStaus || "SESECD_alarmStaus/001" != o.alarmStaus.id){
			ReactAPI.showMessage("w","请选择未处置的报警数据!");
			return false;
		}
		ids.push(o.id);
	}
	ReactAPI.openLoading("报警事件处理中...");
	//异步
	ReactAPI.request({
		type: "get",
		data: {},
		url: updateAlartUrl + "?ids=" + ids + "&status=" + status+ "&alarmType=1",
	  },
	  function(res) {
		ReactAPI.closeLoading();
		if(res.code == 200){
          	ReactAPI.showMessage("s","操作成功!");
			ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alertRecord_alertRecordList_alertRecord_sdg").refreshDataByRequst({
				type: "POST",
				url: "/msService/SESECD/alertRecord/alertRecord/alertRecordList-query",
				param: {},
			  });
		}else if(res.code == 203){
			ReactAPI.showMessage("f",res.data);
		}
	  }
	);
}

