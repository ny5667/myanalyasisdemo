//打开接警查看视图
function openAlarmView(){
	var _gridId = "SESECD_1.0.0_alarmRecord_alarmRecordList_almAlarmRecord_sdg";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
       var rows = grid.getSelecteds();
       if(rows.length!=1){
          ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
          return;
    }
    var code = 'SESECD_1.0.0_alarmRecord_alarmRecordList_self'
    var __pc__ = ReactAPI.getPowerCode(code);
    var url = "/msService/SESECD/alarmRecord/almAlarmRecord/alarmRecordView?__pc__="+__pc__[code]+"&id="+rows[0].id + "&viewCode=SESECD_1.0.0_alarmRecord_alarmRecordList&entityCode=SESECD_1.0.0_alarmRecord&openType=frame&viewType=view";
        window.open(url,"_blank");
}