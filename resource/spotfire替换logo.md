1：找到ReplaceLogoSupport

2：修改核心文件cobranding.config，来指定logo是什么，里面还有其他的配置

3：一切准备就绪，开始打包

	1）找到打包文件C:\TIBCO\Spotfire Server\10.10.0\TIB_sfire_dev_10.10.0_win\SDK\Package Builder\Spotfire.Dxp.PackageBuilder.exe，双击打开
	2)点击Manage->Add->起个名字（replaceLogo）->Ok;
	3)点击TIBCO Spotfire Distribute->点击File->点击 Add TIBCO Spotfire Distribute->将C:\Program Files (x86)\TIBCO\Spotfire\10.3.0（目录下包含Spotfire.Dxp.exe，Spotfire.Dxp.exe.config登录）文件路径配置进去
	4）点击右侧Add->点击Browse->打开你之前准备的ReplaceLogoSupport文件目->一路next结束后->点击Validate and Save
	5)点击你刚才生成的一个条目-》点击File->Build Package File获得对应spk文件
4：打包结束你获得了对应的spk文件，再在http://127.0.0.90/spotfire/#/deploymentsPackages?tabId=dp&areaId=Production中点击 add Package 将你制作好的包上传到spotfire中，这样你做的操作就生效了