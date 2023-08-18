//打开接警查看视图
function openAlarmView() {
    var _gridId = "SESECD_1.0.0_alarmRecord_alarmHandleList_almAlarmRecord_sdg";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
    var rows = grid.getSelecteds();
    if (rows.length != 1) {
        ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
        return;
    }
    var code = 'SESECD_1.0.0_alarmRecord_alarmRecordList_self';
    var __pc__ = ReactAPI.getPowerCode(code);
    var url = "http://" + window.location.host + "/msService/SESECD/alarmRecord/almAlarmRecord/alarmHandleView?__pc__=" + __pc__[code] + "&id=" + rows[0].id + "&viewCode=SESECD_1.0.0_alarmRecord_alarmRecordList&entityCode=SESECD_1.0.0_alarmRecord&openType=frame&viewType=view";
    window.open(url, "_blank");
}

//处警
function alarmHandle() {
    var _gridId = "SESECD_1.0.0_alarmRecord_alarmHandleList_almAlarmRecord_sdg";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
    var rows = grid.getSelecteds();
    if (rows.length == 0 || rows.length > 1) {
        ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
        return;
    }
    var cid = ReactAPI.getUserInfo().company.id;
    if (cid != rows[0].cid) {
        ReactAPI.showMessage('w', "不允许操作非本公司数据!");
        return false;
    }
    if (rows[0].isIncident === true) {
        ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1601456638419"));
        return;
    }

    var ids = new Array(); //组织接警记录ID
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].id);
    }

    ReactAPI.createDialog("alarmHandleDialog", {
        title: ReactAPI.international.getText("SESECD.viewtitle.randon1576927454878"),
        url: "/msService/SESECD/alarmRecord/almAlarmRecord/alarmHandleEdit?id=" + rows[0].id,
        isRef: false,
        width: "510px",
        height: "430px",
        buttons: [{
            text: ReactAPI.international.getText("SESECD.custom.randon1576928233503"),
            type: "primary",
            onClick: function onClick(event) {
                //批量处警，将处理状态和处理记录回填到相应的接警中
                var processState = event.ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.processState").getValue().value;
                debugger;
                var alarmLevel = event.ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.alarmLevel").getValue().value;
                if (alarmLevel == null) {
                    ReactAPI.showMessage("f", "报警等级不能为空！");
                    return;
                }
                var process = event.ReactAPI.getComponentAPI("TextArea").APIs("almAlarmRecord.process").getValue();
                var incident = event.ReactAPI.getComponentAPI('Select').APIs('almAlarmRecord.isIncident').getValue();
                var eventType = event.ReactAPI.getComponentAPI("SystemCode").APIs("almAlarmRecord.eventType").getValue().value;
                if (eventType == null) {
                    ReactAPI.showMessage("f", "处理状态不能为空！");
                    return;
                }
                var isEmergency = event.ReactAPI.getComponentAPI().Checkbox.APIs("almAlarmRecord.isEmergency").getValue().value;
                var drillPlanId = "";
                if (event.ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.drillPlanId.name").getValue().length > 0) {
                    drillPlanId = event.ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.drillPlanId.name").getValue()[0].id;
                } else {
                    drillPlanId = "";
                }

                //almAlarmRecord.voiceConfigId.contentText
                var voiceConfigId = "";
                if (event.ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.voiceConfigId.contentText").getValue().length > 0) {
                    voiceConfigId = event.ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.voiceConfigId.contentText").getValue()[0].id;
                }

                //var mesCode = "";
                // if (event.ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.mesPerson.name").getValue().length > 0) {
                //     mesCode = event.ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.mesPerson.name").getValue()[0].code;
                // } else {
                //     mesCode = "";
                // }

                var mesCode = new Array(); //获取接收人员ID
                var mesCodess = event.ReactAPI.getComponentAPI().Reference.APIs("almAlarmRecord.alarmRecordIdPersonId").getValue();
                if (mesCodess.length > 0) {
                    for (var i = 0; i < mesCodess.length; i++) {
                        mesCode.push(mesCodess[i].code);
                    }
                }
                ReactAPI.request({
                    type: "post",
                    data: {
                        alarmIds: ids,
                        //接警IDS
                        mesCode: mesCode,
                        //消息接收人code
                        drillPlanId: drillPlanId,
                        //演练计划id
                        processState: processState,
                        //事件等级
                        alarmLevel: alarmLevel,
                        //处理状态
                        eventType: eventType,
                        //事件类型
                        process: process,
                        //处理记录
                        incident: incident, //是否应急事件
                        voiceConfigId: voiceConfigId //语音配置
                    },
                    url: "/msService/SESECD/alarmRecord/alarmRecord/changeAlarmState"
                }, function (res) {
                    console.log(res);
                    debugger;
                    if (res.code == 200) {

                        enterEmeHandle(isEmergency);

                        debugger;
                        ReactAPI.showMessage("s", ReactAPI.international.getText("SESECD.custom.randon1576994234674"), '', false);
                        setTimeout(function () {
                            ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmHandleList_almAlarmRecord_sdg").refreshDataByRequst({
                                type: "post",
                                url: "/msService/SESECD/alarmRecord/almAlarmRecord/alarmHandleList-query",
                                param: {}
                            }); //重新刷新接警记录列表
                            ReactAPI.destroyDialog("alarmHandleDialog");
                            //debugger
                            //ReactAPI.destroyDialog("alarmHandleDialog");
                            //	if(window.ecdCommomGisEcdNewUrl) {
                            //  	window.open(window.ecdCommomGisEcdNewUrl,"_blank");
                            //  }
                        }, 3000);
                    } else {
                        ReactAPI.destroyDialog("alarmHandleDialog");
                    }
                });
            }
        }, {
            text: ReactAPI.international.getText("SESECD.custom.randon1576928250949"),
            onClick: function onClick() {
                ReactAPI.destroyDialog("alarmHandleDialog");
            }
        }]
    });
}

function enterEmeHandle(isEmergency) {
    debugger;
    var id = ReactAPI.getComponentAPI().SupDataGrid.APIs('SESECD_1.0.0_alarmRecord_alarmHandleList_almAlarmRecord_sdg').getSelecteds()[0].id;
    //var flag = ReactAPI.getComponentAPI().Checkbox.APIs("almAlarmRecord.isEmergency").getValue().value;
    if (isEmergency) {
        var code = 'SESECD_1.0.0_ecdPanel_gisEcd_self';
        var __pc__ = ReactAPI.getPowerCode(code);
        //var url = "http://"+window.location.host+"/msService/SESECD/ecdPanel/ecdCommom/gisEcd?__pc__="+__pc__[code]+"&eventId="+id;
        var url = "/msService/SESGISConfig/themeConfig/themeLayers/index?themeCode=newEmeCommand&eventId=" + id;
        window.open(url, "_blank");
    }
}

//转事故
function alarmToEvent() {
    debugger;
    var _gridId = "SESECD_1.0.0_alarmRecord_alarmHandleList_almAlarmRecord_sdg";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
    var rows = grid.getSelecteds();
    if (rows.length == 0) {
        ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
        return;
    }
    if (rows[0].cid !== ReactAPI.getUserInfo().company.id) {
        ReactAPI.showMessage('w', ReactAPI.international.getText('ec.edit.button.uncurrent.company'));
        return false;
    }
    var alarmDtos = new Array(); //组织接警记录信息
    if (rows[0].processState.id === 'SESECD_processState/004' || rows[0].processState.id === 'SESECD_processState/005') {
        ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1586948027778", rows[0].accidentName));
        return;
    }

    //如果已转事故处理
    if (rows[0].accidentId) {
        ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1587400849314"));
        return;
    }

    var lon, lat, hei;

    if (!isNaN(parseFloat(rows[0].lon))) {
        lon = parseFloat(rows[0].lon);
    }
    if (!isNaN(parseFloat(rows[0].lat))) {
        lat = parseFloat(rows[0].lat);
    }
    if (!isNaN(parseFloat(rows[0].hei))) {
        hei = parseFloat(rows[0].hei);
    }

    alarmDtos.push({
        alarmId: rows[0].id,
        //接警ID
        accidentName: rows[0].accidentName,
        alarmPersonId: rows[0].alarmPerson == null ? null : rows[0].alarmPerson.id,
        //上报人ID
        receiverId: rows[0].receiver == null ? null : rows[0].receiver.id,
        //接警人ID
        hpmDepartmentId: rows[0].hpmDepartment == null ? null : rows[0].hpmDepartment.id,
        //事发部门ID
        happenTime: rows[0].happenTime,
        //发生时间
        position: rows[0].position,
        //发生地点
        description: rows[0].description,
        //事件描述
        lon: lon,
        //地图经度
        lat: lat,
        //地图纬度
        hei: hei //地图高度
    });

    ReactAPI.openConfirm({
        message: ReactAPI.international.getText("SESECD.custom.randon1576996807155"),
        buttons: [{
            operatetype: "yes",
            text: "确认",
            type: "primary",
            onClick: function onClick() {
                // TODO
                ReactAPI.request({
                    type: "post",
                    data: alarmDtos,
                    url: "/msService/SESECD/emcAction/CustomSESECDAlarmRecord/alarmTurnAccident"
                }, function (res) {
                    if (res.code != 200) {
                        ReactAPI.showMessage("f", res.message);
                        return;
                    }
                    "取消";
                    ReactAPI.closeConfirm();
                });
            }
        }, {
            operatetype: "cancel",
            text: ReactAPI.international.getText("SESECD.custom.randon1576928250949"),
            onClick: function onClick() {
                // TODO
                ReactAPI.closeConfirm();
            }
        }]
    });

    //    ReactAPI.createDialog("accidentHandleDialog", {
    //        title: ReactAPI.international.getText("SESECD.custom.randon1576996757145"),
    //        isRef: false,
    //        width: "400px",
    //        height: "300px",
    //        htmlDom: "<span>" + ReactAPI.international.getText("SESECD.custom.randon1576996807155") + "</span>",
    //        buttons: [{
    //            text: ReactAPI.international.getText("SESECD.custom.randon1576928233503"),
    //            type: "primary",
    //            onClick: function(event) {
    //                ReactAPI.request({
    //                    type: "post",
    //                    data: alarmDtos,
    //                    url: "/msService/SESECD/emcAction/CustomSESECDAlarmRecord/alarmTurnAccident",
    //                },
    //                function(res) {
    //                    if (res.code != 200) {
    //                        ReactAPI.showMessage("f", res.message);
    //                        return;
    //                    }
    //                    ReactAPI.showMessage("s", ReactAPI.international.getText("SESECD.custom.randon1577000711699"));
    //                    setTimeout(function() {
    //                        ReactAPI.destroyDialog("accidentHandleDialog");
    //                    },
    //                    3000);
    //                });
    //            }
    //        },
    //        {
    //            text: ReactAPI.international.getText("SESECD.custom.randon1576928250949"),
    //            onClick: function(){
    //                ReactAPI.destroyDialog("accidentHandleDialog");
    //            }
    //        },
    //        ]
    //    });
}

//进入应急处置
function enterAction() {
    debugger;
    var _gridId = "SESECD_1.0.0_alarmRecord_alarmHandleList_almAlarmRecord_sdg";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
    var rows = grid.getSelecteds();
    if (rows.length != 1) {
        ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
        return;
    }
    var selected = rows[0];
    var isIncident = rows[0].isIncident;
    var isOver = rows[0].isOver;

    if (isOver === true) {
        ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1587462895468"));
        return;
    }

    if (isIncident === true) {
        var id = selected.id;
        var code = 'SESECD_1.0.0_ecdPanel_gisEcd_self';
        var __pc__ = ReactAPI.getPowerCode(code);
        //var url = "http://" + window.location.host + "/msService/SESECD/ecdPanel/ecdCommom/gisEcd?__pc__=" + __pc__[code] + "&eventId=" + id;
        var url = "/msService/SESGISConfig/themeConfig/themeLayers/index?themeCode=newEmeCommand&eventId=" + id;
        window.open(url, "_blank");
    } else {
        ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1583676739130"));
    }
}

//接警确认
function alarmCheck() {
    console.log("接警确认---");
    var _gridId = "SESECD_1.0.0_alarmRecord_alarmHandleList_almAlarmRecord_sdg";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
    var rows = grid.getSelecteds();
    console.log(rows);
    if (rows.length == 0 || rows.length > 1) {
        ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
        return;
    }
    if (rows[0].isIncident === true) {
        ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1601456638419"));
        return;
    }

    ///msService/SESECD/alarmRecord/almAlarmRecord/alarmCheck

    ReactAPI.createDialog("xxxxx", {
        title: ReactAPI.international.getText("SESECD.custom.random1692181232386"),
        url: "/msService/SESECD/alarmRecord/almAlarmRecord/alarmCheck?id=" + rows[0].id,
        isRef: false,
        width: "510px",
        height: "430px",
        buttons: [{
            text: ReactAPI.international.getText("SESECD.custom.randon1576928233503"),
            type: "primary",
            onClick: function onClick(event) {
                //批量处警，将处理状态和处理记录回填到相应的接警中
                console.log("------------事件后");
                console.log(event);
            }
        }, {
            text: ReactAPI.international.getText("SESECD.custom.randon1576928250949"),
            onClick: function onClick() {
                ReactAPI.destroyDialog("alarmHandleDialog");
            }
        }]
    });
}