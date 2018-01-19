# apollo接口文档

#除了注册和登录都加token认证

## 1.用户

### 1.1 用户注册

* __Method__
  POST

* __URL__
  api/app/user/register

* __Request__

> 请求参数Json格式

```
{
 "userId":"1231441534wqe1",  //uuid
 "mobileNum":"136966551", //手机号码
 "operator":"yanglong",  //运营商
 "netSystem":"wififf",//网络制式
 "password":"123", //密码
 "uName":"yanglong"  //用户名
}
```

* __response__

> 成功

```
{
    "code": 0,
    "data": null
    
}
```

> 失败

```
{
  "code":2,
  "reason":"参数错误"
}
```


### 1.2 用户登录

* __Method__
  POST

* __URL__
  api/app/user/login

* __Request__

Param | Type | Description 

mobileNumAndUname | String | 手机号码或者用户名
password | string | 密码

> 请求参数Json格式

```
{
 "devId":"1231441534wqe1", //uuid
 "devImei":"13696655620", //手机imei
 "devBrand":"华为荣耀7", //手机品牌
 "devModel":"qwe", //设备型号
 "androidApiVer":"123", //安卓版本
 "factoryOs":"yanglong", //厂商定制os
 "factoryOsVer":"yanglong", //厂商定制os版本

}
```

* __response__

> 成功

```
{
  "code": 0,
  "data": {
    "userId": "1231441534wqe1",
    "mobileNum": "13696655620",
    "operator": "yanglong",
    "netSystem": "wififf",
    "password": "$2a$10$kJjIyIMJ0E8sOMyGtZRvOOtzGuSpU5vFOXCEmKImdADnXqDcrI3dW",
    "uName": "yanglong",
    "uSex": 0,
    "uAddress": null,
    "uBirthdate": null,
    "uRegDate": 1504446396000,
    "uState": 1,
    "devId": "1231441534wqe1",
    "devImei": "13696655620",
    "devBrand": "华为荣耀7",
    "devModel": "qwe",
    "androidApiVer": 123,
    "factoryOs": "yanglong",
    "factoryOsVer": "yanglong",
    "dMobilenum": null,
    "dLogintime": null,
    "dState": null,
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MDQ0NDcyNTIsInN1YiI6IntcInVzZXJuYW1lXCI6XCJ5YW5nbG9uZ1wiLFwiaWRcIjpcIjEyMzE0NDE1MzR3cWUxXCJ9IiwiZXhwIjoxNTA1MjkwOTMwfQ.f6cHFLJWMcjIO3cfkuzVIhZxPURSXJLgG4px5dnDBHw"
  }
}
```

> 失败

```
{
  "code":2,
  "reason":"参数错误"
}
```


## 2.测试

### 2.1 创建测试

* __Method__
  POST

* __URL__
  api/app/netTestLog

* __Request__

> 请求参数Json格式

```
{
 "testId":"1369665562011", 
 "testTime":"1234213", 
 "testEvent":"1", 
 "userName":"qwe",
 "mobileNum":"1234",
 "deviceImei":"123123",
  "netType":"123",
 "operator":"yanglong",
  "ipAddr":"123",
 "ssid":"yanglong",
  "gps":"123123",
  "mcc":"123",
 "mnc":"yanglong",
   "lac":"123123",
  "cid":"123",
 "bsss":"yanglong",

   "neighborStationInfo":"123123",
  "transSpeedUp":"123",
 "transSpeedDown":"1",
  "pakageLossProb":"123123",
  "aveTransDelay":"123",
 "transJitterP":"1"，
    "transJitterN": null,
     "errType": null,
     "manualClick": null,
     "screenShap": null,
     "devNetspeed": null，
     "isFrontApp": null，
     "appName": null
}
```

* __response__

> 成功

```
{
  "code": 0,
  "data": {
    "testId": "1369665562011",
    "testTime": 1234213,
    "testEvent": 1,
    "userName": "qwe",
    "mobileNum": "1234",
    "deviceImei": "123123",
    "netType": "123",
    "operator": "yanglong",
    "ipAddr": "123",
    "ssid": "yanglong",
    "gps": "123123",
    "mcc": "123",
    "mnc": "yanglong",
    "lac": "123123",
    "cid": "123",
    "bsss": "yanglong",
    "neighborStationInfo": "123123",
    "transSpeedUp": 123,
    "transSpeedDown": 1,
    "pakageLossProb": 123123,
    "aveTransDelay": 123,
    "transJitterP": 1,
    "transJitterN": null,
    "errType": null,
    "manualClick": null,
    "screenShap": null,
    "devNetspeed": null
  }
}
```

> 失败

```
{
  "code":2,
  "reason":"参数错误"
}
```


### 2.2 创建运行进程

* __Method__
  POST

* __URL__
  api/app/appRun

* __Request__


> 请求参数Json格式

```
[{
 "testId":"13696655620", 
 "checkTime":"13696655620", 
 "userId":"1231441534wqe1", 
 "devId":"1231441534wqe1",
 "appName":"123",
 "appPackage":"yanglong1",
  "appVer":"123",
 "isFrontApp":"1"  1代表前台 2代表其他
},
{
 "testId":"13696655620", 
 "checkTime":"13696655620", 
 "userId":"1231441534wqe1", 
 "devId":"1231441534wqe1",
 "appName":"123",
 "appPackage":"yanglong",
  "appVer":"123",
 "isFrontApp":"1"
}
]
```

* __response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

> 失败

```
{
  "code":2,
  "reason":"参数错误"
}
```



### 2.3 屏幕截图

* __Method__
  POST

* __URL__
  api/app/netTestLog/{testId}

* __Request__

Param | Type | Description 

testId | String | 测试Id


* __response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

> 失败

```
{
  "code":2,
  "reason":"参数错误"
}
```

### 2.4 添加吐槽

* __Method__
  POST

* __URL__
  api/app/netTestLog/describe

* __Request__

Param | Type | Description 

testId | String | 测试Id

describe | String | 吐槽的内容


* __response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

> 失败

```
{
  "code":2,
  "reason":"参数错误"
}
```

## 3 app安装

### 3.1 创建app安装

* __Method__
  POST

* __URL__
  api/app/appInstall

* __Request__


> 请求参数Json格式

```
[{ 
 "checkTime":"13696655620", 
 "userId":"1231441534wqe1", 
 "devId":"1231441534wqe1",
 "appName":"123",
 "appPackage":"yanglong1",
  "appVer":"123"
},
{ 
"checkTime":"13696655620", 
  "userId":"1231441534wqe1", 
  "devId":"1231441534wqe1",
  "appName":"123",
  "appPackage":"yanglong2",
   "appVer":"123"
}
]
```

* __response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

> 失败

```
{
  "code":2,
  "reason":"参数错误"
}
```



## 4 网络速度

### 4.1 创建网络速度

* __Method__
  POST

* __URL__
  api/app/network

* __Request__


> 请求参数Json格式

```
[{
  "userId":"13696655620", 
 "timeStamp":"1234567123", 
 "uploadMaximum":"100", 
 "uploadMinimum":"1",
 "uploadAvg":"50", 
  "downMaximum":"100", 
  "downMinimum":"1",
  "downAvg":"50",
    "netType":"123",
   "operator":"yanglong",
  "appPackages":"钉钉,微信,QQ"
},
{
  "userId":"13696655620", 
 "timeStamp":"123456789123", 
 "uploadMaximum":"100", 
 "uploadMinimum":"1",
 "uploadAvg":"50", 
  "downMaximum":"100", 
  "downMinimum":"1",
  "downAvg":"50",
    "netType":"123",
   "operator":"yanglong",
  "appPackages":"钉钉,微信,QQ"
}
]
```

* __response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

> 失败

```
{
  "code":2,
  "reason":"参数错误"
}
```




