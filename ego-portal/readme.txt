1.本地json数据(固定不变)用于测试，直接使用ajax请求json数据。返回json数据渲染界面。获取项目本地json数据，不需要走controller
2.使用http请求数据(跨域)，客户端能接受数据，但是渲染时前端界面报错：
Failed to load http://***** No 'Access-Control-Allow-Origin' header is present on the requested resource. Origin 'http://127.0.0.1:8082' is therefore not allowed access.
3.解决办法
	1)、利用script标签(被浏览器允许跨域请求)，请求js数据，利用回调函数解析数据
	2)、服务器端解决，利用HttpClient
通俗说法：跨域分前台解决和后台解决，前台如果发送jsonp、http请求则后台不可以直接返回json数据，(前台知道要跨域，所以不接收json数据)，需要修改格式为
带回调函数的js数据，用于页面渲染。而使用json请求，浏览器默认不跨域，后台使用httpClient解决跨域请求(返回json数据)。

*****************************************************
处理购物车中的商品时，可能会出现url json转换错误，要忽略掉image***
重新写一个类，作为存放在购物车中的商品类************************
*****************************************************