# 将token放入到请求头中

- 使用AfterScript 将token放入到请求头中

```javascript
var code = ke.response.data.code;
if (code == 200) {
    //判断,如果服务端响应code是8200才执行操作
    //获取token
    var token = ke.response.data.data.tokenValue;
    //2、如果全局参数是query类型,则设置当前逻辑分组下的全局Parameter,开发者自行选择
    ke.global.setAllHeader("Authorization", "Bearer " + token);
}
```