//======================================================================================
//视图的事件，请不要在这里修改代码，修改的代码会被覆盖
//======================================================================================

/*==================================afterSave事件==================================*/


/*==================================afterSubmit事件==================================*/


/*==================================beforeSave事件==================================*/


/*==================================beforeSubmit事件==================================*/


/*==================================onload事件==================================*/
var SESECD_tagConfig_tagConfig_onload = function(){
	
}

/*==================================onsave事件==================================*/
var SESECD_tagConfig_tagConfig_onsave = function(){
	var a = ReactAPI.getComponentAPI().InputNumber.APIs("tagConfig.upperLimit").getValue();
var b = ReactAPI.getComponentAPI().InputNumber.APIs("tagConfig.lowerLimit").getValue();
if(a < b){
     ReactAPI.showMessage("f", "下限应小于上限！");
   return false;

}
}

