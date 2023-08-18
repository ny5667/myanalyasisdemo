var baseEventId;
//重新渲染应急预案的指令
function getCommomInfo(eventId){
    if(eventId != undefined && eventId != "" &&  eventId !=null){
     
        baseEventId = eventId;
    	ReactAPI.request(
           {
             type: "get",
             data: {},
             url: "/msService/SESECD/alarmRecord/alarmRecord/getCommomInfo?eventId=" + eventId,
           },
           function(res) {
              if(res.code == 200){
                  var data = res.data.result;
                  if(data){
                      var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_ecdPanel_ecdCommomList_ecdCommom_sdg");
                      var Arry = new Array(); 
                      for(var i =0;i<data.length;i++){
                        var value = {
                            commomState:{
                              id:data[i].commomStateId,
                              value:data[i].commomStateValue
                            },
                            actionName:data[i].actionName,
                            actionDescription:data[i].actionDescription,
                            actionCatogory:{
                                id:data[i].actionCatogoryId,
                                value:data[i].actionCatogoryValue
                            },
                            actionAddress:data[i].actionAddress,
                            commomId:{
                                id:data[i].commomId
                            },
                            eventId:{
                                id:data[i].eventId
                            },
                            emcPlanId:{
                                id:data[i].planId
                            },
                            owners:data[i].owners,
                            isMapPoint:data[i].isMapPoint,
                            mapPoint:data[i].mapPoint
                        }
                        Arry.push(value);
                      }
                      grid.addLine(Arry);
                  }
              }
           }
        );
    }
    
}


//预案点击链接
function showFormatFunc(value,nRow){
  return '<a href="javascript:void(0)" onclick="viewCommomDetail()"><span style="color:blue;">' + value + '</span></a>';
};

//浏览
function viewCommomDetail(){
	var _gridId = "SESECD_1.0.0_ecdPanel_ecdCommomList_ecdCommom_sdg";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
       var rows = grid.getSelecteds();
       if(rows.length!=1){
          ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
          return;
    }
    var code = 'SESWssEP_1.0.0_emergencyPlan_emergencyPlanList_self'
    var __pc__ = ReactAPI.getPowerCode(code);
    var url = "/msService/SESWssEP/emergencyPlan/emergencyPlan/emergencyPlanMapView?__pc__="+__pc__[code]+"&id="+rows[0].emcPlanId.id + "&viewCode=SESWssEP_1.0.0_emergencyPlan_emergencyPlanList&entityCode=SESWssEP_1.0.0_emergencyPlan&openType=frame&viewType=edit";
	ReactAPI.createDialog("emcPlanDialog", {
      title: ReactAPI.international.getText("SESECD.custom.randon1578448051183"),
      isRef: false, 
      width:"800px",
      height:"550px",
      url: url,
      buttons: [
        {
          text: ReactAPI.international.getText("Button.text.close"),
          onClick: event => {
            ReactAPI.destroyDialog("emcPlanDialog");
          }
        },
      ]
    });
}

//编辑
function editCommomDetail(){
	var _gridId = "SESECD_1.0.0_ecdPanel_eventPanelExtradg1578378703400";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
       var rows = grid.getSelecteds();
       if(rows.length!=1){
          ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
          return;
    }
	if(rows[0] && rows[0].commomState){
		if(rows[0].commomState.id!="SESECD_commonState/001"){
			ReactAPI.showMessage("w", rows[0].commomState.value + ReactAPI.international.getText("SESECD.custom.randon1578480398537"));
			return;
		}
	}
    var code = 'SESECD_1.0.0_alarmRecord_alarmRecordList_self'
    var __pc__ = ReactAPI.getPowerCode(code);
    var url = "/msService/SESECD/alarmRecord/alarmAction/alarmCommomEdit?__pc__="+__pc__[code]+"&id="+rows[0].commomId.id + "&viewCode=SESECD_1.0.0_alarmRecord_alarmCommomEdit&entityCode=SESECD_1.0.0_alarmRecord&openType=frame&viewType=edit";
	ReactAPI.createDialog("commomDialog", {
      title: ReactAPI.international.getText("SESECD.custom.randon1578462908320"),
      isRef: false, 
      width:"510px",
      height:"430px",
      url: url,
      buttons: [
        {
          text: ReactAPI.international.getText("SESECD.custom.randon1576928233503"),
          type: "primary",
          onClick: function(event) {//指令保存
            event.ReactAPI.submitFormData("save", function(res) {
              ReactAPI.destroyDialog("commomDialog");
              datasFlush();
            });
          }
        },
        {
          text: ReactAPI.international.getText("SESECD.custom.randon1576928250949"),
          onClick: event => {
            ReactAPI.destroyDialog("commomDialog");
          }
        },
      ]
    });
}

//下达
function releaseCommom(){
	var _gridId = "SESECD_1.0.0_ecdPanel_ecdCommomList_ecdCommom_sdg";
	var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
	var rows = grid.getSelecteds();
	if(rows.length==0){
		ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
		return;
	}

	var result = ReactAPI.request({
	  type: "get",
	  data: {},
	  url: "/msService/SESECD/emcAction/emcAction/judgeDispatcher",
	  async: false
	});
	if(!result.data){
		ReactAPI.showMessage("w", "当前登录人不是调度员");
		return;
	}

    var array = new Array();
	for(var i=0;i<rows.length;i++){

	   // if(rows[i].owners === null || rows[i].owners === undefined){
	   //     ReactAPI.showMessage("w", "当前登录人不是调度员");
	   //     return;
	  //  }
	    if(rows[i].commomState && rows[i].commomState.id!="SESECD_commonState/001"){
			ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1578449309161"));
			return;
	    }
//	    if(rows[i].isMapPoint != undefined && rows[i].isMapPoint === false){
//	        ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1588256663997"));
//	        return;
//	    }

		array.push(Number(rows[i].commomId.id));
	}
	ReactAPI.createDialog("commomReleaseDialog", {
      title: ReactAPI.international.getText("SESECD.custom.randon1578479471570"),
      isRef: false, 
      width:"300px",
      height:"200px",
      htmlDom: "<span>"+ ReactAPI.international.getText("SESECD.custom.randon1578479513360") +"</span>",
      buttons: [
        {
          text: ReactAPI.international.getText("SESECD.custom.randon1578479688651"),
          type: "primary",
          onClick: function(event) {//指令下达
		      ReactAPI.request(
				 {
				   type: "post",
				   data: array,
				   url: "/msService/SESECD/alarmRecord/alarmRecord/releaseCommom",
				 },
				 function(res) {
					if(res.code == 200){
						ReactAPI.destroyDialog("commomReleaseDialog");
						datasFlush();
						flushActionDatas();
					}else{
                        ReactAPI.showMessage("w", res.msg);
					}
				 }
			  );
          }
        },
        {
          text: ReactAPI.international.getText("SESECD.custom.randon1576928250949"),
          onClick: event => {
            ReactAPI.destroyDialog("commomReleaseDialog");
          }
        },
      ]
    });
}





//刷新
function datasFlush(){
	var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_ecdPanel_ecdCommomList_ecdCommom_sdg");
	grid.deleteLine();
	var eventId = baseEventId;
	getCommomInfo(eventId);
}

//坐标录入
function addPoint(){
	var selectRow = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_ecdPanel_eventPanelExtradg1578378703400").getSelecteds();
	if(selectRow.length!=1){
		ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
	}else if(selectRow[0].commomState.id === 'SESECD_commonState/002'){
        ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1588954205177"));
        return;
	}else{
		var msg = {
          data:{
              instructionId:selectRow[0].commomId.id
          },
          type:"instruction"
		}
		window.parent.invokeLayerMethod('emeCommandAppLayer', '_insertCors', msg);
	}
}






//设置指令列表的单击事件
function setIntrocutClickEvent(){
	ReactAPI.getComponentAPI("SupDataGrid")
	  .APIs("SESECD_1.0.0_ecdPanel_ecdCommomList_ecdCommom_sdg")
	  .setClickEvt(function(e, data) {
        var msg = {
          data:{
              instructionId:data.commomId.id
          },
          type:"instruction"
		}
        window.parent.invokeLayerMethod('emeCommandAppLayer', '_connectEcdPlaneInterface', msg);
	  });
}



//获取ws的URL
var wsUrl = "";
function getWsUrl(){
	ReactAPI.request(
	   {
		 type: "get",
		 data: {},
		 url: "/msService/SesCommonFunc/getSesEcdWebSocketURL",
	   },
	   function(res) {
		  if(res.code == 200){
			wsUrl = 'ws://' + window.location.host + res.data;
			createWebsocket(wsUrl);
		  }
	   }
	);
}
//创建websocket
let timerHeart = null;
function createWebsocket(wsUrl){
	var ws = new WebSocket(`${wsUrl}?token=${localStorage.getItem('ticket')}`); 
	//申请一个WebSocket对象，参数是服务端地址，同http协议使用http://开头一样，WebSocket协议的url使用ws://开头，安全的WebSocket协议使用wss://开头
	ws.onopen = function(){
	　　//当WebSocket创建成功时，触发onopen事件
	    console.log("websocket connect success");
	　　//ws.send("hello"); //将消息发送到服务端
	}
	ws.onmessage = function(e){
	　　//当客户端收到服务端发来的消息时，触发onmessage事件，参数e.data包含server传递过来的数据
	　　console.log(e.data);
		flushDatasByWs(e.data);
	}
	ws.onclose = function(e){
	　　//当客户端收到服务端发送的关闭连接请求时，触发onclose事件
	　　console.log("close");
        if(timerHeart) {
           clearInterval(timerHeart);
         }
       	 reconnect(); 
	}
	ws.onerror = function(e){
	　　//如果出现连接、处理、接收、发送数据失败的时候触发onerror事件
	　　console.log(error);
        reconnect();
	}
    timerHeart = setInterval(()=>{
       ws.send(`socket 一分钟一次的心跳...`);
     }, 1000*8);
}

var limitConnect = 10;
var timeConnect = 0;
function reconnect() {
  console.log(`websocket 开始重连...`);
  // lockReconnect加锁，防止onclose、onerror两次重连
  if(limitConnect > 0){
    if(!sessionStorage.getItem('lockReconnect')){
      sessionStorage.setItem("lockReconnect", 1);
      limitConnect--;
      timeConnect++;
      console.log(`websocket 第${timeConnect}次重连...`);
      // 进行重连
      setTimeout(()=>{
        sessionStorage.removeItem("lockReconnect");
        createWebsocket(wsUrl);
      }, 1000*10);
    }
  } else {
    console.log(`websocket连接超时，重连终止！`);
  }
}

//websocket接收消息后刷新应急行动或者应急态势
function flushDatasByWs(wsMsg){
	if(wsMsg!= null && wsMsg != undefined && wsMsg != ""){
		try{
			var data = JSON.parse(wsMsg);
			var actionId = data.actionId;
			var situationId = data.situationId;
			var eventId = data.eventId;
			if(eventId && actionId){
				setTimeout(function(){
					flushActionDatas();//刷新应急行动数据
				},2000);
			}else if(eventId && situationId){
				setTimeout(function(){
					flushSituationDatas();//刷新应急态势数据
				},2000);
			}
		}catch(e){
			console.log('error');
		}
    }
}