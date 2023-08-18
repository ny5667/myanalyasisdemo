//======================================================================================
//视图的事件，请不要在这里修改代码，修改的代码会被覆盖
//======================================================================================

/*==================================onchange='onchangeTimeString(obj)'事件==================================*/
function onchangeTimeString(obj){
   accidentNameOnChange(obj);
}

/*==================================afterSave事件==================================*/


/*==================================afterSubmit事件==================================*/


/*==================================beforeSave事件==================================*/


/*==================================beforeSubmit事件==================================*/


/*==================================onload事件==================================*/
var SESECD_emcAction_emcAction_onload = function(){
	onLoad();
var aaa = ReactAPI.getComponentAPI().SystemCode.APIs('emcAction.actionState').getValue().value;
if(aaa=="SESECD_actionState/003"){ ReactAPI.getComponentAPI("SystemCode").APIs("emcAction.actionState").setReadonly(true);
}
}

/*==================================onsave事件==================================*/
var SESECD_emcAction_emcAction_onsave = function(){
	debugger
ReactAPI.destroyDialog("actionEmcDialog");
}

