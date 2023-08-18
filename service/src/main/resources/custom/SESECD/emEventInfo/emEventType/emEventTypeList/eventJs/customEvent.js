//======================================================================================
//视图的事件，请不要在这里修改代码，修改的代码会被覆盖
//======================================================================================

/*==================================helpInfo事件==================================*/


/*==================================onload事件==================================*/
var SESECD_emEventInfo_emEventType_onload = function(){
	//写在onload 中
$(document).ready(function(){
	// 按钮id
  $("#btn-update").click(function(){
	return isInitialize();
  });
	//
  $("#btn-delete").click(function(){
	return isInitialize();
  });
});

//是否初始化数据
function  isInitialize(){
	var data = ReactAPI.getComponentAPI().SupDataGrid.APIs('SESECD_1.0.0_emEventInfo_emEventTypeList_emEventType_sdg').getSelecteds()[0];
	if(data !== null || data !== undefined){
		if(data.isInitialize){
			ReactAPI.showMessage('w','这条数据是初始化默认数据，禁止修改或删除');
			return false;
		}
	}
	return true;
}
}

