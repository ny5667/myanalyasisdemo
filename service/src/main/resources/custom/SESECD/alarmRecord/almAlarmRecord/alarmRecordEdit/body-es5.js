var layerCode = "incidentLayer";
function positionOnchange(obj) {
  debugger;
  console.info(obj);
  //后台请求获取 获取常用地址的坐标信息
  ReactAPI.request({
    type: "get",
    url: "/msService/SESECD/emcAction/CustomSESECDAlarmRecord/getDevicePositionMap?id=" + obj[0].id
  }, function (res) {
    console.info(res);
    if (res.code == 200) {
      var position = res.data;
      position.layerCode = layerCode;
      position.oldLayerCode = layerCode;
      console.info(position);
      ReactAPI.getComponentAPI().Map.APIs('almAlarmRecord.mapPoint').setValue(position);
    }
  });
}

function customOnLoad() {

  var openLabel = ReactAPI.getComponentAPI("Checkbox").APIs("almAlarmRecord.openLabel").getValue().value;
  if (openLabel == false) {
    $($(".layout-comp-wrap")[3]).hide();
  } else {
    $($(".layout-comp-wrap")[3]).show();
  }

  var content = ReactAPI.getParamsInRequestUrl().content;
  var responsibleDepartmentId = ReactAPI.getParamsInRequestUrl().responsibleDepartmentId;
  var responsibleDepartmentName = ReactAPI.getParamsInRequestUrl().responsibleDepartmentName;

  if (content !== undefined) {
    ReactAPI.getComponentAPI("Input").APIs("almAlarmRecord.accidentName").setValue(content);
    ReactAPI.getComponentAPI("TextArea").APIs("almAlarmRecord.description").setValue(content);
  }
  if (responsibleDepartmentId !== undefined && responsibleDepartmentName !== undefined) {
    ReactAPI.getComponentAPI("Reference").APIs("almAlarmRecord.hpmDepartment.name").setValue({
      id: parseInt(responsibleDepartmentId),
      name: responsibleDepartmentName
    });
    ReactAPI.getComponentAPI("Input").APIs("almAlarmRecord.position").setValue(responsibleDepartmentName);
  }

  ReactAPI.setHeadBtnAttr("describe", { icon: "sup-btn-eighteen-dt-op-reference" });
  $("div[data-key='almAlarmRecord.isTurnAlarm']").hide();
  $("div[data-key='almAlarmRecord.isOver']").hide();
}

//打开或者关闭扩展信息
function clickOpenLabel(obj) {
  if (obj == false) {
    $($(".layout-comp-wrap")[3]).hide();
  } else {
    $($(".layout-comp-wrap")[3]).show();
  }
}

//将应急预案中的指令回填到接警的指令中
function getOrderByPlanId(obj) {
  var ids = new Array();
  for (var i = 0; i < obj.length; i++) {
    ids.push(obj[i].id);
  }
  ReactAPI.request({
    type: "post",
    data: ids,
    url: "/msService/SESWssEP/emergencyPlan/emergencyPlan/getEmcPlanActionByPlanId"
  }, function (res) {
    if (res.code == 200) {
      var datas = res.data;
      var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmRecordEditdg1576762078782");
      var gridDatas = grid.getDatagridData();
      var Arry = new Array();
      var row = '';
      var index = 0;
      if (gridDatas.length > 0) {
        index = gridDatas.length;
        for (var i = 0; i < datas.length; i++) {
          var isExist = false;
          for (var j = 0; j < gridDatas.length; j++) {
            if (gridDatas[j].instructions.id == datas[i].id) {
              isExist = true;
              break;
            }
          }
          if (!isExist) {
            var Value = {
              instructions: {
                id: datas[i].id,
                emergencyPlanId: {
                  id: datas[i].emergencyPlanId.id
                }
              },
              actionName: datas[i].actionName,
              actionDescription: datas[i].actionDescription,
              actionAddress: datas[i].actionAddress,
              actionCatogory: {
                id: datas[i].actionCatogory.id,
                value: datas[i].actionCatogory.fullPathName
              },
              commomState: {
                id: "SESECD_commonState/001"
              }
            };
            Arry.push(Value);
            index++;
          }
        }
        if (Arry.length > 0) {
          for (i = 0; i < index; i++) {
            if (i == 0) {
              row += i;
            } else {
              row += ',' + i;
            }
          }
          grid.addLine(Arry);
          grid.saveAddLine(row);
        }
      } else {
        for (var i = 0; i < datas.length; i++) {
          var Value = {
            instructions: {
              id: datas[i].id,
              emergencyPlanId: {
                id: datas[i].emergencyPlanId.id
              }
            },
            actionName: datas[i].actionName,
            actionDescription: datas[i].actionDescription,
            actionAddress: datas[i].actionAddress,
            actionCatogory: {
              id: datas[i].actionCatogory.id,
              value: datas[i].actionCatogory.fullPathName
            },
            commomState: {
              id: "SESECD_commonState/001"
            }
          };
          Arry.push(Value);
          if (i == 0) {
            row += i;
          } else {
            row += ',' + i;
          }
        }
        if (Arry.length > 0) {
          grid.addLine(Arry);
          grid.saveAddLine(row);
        }
      }
    }
  });
}

//删除预案事件
function customDelete() {

  var rowDate = ReactAPI.getComponentAPI('SupDataGrid').APIs("SESECD_1.0.0_alarmRecord_alarmRecordEditdg1576762038198").getSelecteds();
  if (rowDate.length != 1) {
    ReactAPI.showMessage('w', ReactAPI.international.getText('ec.business.view.choose.one'));
    return false;
  }

  var planId = rowDate[0].planObj.id;
  var planRowIndex = rowDate[0].rowIndex.toString();

  var actionRows = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmRecordEditdg1576762078782").getDatagridData();

  //判断指令是否可以删除
  for (var i = actionRows.length - 1; i >= 0; i--) {
    if (actionRows[i].instructions.emergencyPlanId.id === planId && !(actionRows[i].commomState.id === 'SESECD_commonState/001')) {
      ReactAPI.showMessage('w', ReactAPI.international.getText('SESECD.custom.randon1584520155679'));
      return;
    }
  }

  //删除预案
  ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmRecordEditdg1576762038198").deleteLine(planRowIndex);

  onPlanChange();
}

//预案名称变化
function onPlanChange() {
  //删除指令
  var actionRows = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmRecordEditdg1576762078782").getDatagridData();

  var planRows = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmRecordEditdg1576762038198").getDatagridData();

  var planIdArray = [];

  for (var i = 0; i < planRows.length; i++) {
    planIdArray.push(planRows[i].planObj.id);
  }

  var actionDeleteRowIndexArray = [];
  for (var i = actionRows.length - 1; i >= 0; i--) {
    if (planIdArray.indexOf(actionRows[i].instructions.emergencyPlanId.id) === -1) {
      actionDeleteRowIndexArray.push(actionRows[i].rowIndex);
    }
  }
  if (actionDeleteRowIndexArray.length > 0) {
    ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmRecordEditdg1576762078782").deleteLine(actionDeleteRowIndexArray.join(','));
  }
}

//保存事件
function saveActionGridData() {

  //关联指令保存行
  var actionRows = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmRecordEditdg1576762078782").getDatagridData();
  var actionRowArray = [];
  for (var i = 0; i < actionRows.length; i++) {
    actionRowArray.push(i);
  }
  if (actionRowArray.length > 0) {
    ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmRecordEditdg1576762078782").saveAddLine(actionRowArray.join(','));
  }
}

//参照页面
function describe() {
  var url = '/msService/SESECD/eventDescription/eventDescribe/describeListRef';
  ReactAPI.createDialog("newDialog", {
    title: "事件描述参照",
    width: "800px",
    height: "600px",
    url: url,
    isRef: true, // 是否开启参照
    buttons: [{
      text: "选择",
      type: "primary",
      style: { color: "#fff", background: "#3fa1a6" },
      onClick: function onClick(event) {
        if (event.length > 0) {
          ReactAPI.getComponentAPI().TextArea.APIs("almAlarmRecord.description").setValue(event[0].name);
        }
        ReactAPI.destroyDialog("newDialog");
      }
    }, {
      text: ReactAPI.international.getText("Button.text.cancel"),
      onClick: function onClick() {
        ReactAPI.destroyDialog("newDialog");
      }
    }]
  });
}
//事故类型删除触发事件
function alarmIdEnenetrelAfterClear(deleteObj) {
  debugger;
  var typeArr = [];
  typeArr.push(deleteObj.id);

  ReactAPI.request({
    type: "post",
    data: {
      "planIds": null,
      "typeIds": typeArr
    },
    url: "/msService/SESECD/emcAction/emcAction/getContingencyPlan"
  }, function (res) {
    console.log(res);
    var arrl = new Array();
    for (var i = 0; i < res.data.length; i++) {
      ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.alarmIdPlanObj").removeValue(res.data[i].name);
    }
  });
  console.log("afterClear" + deleteObj);
}

//事故类型添加出发事件
function alarmIdEnenetrelOnAfterSetPosition(MneObj) {
  debugger;
  var planArr = [];
  var arr = ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.alarmIdPlanObj").getValue();
  for (var i = 0; i < arr.length; i++) {
    planArr.push(arr[i].id);
  }
  var typeArr = [];
  for (var _i = 0; _i < MneObj.length; _i++) {
    typeArr.push(MneObj[_i].id);
  }
  console.log(arr);

  ReactAPI.request({
    type: "post",
    data: {
      "planIds": planArr,
      "typeIds": typeArr
    },
    url: "/msService/SESECD/emcAction/emcAction/getContingencyPlan"
  }, function (res) {
    console.log(res);
    ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.alarmIdPlanObj").setValue(res.data);
  });
}