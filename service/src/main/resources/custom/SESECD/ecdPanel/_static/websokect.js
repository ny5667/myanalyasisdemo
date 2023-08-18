const existedSocketMap = new Map();  // 已经建立连接的socket
 
 /**
  * 全局websocket 创建类，内含断线、异常重连机制
  */
 class MsgWebSocket {
   constructor(websocketUrl, msgCallback, socketName){
     this.socketUrl = websocketUrl.slice(0, 1) === '/' ? websocketUrl : `/${websocketUrl}`;
     this.msgCallback = msgCallback;
     this.ip_address = "10.10.40.32";
     this.socketName = socketName;   // 必须是英文 用来判断当前的 websocket 是否已经建立
     this.socketInstance = null;
     this.limitConnect = 10;  // 断线重连次数
     this.timeConnect = 0;
     this.timerHeart = null;
     this.wsUrl = "";
     this.initUrl();
   }
   initUrl() {
    this.serverUrl = window.location.hostname === 'localhost' ? `http://${this.ip_address}:8080` : `${window.location.protocol}//${window.location.host}`;//平台地址
    if(window.location.hostname === 'localhost'){
      this.wsUrl = `ws://${this.ip_address}:8080`;
    } else {
      if(window.location.protocol === "https:"){
        this.wsUrl = `wss://${window.location.host}`;
      } else {
        this.wsUrl = `ws://${window.location.host}`;
      }
    }
  }
   initSocket() {
     console.log(`${this.socketName} websocket 开始初始化...`);
     if ('WebSocket' in window) {
       this.socketInstance = new WebSocket(`${this.wsUrl}${this.socketUrl}?token=${localStorage.getItem('ticket')}`);
       existedSocketMap.set(this.socketName, true);
     } else {
       console.error("当前浏览器不支持websocket通信，请检查浏览器版本");
       return null;
     }
 
     this.socketInstance.onopen = () => {
       console.log(`${this.socketName} websocket 连接成功`);
     };
 
     this.socketInstance.onmessage = e => {
       this.msgCallback(e);
     };
 
     this.socketInstance.onclose = () => {
       console.log(`${this.socketName} websocket 连接关闭`);
       if(this.timerHeart) {
         clearInterval(this.timerHeart);
       }
       this.reconnect();  // 开始重连
     };
 
     this.socketInstance.onerror = () => {
       console.log(`${this.socketName} websocket 通信发生错误`);
       this.reconnect();
     };
 
     window.onbeforeunload = () => {
       this.socketInstance.close();
       existedSocketMap.delete(this.socketName);
     };
     this.timerHeart = setInterval(()=>{
       this.socketInstance.send(`${this.socketName} socket 一分钟一次的心跳...`);
     }, 1000*8);
     return this.socketInstance;
   }
 
   reconnect() {
     console.log(`${this.socketName} websocket 开始重连...`);
     // lockReconnect加锁，防止onclose、onerror两次重连
     if(this.limitConnect > 0){
       if(!sessionStorage.getItem('lockReconnect')){
         sessionStorage.setItem("lockReconnect", 1);
         this.limitConnect--;
         this.timeConnect++;
         console.log(`${this.socketName} websocket 第${this.timeConnect}次重连...`);
         // 进行重连
         setTimeout(()=>{
           sessionStorage.removeItem("lockReconnect");
           this.initSocket();
         }, 1000*10);
       }
     } else {
       console.log(`${this.socketName} websocket连接超时，重连终止！`);
     }
   }
 }
 /**
  * 创建websocket
  * @param {*} websocketUrl websocket地址
  * @param {*} msgCallback 收到消息回调函数
  * @param {*} socketName websocket名称
  */
 export const createSocket = (websocketUrl, msgCallback, socketName) =>{
   if(existedSocketMap.get(socketName)) return;
   new MsgWebSocket(websocketUrl, msgCallback, socketName).initSocket();
 };