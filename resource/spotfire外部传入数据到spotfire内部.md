##一：问题起源：
	卫计委项目，想要将我们spotfire数据分析项目接入他们平台，协议通过在我们url后面加参数sec（des3方式加密的待传输数据）方式将数据代入spotfire内部使用

##二：解决方案：
	进过研究发现可能有两种方式可以解决
	方式一：spotfire单点登录参考：
		https://community.tibco.com/wiki/custom-authentication-tibco-spotfire-75-and-later-versions；
		https://community.tibco.com/questions/tibco-spotfire-75-custom-external-authentication-issue
		https://community.tibco.com/wiki/spotfire-adding-parameters-web-custom-authenticator
	方式二：confurationBlock：参考：
		https://community.tibco.com/wiki/create-configuration-block-tibco-spotfire

## 三：解决问题
	方式一后续在研究
	本次原理：通过配置一个java服务接受url带过来的参数，解密sec参数，再重定向到配置好的spotfire外部触发login对应的地址
	步骤：
		给spotfire配置“spotfire 外部触发 login”
		cd SSO_Spotfire文件夹下执行mvn clean package，在target文件夹下找到SSO_Spotfire.jar包
		在spotfire对应服务器上运行SSO_Spotfire程序
		在网页中访问如下链接：http://localhost:8020/Perkinelmer/SSOTransfor?sec=sO6shaAStX3HKH303aSdsFayoLj0ZHEzqj5uY6hWXHnT0KqtR5BOdyEePalK+p4%2FweIEV%2B6PJ7PHqEoqxbRjNFuMI0StBFJDM7S47i%2FW3S5D6IA74lFjhGhUZ+LhKGyZyJpzNExYaw%2FoXUbNUXCztLS%2F5PQxUBiVFyvU5lJIsOf1Q%3D
		第一次访问，程序在session中没有找到到flagSSOTransfor重定向到http://"+spotfireIp+":"+spotfirePort+"/spotfire/resources/custom-login/custom-login-app-example.html?username="+username+"&password="+password+"&libraryname="+libraryname;
		第二次访问：程序在session中找到flagSSOTransfor=1，重定向到"http://"+spotfireIp+":"+spotfirePort+"/spotfire/wp/analysis?file="+libraryname;
	本解决方案不是很完美，后续希望能真正通过confurationBlock传入数据到spotfire
		
		
	
	
