//页面加载事件
function pageLoad(){
    ReactAPI.getComponentAPI("SupDataGrid")
      .APIs("SESECD_1.0.0_ecdAlertRecord_alertRecordList_ecdAlertRecord_sdg")
      .setDbclickEvt(function (e, data) {
        // 如果需要单元格的双击事件处理,可使用data.currClickColKey的值做列判断
        console.log(data);
        const powerCode = 'SESECD_1.0.0_ecdAlertRecord_alertRecordList_self';
        var result = ReactAPI.getPowerCode(powerCode, function (res) {
            var openUrl = `/msService/VxECD/alertRecord/vxAlertRecord/alertRecordEdit?id=${data.id}&__pc__=${res[powerCode]}`;
            window.open(openUrl);
        });
      });
}

//重复报警
function repeatAlarm(){
	updateAlertStatus("2");
}


//误报
function misinformation(){
	updateAlertStatus("1");
}

//生成接警
function generateAlarm(){
	updateAlertStatus("3");
}

//------------------------------------------------------------------公共功能-------------------------------------------------------------------------

//更新处置状态
function updateAlertStatus(status){
    var gridId = ReactAPI.getComponentAPI().SupDataGrid.APIs("SESECD_1.0.0_ecdAlertRecord_alertRecordList_ecdAlertRecord_sdg");
	var data = gridId.getSelecteds();
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
		if(!o.alarmState || "已处置" === o.alarmState){
			ReactAPI.showMessage("w","请选择未处置的报警数据!");
			return false;
		}
		ids.push(o.id);
	}
	ReactAPI.openLoading("报警事件处理中...");
	//异步
	var updateAlertUrl = `/msService/SESECD/ecdAlertRecord/ecdAlertRecord/updateAlertRecordStatus?ids=${ids}&status=${status}`;
	ReactAPI.request({
		type: "get",
		data: {},
		url: updateAlertUrl,
	  },
	  function(res) {
		ReactAPI.closeLoading();
		if(res.code == 200){
          	ReactAPI.showMessage("s","操作成功!");
			gridId.refreshDataByRequst({
				type: "POST",
				url: "/msService/SESECD/ecdAlertRecord/ecdAlertRecord/alertRecordList-query",
				param: {},
			  });
		}else if(res.code == 203){
			ReactAPI.showMessage("f",res.data);
		}
	  }
	);
}