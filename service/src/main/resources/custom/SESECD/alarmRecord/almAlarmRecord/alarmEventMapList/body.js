//重写列表的双击事件，选择是应急事件并且未结束的应急事件
function getEventId(event,row){
    window.parent._connectEventInterface(row.id);
    baseEventId = row.id;
}

