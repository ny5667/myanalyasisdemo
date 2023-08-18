var globalHandle = {
  getCookie: function(name) {
    name = name + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
         }
         if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
         }
     }
    return undefined;
  },
  setCookie:function(key, value, exp) {
    if (key) {
      if (exp instanceof Date) {
        document.cookie = escape(key) + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/"
        var expires = "expires="+ exp.toUTCString();
        document.cookie = key + "=" + value + ";" + expires + ";path=/";
      }
      else {
        document.cookie = key + "=" + value+ ";path=/"
      }
    }
  },
  appendTheme: function() {
    var publicPath = window.publicPathString;
    var theme = globalHandle.getCookie('theme');
    theme = theme ? theme : 'default'
    var link = document.createElement('link');
    link.type = 'text/css';
    link.id = 'theme-' + theme;
    link.rel = 'stylesheet';
    link.href = publicPath + 'theme/supplant-app-' + theme + '.css';
    document.getElementsByTagName('head')[0].appendChild(link);
  },
}

globalHandle.appendTheme();
