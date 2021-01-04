## spotfire client调用python外部包

1）通过本地python 的pip机制下载对应包

比如配置suds外部包到本地client

pip install suds-community

之后在C:\Program Files\Python39\Lib\site-packages路径下就找到几个新下载的包拷贝到spotfire对应的python包路径

这个路径位置：Tools->Python Tools->第一个页面路径就是python包路径了

2）使用此python包

Data->Data Function Properties->Register New之后就在这个python脚本中编写了

注意：spotfire中的python print功能你看不到效果，测试可以通过给output Parameters配置一个output参数，同时制定这个output参数为spotfire document preperties(这个文档属性添加一个output，再往input中赋值output属性即可)

from suds.client import Client
client = Client('http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?wsdl')
output=str(client.service.getMobileCodeInfo("18300000000",""))



## spotfire any 调用python外部包



