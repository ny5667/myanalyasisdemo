//打开应急事件查看视图
function openAlarmView() {
  var _gridId = "SESECD_1.0.0_alarmRecord_alarmEventList_almAlarmRecord_sdg";
  var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
  var rows = grid.getSelecteds();
  if (rows.length != 1) {
    ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
    return;
  }
  var code = 'SESECD_1.0.0_alarmRecord_alarmRecordList_self'
  var __pc__ = ReactAPI.getPowerCode(code);
  var url = "/msService/SESECD/alarmRecord/almAlarmRecord/alarmEventView?__pc__=" + __pc__[code] + "&id=" + rows[0].id + "&viewCode=SESECD_1.0.0_alarmRecord_alarmRecordList&entityCode=SESECD_1.0.0_alarmRecord&openType=frame&viewType=view";
  window.open(url, "_blank");
}


//事件关闭
function alarmOver() {
  var _gridId = "SESECD_1.0.0_alarmRecord_alarmEventList_almAlarmRecord_sdg";
  var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
  var rows = grid.getSelecteds();
  if (rows.length == 0) {
    ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
    return;
  }
  var cid = ReactAPI.getUserInfo().company.id;
  var ids = new Array();//组织应急事件ID
  for (var i = 0; i < rows.length; i++) {
    if (rows[i].cid != cid) {
      ReactAPI.showMessage('w', "不允许修改非本公司数据!");
      return false;
    }
    if (rows[i].isOver == true) {
      ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1577084065733") + (rows[i].rowIndex + 1) + ReactAPI.international.getText("SESECD.custom.randon1577084139417") + "[" + rows[i].accidentName + "]," + ReactAPI.international.getText("SESECD.custom.randon1577084544748"));
      return false;
    }
    ids.push(rows[i].id)
  }
  ReactAPI.createDialog("alarmEventDialog", {
    title: ReactAPI.international.getText("SESECD.custom.randon1577082425419"),
    isRef: false,
    width: "400px",
    height: "300px",
    htmlDom: "<span>" + ReactAPI.international.getText("SESECD.custom.randon1577082979789") + "</span>",
    buttons: [
      {
        text: ReactAPI.international.getText("SESECD.custom.randon1576928233503"),
        type: "primary",
        onClick: function (event) {//批量关闭事件
          ReactAPI.request(
            {
              type: "post",
              data: ids,//应急事件ID
              url: "/msService/SESECD/alarmRecord/alarmRecord/overEvents",
            },
            function (res) {
              if (res.code == 200 && res.data == true) {
                ReactAPI.showMessage("s", ReactAPI.international.getText("SESECD.custom.randon1577103321584"));
                setTimeout(function () {
                  ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmEventList_almAlarmRecord_sdg").refreshDataByRequst({
                    type: "post",
                    url: "/msService/SESECD/alarmRecord/almAlarmRecord/alarmEventList-query",
                    param: {}
                  });//重新刷新应急事件列表
                  ReactAPI.destroyDialog("alarmEventDialog");
                }, 1000);
              }
            }
          );
        }
      },
      {
        text: ReactAPI.international.getText("SESECD.custom.randon1576928250949"),
        onClick: event => {
          ReactAPI.destroyDialog("alarmEventDialog");
        }
      },
    ]
  });
}
//事件归档
function turnAccident() {
  var _gridId = "SESECD_1.0.0_alarmRecord_alarmEventList_almAlarmRecord_sdg";
  var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
  var rows = grid.getSelecteds();
  if (rows.length != 1) {
    ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
    return;
  }
  var eventId = "";//组织应急事件ID
  for (var i = 0; i < rows.length; i++) {
    if (rows[i].isOver != true) {
      ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1577084065733") + (rows[i].rowIndex + 1) + ReactAPI.international.getText("SESECD.custom.randon1577084139417") + "[" + rows[i].accidentName + "]," + ReactAPI.international.getText("SESECD.custom.randon1577092679931"));
      return false;
    }
    eventId = rows[i].id + "";
  }
  ReactAPI.createDialog("alarmTurnEventDialog", {
    title: ReactAPI.international.getText("SESECD.custom.randon1577082425419"),
    isRef: false,
    width: "400px",
    height: "350px",
    url: "/msService/SESECD/alarmRecord/almAlarmRecord/alarmTurnEdit?id=" + eventId,
    buttons: [
      {
        text: ReactAPI.international.getText("SESECD.custom.randon1576928233503"),
        type: "primary",
        onClick: function (event) {//归档事件
          event.ReactAPI.getComponentAPI("Input").APIs("almAlarmRecord.alarmIds").setValue(eventId);
          event.ReactAPI.submitFormData("save", function (res) {
            ReactAPI.showMessage("s", ReactAPI.international.getText("SESECD.custom.randon1587460456008"));
          });
        }
      },
      {
        text: ReactAPI.international.getText("SESECD.custom.randon1576928250949"),
        onClick: event => {
          ReactAPI.destroyDialog("alarmTurnEventDialog");
        }
      },
    ]
  });
}

//进入应急处置
function enterAction() {
  debugger
  var _gridId = "SESECD_1.0.0_alarmRecord_alarmEventList_almAlarmRecord_sdg";
  var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
  var rows = grid.getSelecteds();
  if (rows.length != 1) {
    ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
    return;
  }
  var cid = ReactAPI.getUserInfo().company.id;
  if (cid != rows[0].cid) {
    ReactAPI.showMessage('w', "不允许修改非本公司数据!");
    return false;
  }
  var selected = rows[0];
  //var isIncident = rows[0].isIncident;
  var isOver = rows[0].isOver;

  if (isOver === true) {
    ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1587462895468"));
    return;
  }

  //if(isIncident===true){
  var id = selected.id;
  var code = 'SESECD_1.0.0_ecdPanel_gisEcd_self'
  var __pc__ = ReactAPI.getPowerCode(code);
  //var url = "http://"+window.location.host+"/msService/SESECD/ecdPanel/ecdCommom/gisEcd?__pc__="+__pc__[code]+"&eventId="+id; //旧版地图
  var url = "/msService/SESGISConfig/themeConfig/themeLayers/index?themeCode=newEmeCommand&eventId=" + id;
  window.open(url, "_blank");
  //}else{
  //	ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1583676739130"));
  //}
}

//省国贸上报
function sgmReport() {
  let row = getSelectRow();
  if (row === undefined) {
    return;
  }

  // 自定义按钮
  ReactAPI.openConfirm({
    message: "是否上报？",
    buttons: [
      {
        operatetype: "yes",
        text: "是",
        type: "primary",
        onClick: function () {
          ReactAPI.closeConfirm();
          ReactAPI.openLoading("上报中");
          ReactAPI.request(
            {
              type: "get",
              data: {},
              url: `/msService/SESECD/alarmRecord/almAlarmRecord/sgmReportEvent/report/${row.id}`
            },
            function(res) {
              ReactAPI.closeLoading();
              if(res.code === 200){
//                ReactAPI.closeMessage();
//                ReactAPI.showMessage("s", "上报成功");
                  alert("上报成功")
              }else{
//                ReactAPI.closeMessage();
//                ReactAPI.showMessage("w", res.message);
                  alert(res.message)
              }
            }
          );


        }
      },
      {
        operatetype: "no",
        text: "否",
        type: "primary",
        onClick: function () {
          ReactAPI.closeConfirm();
        }
      }
    ]
  });

}

//接警确认
function alarmCheck(){
  console.log("接警确认---")
}

/*------------------------------------------------------------------公共方法----------------------------------------------------------------------*/


function getSelectRow() {
  var _gridId = "SESECD_1.0.0_alarmRecord_alarmEventList_almAlarmRecord_sdg";
  var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
  var rows = grid.getSelecteds();
  if (rows.length != 1) {
    ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
    return;
  }
  var cid = ReactAPI.getUserInfo().company.id;
  if (cid != rows[0].cid) {
    ReactAPI.showMessage('w', "不允许修改非本公司数据!");
    return;
  }
  var selected = rows[0];
  return selected;
}