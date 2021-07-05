
# nacos-ctl
# 简介
# 功能列表
以交互模式提供服务，可通过配置文件或启动参数设置初始信息
基本功能如下：

- 命名空间：
  - 增删查改
- 配置命令：
  - 增删查，列出前n条
- 服务命令：
  - 增删查改，列出前n条
- 实例命令：
  - 增删查改
- 系统开关：
  - 查，改



# 实现方式

数据获取的实现基于：

- Open-Api v1
- Prometheus Http
- Nacos Java SDK 1.4.2







# 功能效果

## 启动与参数设置

nacosctl以jar包形式部署，为方便启动，写了一个简易启动脚本，使用方式如下：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625478044505-351a8cf6-447a-40f5-a987-90358917f479.png#clientId=ue5ab1146-be40-4&from=paste&height=897&id=udce8339f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1794&originWidth=2480&originalType=binary&ratio=1&size=720419&status=done&style=none&taskId=u8e7591f0-7f86-42ab-9b54-da0e79ca045&width=1240)
启动会等待10秒左右，因为需要创建nacos-client中的service比较耗时，启动后可以看到提示语，参数配置情况，以及连接效果，看到输入栏提示后即可进行操作，按tab自动展示帮助，使用quit退出
初次连接会尝试使用配置中的信息进行鉴权，如果失败则会有提示，不过不影响后续正常启动（因为对于未启动权健的nacos-server，就算认证失败了也可以访问）
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625478128862-484b4690-37d3-43c7-8b83-62a49502a1c7.png#clientId=ue5ab1146-be40-4&from=paste&height=329&id=ub4f247c1&margin=%5Bobject%20Object%5D&name=image.png&originHeight=658&originWidth=1790&originalType=binary&ratio=1&size=249278&status=done&style=none&taskId=u53089eba-9821-4915-9496-6c1895ec418&width=895)
nacosctl中自带了一套基本的配置，启动后会自动以nacos初始账号密码连接本机8848端口
启动之前也可以修改配置，例如指定启动参数：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625054797371-312ae4ca-feeb-49df-b5d8-23c8fa5f4d08.png#clientId=u7647598a-212f-4&from=paste&height=71&id=u6c582e79&margin=%5Bobject%20Object%5D&name=image.png&originHeight=142&originWidth=1354&originalType=binary&ratio=2&size=62760&status=done&style=none&taskId=u7e50fa32-23a7-42b4-be44-bac11e9b2d5&width=677)
更多的参数可以在启动后通过help指令查看：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625054782596-84cbe6bf-35a1-4b2d-8345-ff13295d6a2d.png#clientId=u7647598a-212f-4&from=paste&height=420&id=u8539b0ec&margin=%5Bobject%20Object%5D&name=image.png&originHeight=839&originWidth=1496&originalType=binary&ratio=2&size=425205&status=done&style=none&taskId=uc4eb9a33-5d2b-49cb-8f55-e1cb71a71d3&width=748)
也可以通过配置文件，持久化修改配置，只需要在和nacosctl同目录下创建一个名为conf.properties的文件即可，比如example文件夹目录下的摆放方式：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625482706501-d19d4bad-355e-4602-9c57-923495818dfc.png)
文件写法如下：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625054749360-32b8c8ec-f4a6-4b4d-bda2-6a4f6b682108.png#clientId=u365f51c1-e1ce-4&from=paste&height=346&id=TjEKs&margin=%5Bobject%20Object%5D&name=image.png&originHeight=692&originWidth=1366&originalType=binary&ratio=2&size=148162&status=done&style=none&taskId=u3f824818-a4fb-4c6e-8bd6-47fd438997d&width=683)
在同时有配置文件且启动指定了参数到时候，nacosctl会优先使用启动指定的参数
​

## 基本指令与帮助

启动后，可以通过help指令查看全局帮助：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051883822-27762a4c-1920-4d3a-9fea-b6a9dd6776ed.png#clientId=u365f51c1-e1ce-4&from=paste&height=932&id=u06a7da29&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1864&originWidth=2444&originalType=binary&ratio=2&size=898346&status=done&style=none&taskId=u4c8c29a7-d037-4e8d-a624-98d3ae0b70a&width=1222)
在输入的过程中，可以通过按tab键获得提示与补全能力：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051883332-e5ff3a5c-35c3-4bb8-ac79-c2c1ac605559.png#clientId=u365f51c1-e1ce-4&from=paste&height=233&id=u0d68f861&margin=%5Bobject%20Object%5D&name=image.png&originHeight=466&originWidth=2306&originalType=binary&ratio=2&size=235183&status=done&style=none&taskId=u97a26a2c-0d13-4342-aab0-ab57bb02be1&width=1153)
若想查看各个子命令的使用方式，则可以采用subcommand help xx的方式：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051883170-e7e07661-0f1c-4602-9910-8e959fc3563d.png#clientId=u365f51c1-e1ce-4&from=paste&height=541&id=u2a8f4fda&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1082&originWidth=1534&originalType=binary&ratio=2&size=255323&status=done&style=none&taskId=ueac13fba-1688-435f-8409-1c780367d5b&width=767)
查看子命令的具体某个小命令：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051884277-f3534026-172c-4a5f-8c75-d4562a4e6bf8.png#clientId=u365f51c1-e1ce-4&from=paste&height=800&id=u00b3e470&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1600&originWidth=1630&originalType=binary&ratio=2&size=600577&status=done&style=none&taskId=u9907ff64-96d4-469b-8303-6ff3f03d197&width=815)
如果输入指令的过程缺少参数或者拼写错误，也会有红色高亮提示：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051884254-3158ad8e-2592-4597-8418-fcfbc3e7bfdf.png#clientId=u365f51c1-e1ce-4&from=paste&height=97&id=u5c23df04&margin=%5Bobject%20Object%5D&name=image.png&originHeight=194&originWidth=1064&originalType=binary&ratio=2&size=122697&status=done&style=none&taskId=u340b3b9d-39b6-4d19-ab6a-51d9157a43f&width=532)
缺少参数的提示：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051884884-205a1e9d-4355-4fb4-8070-0a2e7ea2a71c.png#clientId=u365f51c1-e1ce-4&from=paste&height=606&id=ue5070227&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1212&originWidth=1696&originalType=binary&ratio=2&size=437203&status=done&style=none&taskId=u9e5abc5b-4d19-43cd-8bb0-7cad2fa4e02&width=848)
此外，为了防止误删除操作，默认情况下nacosctl会对删除操作进行确认询问：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625478252647-d4669040-5386-46f9-80d9-51c44a246ee8.png#clientId=ue5ab1146-be40-4&from=paste&height=231&id=u012e1d04&margin=%5Bobject%20Object%5D&name=image.png&originHeight=462&originWidth=1544&originalType=binary&ratio=1&size=208920&status=done&style=none&taskId=uf2cf6425-5544-4ada-af59-ca7c1ee463a&width=772)
如果想要关闭掉误操作询问，则可以在配置文件中将confirmEnabled设置为false：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625478329021-0862607b-aa2e-4f49-8434-b934eaef358f.png#clientId=ue5ab1146-be40-4&from=paste&height=367&id=u812dc54a&margin=%5Bobject%20Object%5D&name=image.png&originHeight=734&originWidth=1108&originalType=binary&ratio=1&size=167895&status=done&style=none&taskId=u992002e7-ce74-4186-894d-1c56fc65a81&width=554)
再次重启后，没有删除保护了：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625478388496-0941dc63-ad3e-4b82-9e8e-d814fc92d582.png#clientId=ue5ab1146-be40-4&from=paste&height=106&id=u20e75ad3&margin=%5Bobject%20Object%5D&name=image.png&originHeight=212&originWidth=1372&originalType=binary&ratio=1&size=88237&status=done&style=none&taskId=ua90e2111-2b3a-4aa6-99f9-030b0880b6e&width=686)

## 命名空间切换

由于Nacos存在命名空间隔离，不同Namespace下的Config和Service是不相通的，所以nacosctl在操作过程中也提供了一个全局上下文的namespace，代表当前操作的所有资源所属的命名空间，可以在启动后的输入提示栏左侧括号中看到：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051884805-121b7d1b-36b8-4e5d-b1c8-7d12e3c3d9cf.png#clientId=u365f51c1-e1ce-4&from=paste&height=224&id=uc6733c46&margin=%5Bobject%20Object%5D&name=image.png&originHeight=448&originWidth=1814&originalType=binary&ratio=2&size=211760&status=done&style=none&taskId=ucb156dbc-f0d5-4573-9771-fb286c5cefe&width=907)
使用者可以通过use命令手动切换当前的上下文命名空间，输入use后按下tab，会自动展示可选的命名空间，以namespace名称（namespaceId）的形式展示：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051885410-aca7de98-c1bd-4d6e-ba4c-2139c5c41f87.png#clientId=u365f51c1-e1ce-4&from=paste&height=241&id=uae68e05a&margin=%5Bobject%20Object%5D&name=image.png&originHeight=482&originWidth=2358&originalType=binary&ratio=2&size=281578&status=done&style=none&taskId=u8a4e7059-8503-4bf3-8477-427e357cffc&width=1179)
输入前缀后，通过tab键自动补全，回车后，即可切换到新的命名空间，此过程也需要几秒钟的等待
​

## 命名空间操作

命名空间操作提供了基本的增删查改四个命令：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051885710-8b09849b-13b2-4626-973a-7dc6351f0019.png#clientId=u365f51c1-e1ce-4&from=paste&height=115&id=u87fd1cad&margin=%5Bobject%20Object%5D&name=image.png&originHeight=230&originWidth=1402&originalType=binary&ratio=2&size=93521&status=done&style=none&taskId=ucb70e62d-9a9e-45c9-8470-02dd307525f&width=701)
具体语法规范可以在nacosctl中通过namespace help xx来查看，这里特意说明一下update子命令：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051886114-1e7abec0-772f-48e4-a867-12cf1f2adea5.png#clientId=u365f51c1-e1ce-4&from=paste&height=533&id=u8c2ab544&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1066&originWidth=1456&originalType=binary&ratio=2&size=328261&status=done&style=none&taskId=ubc016da5-757f-4915-8fb1-451db006c77&width=728)
Namespace update只能修改命名空间的描述，而不能修改name和id，若想修改id，则需要重新创建删除，下图是一次命名空间的创建-查看-删除操作的指令流程：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051886366-da4871fc-fb8a-46be-87e0-052f4995a902.png#clientId=u365f51c1-e1ce-4&from=paste&height=824&id=u1560cea8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1648&originWidth=2420&originalType=binary&ratio=2&size=700174&status=done&style=none&taskId=u742f5045-f504-476b-9f5c-6e9b3acc52b&width=1210)


## Config操作

Config操作是指对Nacos的配置模块的资源进行操作，而不是配置nacosctl自身的信息
该模块提供了增删查改四个功能
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051886672-5b91d8ec-a594-4137-8177-e20feea331fc.png#clientId=u365f51c1-e1ce-4&from=paste&height=111&id=u455b2117&margin=%5Bobject%20Object%5D&name=image.png&originHeight=222&originWidth=1144&originalType=binary&ratio=2&size=79501&status=done&style=none&taskId=u3fd6088b-1143-49cc-b152-aadfc86a2a8&width=572)
其中，config add是添加一条新的配置，指定好group与dataId后，输入配置内容即可创建，语法规则如下：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051886972-cf1652cb-4b44-4433-8783-9dd2ebe98022.png#clientId=u365f51c1-e1ce-4&from=paste&height=642&id=u144d1219&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1284&originWidth=1996&originalType=binary&ratio=2&size=519842&status=done&style=none&taskId=u4b349f9b-7555-4ecf-9b18-375a93ba113&width=998)
其中，为了方便较长配置文件的创建操作，提供了文件读取功能，即通过-f指定文件，上传的config的内容即为文件内容，若不指定-f，则config的内容即为输入内容，例子如下：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051886998-7fe03cc7-79b3-4d3d-a65f-ba2ea90d5e93.png#clientId=u365f51c1-e1ce-4&from=paste&height=306&id=u1563beae&margin=%5Bobject%20Object%5D&name=image.png&originHeight=612&originWidth=2042&originalType=binary&ratio=2&size=407672&status=done&style=none&taskId=ucb248a86-35b0-4786-adef-4d865ce2f33&width=1021)
同理，对于config get操作，也提供了-f能力，可以把获取的输出内容直接写到指定文件中：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051887972-31149ea6-5606-44b7-8e5a-8b0b3c66753c.png#clientId=u365f51c1-e1ce-4&from=paste&height=340&id=u8d20b978&margin=%5Bobject%20Object%5D&name=image.png&originHeight=680&originWidth=1982&originalType=binary&ratio=2&size=525271&status=done&style=none&taskId=u10a8dc48-5d8f-46db-b247-1b8d9d261f5&width=991)
而若只想浏览当前nacos-server上的配置文件，则可以使用config list命令，默认情况下会列出nacos-console第一页的config，可以通过help查看并指定参数修改数目，使用效果如下：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051887823-1c31231a-e91b-4c6a-8b71-7d6490d2761b.png#clientId=u365f51c1-e1ce-4&from=paste&height=499&id=u6bc2eecc&margin=%5Bobject%20Object%5D&name=image.png&originHeight=998&originWidth=1464&originalType=binary&ratio=2&size=259629&status=done&style=none&taskId=ude88cedc-9aab-4a58-8d40-d6fffb9fe7e&width=732)

## Service操作

Service操作是指通过open-api，对nacos-server的服务进行编辑，提供了增删查改的基本能力：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051888269-079417f0-ee2f-4bb9-9223-cc1c02d7d38d.png#clientId=u365f51c1-e1ce-4&from=paste&height=107&id=u7638c168&margin=%5Bobject%20Object%5D&name=image.png&originHeight=214&originWidth=1270&originalType=binary&ratio=2&size=96911&status=done&style=none&taskId=u7855f7c4-b2ba-4ec7-a68e-93dedd9b9b2&width=635)
增删查改具体使用方式与config类似，参数选项细节可以通过service help xx指令查看
下图是一个 创建-查询-删除-查询 的使用案例：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051890510-0ad705d7-f515-4e18-b842-ffae9cb84c0f.png#clientId=u365f51c1-e1ce-4&from=paste&height=325&id=u7bef7f60&margin=%5Bobject%20Object%5D&name=image.png&originHeight=650&originWidth=3414&originalType=binary&ratio=2&size=636467&status=done&style=none&taskId=u02a346e4-bce6-41da-a1a1-b55779d5b95&width=1707)
如果想知道nacos-server有哪些内容，也可以通过service list命令查看，用法与config list类似：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051889959-f93130ec-3f83-461a-95cd-31f0363521fe.png#clientId=u365f51c1-e1ce-4&from=paste&height=259&id=udced3310&margin=%5Bobject%20Object%5D&name=image.png&originHeight=518&originWidth=2346&originalType=binary&ratio=2&size=211675&status=done&style=none&taskId=u95c1601f-b7b1-426a-849d-0251fb35beb&width=1173)
（这里注意，nacosctl及open-api的创建只会创建空服务而没有实例，过一段时间服务会被销毁掉）
​

## Instance操作

Instance操作是基于nacos-client实现，提供了对实例的操作（以及创建后会维持心跳），也是增删查改功能：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051889874-1a9c8882-004c-45e1-a5d3-2c9b3ed8aca0.png#clientId=u365f51c1-e1ce-4&from=paste&height=120&id=uddee5fb7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=240&originWidth=1154&originalType=binary&ratio=2&size=96193&status=done&style=none&taskId=ue6edbabb-2f2a-4c05-b893-cc6181b10b7&width=577)
下面是一个简单的使用例子：为同一个服务创建3个实例，查询，修改后再查询，删除一个，再查询：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051890194-35d85c3a-e684-4d5b-b2f2-d2c94fb0adc3.png#clientId=u365f51c1-e1ce-4&from=paste&height=454&id=u2020a0c0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=908&originWidth=3476&originalType=binary&ratio=2&size=839691&status=done&style=none&taskId=u8b83210f-71cc-4cce-91e3-d38ff06eaea&width=1738)
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051890970-a1289fe3-5567-4264-be65-9c0504627c3a.png#clientId=u365f51c1-e1ce-4&from=paste&height=617&id=ua4f0f586&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1234&originWidth=3470&originalType=binary&ratio=2&size=1453356&status=done&style=none&taskId=u212b17fc-8057-4fdb-8812-a7c560fc3ed&width=1735)




## Switch操作

Switch操作是指对开源版Nacos的系统开关进行查询与修改，基于Open-Api完成。Nacosctl能解决某些开关的展示名称和真实的key名不一致的问题，并提供了remove与add对list与map类开关进行参数修改，switch操作支持的命令如下：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051890963-d3f905d3-40a7-4986-a411-dd8e0777dfbc.png#clientId=u365f51c1-e1ce-4&from=paste&height=98&id=u57bcd7e5&margin=%5Bobject%20Object%5D&name=image.png&originHeight=196&originWidth=990&originalType=binary&ratio=2&size=79247&status=done&style=none&taskId=u60876109-4f54-4cb1-bd90-88a4bd4483c&width=495)
通过switch get，可以查询到某个开关的情况，可以按tab自动补全，如果想查看全部开关，则输入switch get all:
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051891773-75e49549-f545-4290-8955-1396cf7c9ddd.png#clientId=u365f51c1-e1ce-4&from=paste&height=736&id=ub37b98b3&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1472&originWidth=2820&originalType=binary&ratio=2&size=936308&status=done&style=none&taskId=ue5023071-5b98-4a23-9713-15b22302175&width=1410)
对于修改开关，以nacos-2.0.1开源版本总结，各开关需要注意的点如下：

```
open-api请求时 key name 需要修改：
(不过对于nacosctl使用者则无需关心，直接输原key就可以了，有自动转换)：
    healthCheckEnabled:true    --> check true/false
    checkTimes:3     -->  healthCheckTimes
    distroEnabled:true  ---> distro
    defaultPushCacheMillis:20000   --->pushCacheMillis


    pushJavaVersion:"0.2.0"   -->pushVersion xx:xx.xx.xx
    pushPythonVersion:"0.4.3"
    pushCVersion:"1.0.12"
    pushCSharpVersion:"0.9.0"
    pushGoVersion:"0.1.0"


不能通过open-api修改：
    enableAuthentication:false     不能设置
    name:"00-00---000-NACOS_SWITCH_DOMAIN-000---00-00"   不能改
    checksum:null   不能设置
    adWeightMap:{}  不能改
    mysqlHealthParams:{"max":3000,"min":2000,"factor":0.65}  不能改
    incrementalList:[]   不能改
    healthCheckWhiteList:[]  不能改


可以直接用原key修改：
    masters:null        Ps：value用，分割输入多个，比如a,b,c,d即可
    serviceStatusSynchronizationPeriodMillis:5000
    distroThreshold:0.7
    disableAddIP:false
    overriddenServerStatus:null
    doubleWriteEnabled:true
    lightBeatEnabled:true
    pushEnabled:true
    clientBeatInterval:1
    enableStandalone:true
    defaultCacheMillis:3000
    sendBeatOnly:false
    distroServerExpiredMillis:10000
    autoChangeHealthCheckEnabled:true
    serverStatusSynchronizationPeriodMillis:2000
    defaultInstanceEphemeral:true
   
        
特殊类型（list/map）：
    httpHealthParams:{"max":5000,"min":500,"factor":0.85}
    tcpHealthParams:{"max":5000,"min":1000,"factor":0.75}
    limitedUrlMap:{}

```

所以对于普通的key，可以通过switch set命令修改，例如：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051891637-2043132f-904f-46fb-ba99-4c5b80f37295.png#clientId=u365f51c1-e1ce-4&from=paste&height=247&id=uc692b473&margin=%5Bobject%20Object%5D&name=image.png&originHeight=494&originWidth=1540&originalType=binary&ratio=2&size=215775&status=done&style=none&taskId=ud189fd32-cc4e-4309-b162-1b417766a17&width=770)
对于特殊类型map，可以通过add与remove修改，例如：
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625051892202-b2b65dc5-7d18-4de4-8b0a-a3318bbcd8f5.png#clientId=u365f51c1-e1ce-4&from=paste&height=531&id=u95b05f10&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1062&originWidth=1952&originalType=binary&ratio=2&size=805740&status=done&style=none&taskId=u4d7c7a67-cf75-4741-b59f-0a0538b6040&width=976)
不过要注意的是，nacosctl这里对map的添加，删除只是对open-api的switch操作进行了一个封装，所以目前会存在无法删除最后一个key的情况，后续需要配合nacos-server解决。

---

由于Open-Api中还对Switch提供了debug操作（也就是只在单节点上生效以便于调试），但是这里目前存在一个问题：若首次使用debug修改部分key后，当后续再使用非debug推送某个key，则该节点的所有key都会被同步到整个集群，包括debug的信息。更多细节原因请@三辰
所以为了尽可能减少用户触发这种情况，nacosctl对这个问题做了如下设计：

- 当使用debug推送后，则后续不能进行非debug推送，提示用户在debug后需要手动改回原值
- 若想继续使用非debug推送，则请重启nacosctl

例如下图，先进行带debug的推送后，如果不重启，就不支持进行正常的非debug推送
![image.png](https://cdn.nlark.com/yuque/0/2021/png/21738773/1625453910112-cee0086b-9809-4ce7-8593-112165c7f98d.png#clientId=uf8b4a174-c9c1-4&from=paste&height=259&id=u4b37e6fd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=518&originWidth=2242&originalType=binary&ratio=1&size=245642&status=done&style=none&taskId=ue4ad299a-0be7-42d6-ba91-24cdb69a146&width=1121)

