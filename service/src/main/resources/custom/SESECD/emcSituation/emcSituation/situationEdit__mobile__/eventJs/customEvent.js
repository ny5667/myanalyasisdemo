//======================================================================================
//视图的事件，请不要在这里修改代码，修改的代码会被覆盖
//======================================================================================

/*==================================onload事件==================================*/
var SESECD_emcSituation_emcSituation_onload = function(){
	$(".m-user-info").hide();
$(".am-flexbox")[0].style.display='none';
$(".am-flexbox")[1].style.display='none';
var url = window.location.href;
debugger;
function getUrlParameter(url, name) {
  name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
  var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
  var results = regex.exec(url);
  return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

var eventId = getUrlParameter(url, 'eventId');
var eventname = getUrlParameter(url, 'eventname');
var data = {
	accidentName:eventname,
	id:eventId
};
if(eventId==null || eventId=='' || eventname==null || eventname==''){
	return;
}
ReactAPI.getComponentAPI().Reference.APIs('emcSituation.eventId.accidentName').setValue(data);
}

/*==================================onsave事件==================================*/
var SESECD_emcSituation_emcSituation_onsave = function(){
	
}

