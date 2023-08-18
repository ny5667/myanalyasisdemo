function customRenderOver() {

    ReactAPI.request({
        type: "get",
        data: {},
        url: "/msService/SESCCTV/monitor/QueryRecordTypeBusiness?type=ECD&business=" + ReactAPI.getParamsInRequestUrl().id
    }, function (res) {
        if (res.code != 200) {
            return;
        }
        if (res.result.length == 0) {
            return;
        }
        var grid = ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmHandleViewdg1600177870401");
        grid.deleteLine();
        grid.addLine(res.result);
        for (var i = 0; i < res.result.length; i++) {
            ReactAPI.getComponentAPI("SupDataGrid").APIs("SESECD_1.0.0_alarmRecord_alarmHandleViewdg1600177870401").setFormatHtml(i, "sort", '<a href="javascript:void(0)" onclick="openVideoWindow(\'' + res.result[i].hls + '\')"><span style="color:blue;">' + ReactAPI.international.getText("SESECD.custom.randon1600973304988") + '</span></a>');
        }
    });
}

function openVideoWindow(videoUrl) {

    console.log(videoUrl);
    sessionStorage.setItem('videoUrl', videoUrl);
    window.open("/msService/SESCCTV/channelConfig/channelConfig/videoViewByUrl");
}