function loadPage() {

  var param = ReactAPI.getParamsInRequestUrl();
  if (param.id) {

    ReactAPI.request({
      type: "post",
      url: "/msService/SESECD/alarmRecord/alarmRecord/getMsgDataByEventId?id=" + param.id
    }, function (res) {
      console.log(res);
      if (res.code == 200) {
        ReactAPI.getComponentAPI("Input").APIs("msgTitle").setValue(res.data.msgTitle);
        ReactAPI.getComponentAPI("TextArea").APIs("msgContent").setValue(res.data.msgContent);
      } else {}
    });
  }
}