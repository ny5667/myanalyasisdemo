//刷新应急行动列表
function listFlush(){
    ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_emcAction_actionList_emcAction_sdg").refreshDataByRequst({
      type: "post",
      url: "/msService/SESECD/emcAction/emcAction/actionList-query",
      param: {}
    });//重新刷新应急事件列表
 }
 
 //查看应急行动
 function actionView(){
    var _gridId = "SESECD_1.0.0_emcAction_actionList_emcAction_sdg";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
    var rows = grid.getSelecteds();
    if(rows.length!=1){
          ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
          return;
     }
     var code = 'SESECD_1.0.0_emcAction_actionList_self'
     var __pc__ = ReactAPI.getPowerCode(code);
     var url = "http://"+window.location.host+"/msService/SESECD/emcAction/emcAction/actionView?__pc__="+__pc__[code]+"&id="+rows[0].id + "&viewCode=SESECD_1.0.0_emcAction_actionList&entityCode=SESECD_1.0.0_emcAction&iscrosscompany=false&openType=frame&viewType=view";
         window.open(url,"_blank");
 }
 
 
 //修改应急行动
 function actionEdit(){
    var _gridId = "SESECD_1.0.0_emcAction_actionList_emcAction_sdg";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
    var rows = grid.getSelecteds();
    if(rows.length!=1){
          ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
          return;
     }
     var code = 'SESECD_1.0.0_emcAction_actionList_self'
     var __pc__ = ReactAPI.getPowerCode(code);
     var url = "http://"+window.location.host+"/msService/SESECD/emcAction/emcAction/actionEdit?__pc__="+__pc__[code]+"&id="+rows[0].id + "&viewCode=SESECD_1.0.0_emcAction_actionList&entityCode=SESECD_1.0.0_emcAction&iscrosscompany=false&openType=frame&viewType=edit";
         window.open(url,"_blank");
 }
 
 //跟踪
 function actionTrack(){
     var _gridId = "SESECD_1.0.0_emcAction_actionList_emcAction_sdg";
     var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
     var rows = grid.getSelecteds();
     if(rows.length!=1){
           ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
           return;
     }
       var cid = ReactAPI.getUserInfo().company.id;
       if(cid != rows[0].cid){
       ReactAPI.showMessage('w',"不允许修改非本公司数据!");
       return false;
     }
     var ids = new Array();//组织接警记录ID
     for(var i=0;i<rows.length;i++){
         var actionState = rows[i].actionState.id;
         if(actionState==="SESECD_actionState/003"){
           ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1577084065733") + (rows[i].rowIndex +1) + ReactAPI.international.getText("SESECD.custom.randon1577084139417") +  ReactAPI.international.getText("SESECD.custom.randon1577175249378"));
           return;
         }
         ids.push(rows[i].id)
     }
     console.log(ids)
     ReactAPI.createDialog("actionTrackDialog", {
       title: ReactAPI.international.getText("SESECD.custom.randon1577173842042"),
       url: "/msService/SESECD/emcAction/emcAction/actionTrackEdit?id=" +ids[0],
       isRef: false, 
       width:"500px",
       height:"430px",
       buttons: [
         {
           text: ReactAPI.international.getText("SESECD.custom.randon1576928233503"),
           type: "primary",
           onClick: function(event) {//批量处警，将处理状态和处理记录回填到相应的接警中
             var actionState = event.ReactAPI.getComponentAPI("SystemCode").APIs("emcAction.actionState").getValue().value;
             var trackRecord = event.ReactAPI.getComponentAPI("TextArea").APIs("emcAction.trackRecord").getValue();
             if(!actionState){
                 ReactAPI.showMessage("f", ReactAPI.international.getText("SESECD.custom.randon1577174873562"));
                 return;
             }
             if(!trackRecord){
                 ReactAPI.showMessage("f", ReactAPI.international.getText("SESECD.custom.randon1577174921050"));
                 return;
             }
             ReactAPI.request(
                {
                  type: "post",
                  data: {
                      ids:ids,//接警IDS
                      actionState:actionState,//处理状态
                      trackRecord:trackRecord,//处理记录
                  },
                  url: "/msService/SESECD/emcAction/emcAction/trackAction",
                },
                function(res) {
                  console.log(res)
                  if(res.data==true){
                     ReactAPI.showMessage("s", ReactAPI.international.getText("SESECD.custom.randon1577173985754"));
                     ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId).refreshDataByRequst({
                       type: "post",
                       url: "/msService/SESECD/emcAction/emcAction/actionList-query",
                       param: {}
                     });//重新刷新接警记录列表
                     ReactAPI.destroyDialog("actionTrackDialog");
                  }
                }
              );
           }
         },
         {
           text: ReactAPI.international.getText("SESECD.custom.randon1576928250949"),
           onClick: event => {
             ReactAPI.destroyDialog("actionTrackDialog");
           }
         },
       ]
     });
 
 }
 //重写应急行动列表双击事件
 function actionDbClick(event,row){
   var cid = ReactAPI.getUserInfo().company.id;
   if(cid != row.cid){
       ReactAPI.showMessage('w',"不允许修改非本公司数据!");
       return false;
     }
   var actionState = row.actionState.id;
   if(actionState==="SESECD_actionState/003"){
       ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1577168682463"));
       return;
   }
   var id = row.id;
   var _gridId = "SESECD_1.0.0_emcAction_actionList_emcAction_sdg";
   var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
   var code = 'SESECD_1.0.0_emcAction_actionList_self'
   var __pc__ = ReactAPI.getPowerCode(code);
   var url = "http://"+window.location.host+"/msService/SESECD/emcAction/emcAction/actionEdit?__pc__="+__pc__[code]+"&id="+id + "&viewCode=SESECD_1.0.0_emcAction_actionList&entityCode=SESECD_1.0.0_emcAction&iscrosscompany=false&openType=frame&viewType=edit";
   window.open(url,"_blank");
 }
 
 //重写平台修改按钮
 function actionEdit(){
  
    var _gridId = "SESECD_1.0.0_emcAction_actionList_emcAction_sdg";
    var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs(_gridId);
    var rows = grid.getSelecteds();
    if(rows.length!=1){
          ReactAPI.showMessage("w", ReactAPI.international.getText("ec.common.checkselected"));
          return;
     }
       //判断数据是否为当前公司
     var cid = ReactAPI.getUserInfo().company.id;
       if(cid != rows[0].cid){
       ReactAPI.showMessage('w',"不允许修改非本公司数据!");
       return false;
     }
   
     var actionState = rows[0].actionState.id;
     if(actionState==="SESECD_actionState/003"){
       ReactAPI.showMessage("w", ReactAPI.international.getText("SESECD.custom.randon1577168682463"));
       return;
     }
     var code = 'SESECD_1.0.0_emcAction_actionList_self'
     var __pc__ = ReactAPI.getPowerCode(code);
     var url = "http://"+window.location.host+"/msService/SESECD/emcAction/emcAction/actionEdit?__pc__="+__pc__[code]+"&id="+rows[0].id + "&viewCode=SESECD_1.0.0_emcAction_actionList&entityCode=SESECD_1.0.0_emcAction&iscrosscompany=false&openType=frame&viewType=edit";
     window.open(url,"_blank");
 }