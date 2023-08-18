//重新渲染应急小组成员信息
function getEmerStaffByEmerEventId(eventId) {
  if (eventId != undefined && eventId != "" && eventId != null) {
    ReactAPI.request({
      type: "get",
      data: {},
      url: "/msService/SESECD/alarmRecord/alarmRecord/getEmerStaffByEmerEventId?eventId=" + eventId
    }, function (res) {
      if (res.code == 200) {
        //console.log(res.data)
        var data = res.data.result;
        if (data) {
          var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1578313558852");
          var layerArry = new Array();
          for (var i = 0; i < data.length; i++) {
            var value = {
              name: data[i].name,
              role: data[i].role,
              telephone: data[i].telephone,
              mobile: data[i].mobile,
              staffId: data[i].staffId,
              staffCode: data[i].staffCode,
              sectionName: data[i].sectionName,
              onlineStatus: data[i].onlineStatus
            };
            layerArry.push(value);
          }
          grid.addLine(layerArry);
        }
      }
    });
  }
}
//重新渲染应急专家信息
function getEmerExportsByEmerEventId(eventId) {
  if (eventId != undefined && eventId != "" && eventId != null) {
    ReactAPI.request({
      type: "get",
      data: {},
      url: "/msService/SESECD/alarmRecord/alarmRecord/getEmerExportsByEmerEventId?eventId=" + eventId
    }, function (res) {
      if (res.code == 200) {
        var data = res.data.result;
        if (data) {
          var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1578313562943");
          var layerArry = new Array();
          for (var i = 0; i < data.length; i++) {
            var value = {
              name: data[i].name,
              expertType: data[i].expertType,
              tel: data[i].tel,
              phone: data[i].phone,
              speciality: data[i].speciality,
              workplace: data[i].workplace,
              expertField: data[i].expertField,
              accidentType: data[i].accidentType
            };
            layerArry.push(value);
          }
          grid.addLine(layerArry);
        }
      }
    });
  }
}

//重新渲染所有应急小组成员信息
function getAllEmerStaffByEmerEventId() {
  var _this = this;
  ReactAPI.request({
    type: "get",
    data: {},
    url: "/msService/SESECD/emergency/emergency/query"
  }, function (res) {
    console.log(111);
    if (res.code == 200) {
      //console.log(res.data)
      var data = res.data;
      var index = "";
      data.forEach(function (e) {
        //获取的数据为跟节点，才导入数据
        if (e.parentId == "-1") {
          addTree(e, index, data);
        }
      });
    }
  });
}

/**
     * 新增数节点
     * @param e      需要新增的数据
     * @param index  需要增加某个节点的下标
     * @param stdArr 需要导入的数据
     */
function addTree(e, index, stdArr) {
  ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1616433002245").setSelecteds(index);
  var addRow;
  if (index) {
    addRow = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1616433002245").addChildLineInTree();
  } else {
    addRow = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1616433002245").addLineInTree();
  }
  packLibrary(addRow, e);
  ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1616433002245").setRowData(addRow.rowIndex, addRow);
  if (e.leaf) {
    forSelectTree(stdArr, e, addRow.rowIndex + "");
  }
}

/**
     * 判断是否是在某个节点下的数据，是的话则新增节点数据
     * @param stdArr    需要导入的数据
     * @param e         父节点数据
     * @param index     需要增加某个节点的下标
     */
function forSelectTree(stdArr, e, index) {
  stdArr.forEach(function (v) {
    if (v.parentId == e.layRec) {
      addTree(v, index, stdArr);
    }
  });
}

/**
    * 封装导入的库信息
    * @param addRow
    * @param e
    */
function packLibrary(addRow, e) {
  addRow.departMentName = e.belongDepartment;
  addRow.sectionName = e.sectionName;
  addRow.personId = e.personId;
  addRow.personName = e.personName;
  addRow.telephone = e.telephone;
  addRow.mobile = e.mobile;
  addRow.leaf = e.leaf;
  addRow.staffCode = e.staffCode;
  // addRow._parentCode = e.parentId;
  // addRow.layNo = e.layNo;
  // addRow._code = e.layRec;
}

//应急小组消息推送
function messagePulish() {
  var eventId = ReactAPI.getParamsInRequestUrl().eventId;
  if (eventId != undefined && eventId != "" && eventId != null) {
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1578313558852");
    var rows = grid.getSelecteds();
    if (rows.length == 0) {
      ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
      return;
    }
    var staffCodes = new Array();
    for (var i = 0; i < rows.length; i++) {
      staffCodes.push(rows[i].staffCode);
    }
    var url = "/msService/SESECD/alarmRecord/almAlarmRecord/messageSend?id=" + eventId;
    ReactAPI.createDialog("messagePulishDialog", {
      url: url,
      isRef: false,
      width: '510px',
      height: '400px',
      buttons: [{
        text: ReactAPI.international.getText("foundation.staff.getPartyTax.sendout"),
        onClick: function onClick(event) {

          var msgTitle = event.ReactAPI.getComponentAPI("Input").APIs("msgTitle").getValue();
          var msgContent = event.ReactAPI.getComponentAPI("TextArea").APIs("msgContent").getValue();

          var sendMessageDto = {
            codes: staffCodes,
            msgTitle: msgTitle,
            msgContent: msgContent
          };
          console.log(sendMessageDto);
          ReactAPI.destroyDialog("messagePulishDialog");
          sendMsg(sendMessageDto);
        }
      }, {
        text: ReactAPI.international.getText("Button.text.close"),
        onClick: function onClick(event) {
          ReactAPI.destroyDialog("messagePulishDialog");
        }
      }]
    });
  }
}

//所有应急小组消息推送
function allMessagePulish() {
  debugger;
  var eventId = ReactAPI.getParamsInRequestUrl().eventId;
  if (eventId != undefined && eventId != "" && eventId != null) {
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1616433002245");
    var rows = grid.getSelecteds();
    if (rows.length == 0) {
      ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
      return;
    }
    var staffCodes = new Array();
    for (var i = 0; i < rows.length; i++) {
      staffCodes.push(rows[i].staffCode);
    }
    var url = "/msService/SESECD/alarmRecord/almAlarmRecord/messageSend?id=" + eventId;
    ReactAPI.createDialog("messagePulishDialog", {
      url: url,
      isRef: false,
      width: '510px',
      height: '400px',
      buttons: [{
        text: ReactAPI.international.getText("foundation.staff.getPartyTax.sendout"),
        onClick: function onClick(event) {

          var msgTitle = event.ReactAPI.getComponentAPI("Input").APIs("msgTitle").getValue();
          var msgContent = event.ReactAPI.getComponentAPI("TextArea").APIs("msgContent").getValue();

          var sendMessageDto = {
            codes: staffCodes,
            msgTitle: msgTitle,
            msgContent: msgContent
          };
          console.log(sendMessageDto);
          ReactAPI.destroyDialog("messagePulishDialog");
          sendMsg(sendMessageDto);
        }
      }, {
        text: ReactAPI.international.getText("Button.text.close"),
        onClick: function onClick(event) {
          ReactAPI.destroyDialog("messagePulishDialog");
        }
      }]
    });
  }
}

//应急专家消息推送
function messageSend() {}

function sendMsg(sendMessageDto) {
  ReactAPI.request({
    type: "post",
    data: sendMessageDto,
    url: "/msService/SESECD/alarmRecord/alarmRecord/sendMsgTo"
  }, function (res) {
    console.log(res);
    if (res.code == 200) {
      ReactAPI.showMessage("s", ReactAPI.international.getText("SESECD.custom.randon1582293749691"));
    } else {
      ReactAPI.showMessage("f", ReactAPI.international.getText("SESECD.custom.randon1582293778533"));
    }
  });
}

//应急小组呼叫功能
function call() {
  var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1578313558852");
  var rows = grid.getSelecteds();
  if (rows.length == 0) {
    ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
    return;
  }
  var number = [];
  var map = {};
  var _iteratorNormalCompletion = true;
  var _didIteratorError = false;
  var _iteratorError = undefined;

  try {
    for (var _iterator = rows[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
      var e = _step.value;

      if (e.telephone) {
        number.push(e.telephone);
        map[e.telephone] = e.name;
      }
    }
  } catch (err) {
    _didIteratorError = true;
    _iteratorError = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion && _iterator.return) {
        _iterator.return();
      }
    } finally {
      if (_didIteratorError) {
        throw _iteratorError;
      }
    }
  }

  if (number.length == 0) {
    ReactAPI.showMessage("w", "选择的人员没有应急电话!");
    return;
  }
  ReactAPI.openLoading("拨号中...");
  //异步
  var param = {
    "action": 1,
    "destcalled": number
  };
  ReactAPI.request({
    type: "post",
    data: param,
    url: "/msService/SESECD/communication/communication/conversation"
  }, function (res) {
    console.log(res);
    ReactAPI.closeLoading();
    //拨号成功
    if (res.code == 200) {
      var getEventId = ReactAPI.getParamsInRequestUrl().eventId;
      getEmerStaffByEmerEventId(getEventId);
    } else {
      ReactAPI.showMessage("f", res.data);
    }
  });
}

//应急小组挂断功能
function hangUp() {
  var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1578313558852");
  var rows = grid.getSelecteds();
  if (rows.length == 0) {
    ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
    return;
  }
  var number = [];
  var map = {};
  var _iteratorNormalCompletion2 = true;
  var _didIteratorError2 = false;
  var _iteratorError2 = undefined;

  try {
    for (var _iterator2 = rows[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
      var e = _step2.value;

      if (e.telephone) {
        number.push(e.telephone);
        map[e.telephone] = e.name;
      }
    }
  } catch (err) {
    _didIteratorError2 = true;
    _iteratorError2 = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion2 && _iterator2.return) {
        _iterator2.return();
      }
    } finally {
      if (_didIteratorError2) {
        throw _iteratorError2;
      }
    }
  }

  if (number.length == 0) {
    ReactAPI.showMessage("w", "选择的人员没有应急电话!");
    return;
  }
  ReactAPI.openLoading("挂断中...");
  //异步
  var param = {
    "action": 2,
    "destcalled": number
  };
  ReactAPI.request({
    type: "post",
    data: param,
    url: "/msService/SESECD/communication/communication/conversation"
  }, function (res) {
    console.log(res);
    ReactAPI.closeLoading();
    //拨号成功
    if (res.code == 200) {
      var getEventId = ReactAPI.getParamsInRequestUrl().eventId;
      getEmerStaffByEmerEventId(getEventId);
    } else {
      ReactAPI.showMessage("f", res.data);
    }
  });
}

//所有应急小组呼叫功能
function allCall() {
  var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1616433002245");
  var rows = grid.getSelecteds();
  if (rows.length == 0) {
    ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
    return;
  }
  var number = [];
  var map = {};
  var _iteratorNormalCompletion3 = true;
  var _didIteratorError3 = false;
  var _iteratorError3 = undefined;

  try {
    for (var _iterator3 = rows[Symbol.iterator](), _step3; !(_iteratorNormalCompletion3 = (_step3 = _iterator3.next()).done); _iteratorNormalCompletion3 = true) {
      var e = _step3.value;

      if (e.telephone) {
        number.push(e.telephone);
        map[e.telephone] = e.name;
      }
    }
  } catch (err) {
    _didIteratorError3 = true;
    _iteratorError3 = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion3 && _iterator3.return) {
        _iterator3.return();
      }
    } finally {
      if (_didIteratorError3) {
        throw _iteratorError3;
      }
    }
  }

  if (number.length == 0) {
    ReactAPI.showMessage("w", "选择的人员没有应急电话!");
    return;
  }
  ReactAPI.openLoading("拨号中...");
  //异步
  var param = {
    "action": 1,
    "destcalled": number
  };
  ReactAPI.request({
    type: "post",
    data: param,
    url: "/msService/SESECD/communication/communication/conversation"
  }, function (res) {
    console.log(res);
    ReactAPI.closeLoading();
    //拨号成功
    if (res.code == 200) {
      var getEventId = ReactAPI.getParamsInRequestUrl().eventId;
      getEmerStaffByEmerEventId(getEventId);
    } else {
      ReactAPI.showMessage("f", res.data);
    }
  });
}
//所有应急小组挂断功能
function allHangUp() {
  var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1616433002245");
  var rows = grid.getSelecteds();
  if (rows.length == 0) {
    ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
    return;
  }
  var number = [];
  var map = {};
  var _iteratorNormalCompletion4 = true;
  var _didIteratorError4 = false;
  var _iteratorError4 = undefined;

  try {
    for (var _iterator4 = rows[Symbol.iterator](), _step4; !(_iteratorNormalCompletion4 = (_step4 = _iterator4.next()).done); _iteratorNormalCompletion4 = true) {
      var e = _step4.value;

      if (e.telephone) {
        number.push(e.telephone);
        map[e.telephone] = e.name;
      }
    }
  } catch (err) {
    _didIteratorError4 = true;
    _iteratorError4 = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion4 && _iterator4.return) {
        _iterator4.return();
      }
    } finally {
      if (_didIteratorError4) {
        throw _iteratorError4;
      }
    }
  }

  if (number.length == 0) {
    ReactAPI.showMessage("w", "选择的人员没有应急电话!");
    return;
  }
  ReactAPI.openLoading("挂断中...");
  //异步
  var param = {
    "action": 2,
    "destcalled": number
  };
  ReactAPI.request({
    type: "post",
    data: param,
    url: "/msService/SESECD/communication/communication/conversation"
  }, function (res) {
    console.log(res);
    ReactAPI.closeLoading();
    //拨号成功
    if (res.code == 200) {
      var getEventId = ReactAPI.getParamsInRequestUrl().eventId;
      getEmerStaffByEmerEventId(getEventId);
    } else {
      ReactAPI.showMessage("f", res.data);
    }
  });
}

function ptInitMenber() {
  debugger;
  var containerHeight = $('.sup-datagrid-container').css('height');
  var ht = Number(containerHeight.replace("px", ""));
  var realHt = ht + 40 + "px";
  $(".supdatagrid-pagination").hide();
  $('.sup-datagrid-container').css({ 'height': realHt });
  $("#btn-export").remove();
  ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1578313558852").setBtnImg("message", "sup-btn-own-fb");
  ReactAPI.getComponentAPI().SupDataGrid.APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1578313558852").setBtnImg("btn-hangUp", "sup-btn-own-zz");
  ReactAPI.getComponentAPI().SupDataGrid.APIs("SESECD_1.0.0_addrBook_addrBookEtrxdg1578313558852").setBtnImg("btn-call", "sup-btn-own-tb");

  /**window.setInterval(function(){
      	 var eventId = ReactAPI.getParamsInRequestUrl().eventId;
     	getEmerStaffByEmerEventId(eventId)
     },1000);*/
}