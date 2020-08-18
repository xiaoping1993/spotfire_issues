## 一：docker简介技术
	参考：https://www.runoob.com/docker/docker-tutorial.html

	docker是go语言开发的容器化技术，依赖操作系统层面底层软硬件配置，达到的沙盒模式轻量级、可移植效果
	
	比虚拟机模式不知道好太多，基本上一个应用安装需要多少内存，将docker化之后也不会有多少额外的内存增加

### 安装
	linux 安装：
	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
	window 安装：
	 提前说一下，只要你安装的是linux docker container,那么都是需要一个linux环境的，所以在windows中安装linux docker container
	 都会有一个linux虚拟机被创建，无论你是window7 window8(安装之前需要你有一个oralce vm 因为他会自动创建一个linux虚拟机环境作为基础linux docker container基础)，还是window10(需要开启hyper-v这实际上就是一个虚拟机，开启了这个你安装docker后，就会自动创建一个linux虚拟机了)（我这里再多嘴一句，因为都是虚拟机你开通了hyper-v就不能启动oralce-vm或者其他虚拟机）

	 window 7,8安装地址:http://mirrors.aliyun.com/docker-toolbox/windows/docker-toolbox/
	 	其中：
		Docker CLI - 客户端，用来运行 docker 引擎创建镜像和容器。
		Docker Machine - 可以让你在 Windows 的命令行中运行 docker 引擎命令。
		Docker Compose - 用来运行 docker-compose 命令。
		Kitematic - 这是 Docker 的 GUI 版本。
		Docker QuickStart shell - 这是一个已经配置好Docker的命令行环境。
		Oracle VM Virtualbox - 虚拟机。
	 window10 : https://www.docker.com/get-started点击 Get started with Docker Desktop，并下载 Windows 的版本
		先开通Hyper-v
		再安装安装包
### docker架构
	镜像（Image）：Docker 镜像（Image），就相当于是一个 root 文件系统。比如官方镜像 ubuntu:16.04 就包含了完整的一套 Ubuntu16.04 最小系统的 root 文件系统。
	容器（Container）：镜像（Image）和容器（Container）的关系，就像是面向对象程序设计中的类和实例一样，镜像是静态的定义，容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等。
	仓库（Repository）：仓库可看成一个代码控制中心，用来保存镜像
### docker常用指令
	这里我就简单说下最常用脚本，其他高级的功能可以自行查看参考url
	启动docker打开配置了docker环境的命令窗口，开始输入docker 命令
	1.获得镜像
	1）拉取镜像：docker pull ubuntu
	2) 创建镜像：在你的dockerfile文件根目录下执行docker build -m 3GB -t tsslinux1010v2 --build-arg toolpwd=spotfire --build-arg adminuser=spotfire .
	意思是：根据这个dockerfile创建镜像，镜像大小3GB，名称tsslinux1010v2,dockerfile中需要的参数 toolpwd=spotfire,adminuser=spotfire
	2.获得容器
	1）运行镜像就会获得容器：docker run -it -d -p 92:80 -p 9080:9080 -p 9443:9443 -m 3GB --cpus=2 --name tsslinux3gbv3 tsslinux1010v3
	意思是：运行docker镜像库中已有镜像tsslinux1010v3获得名称tsslinux3gbv3的容器其中这个容器我们也可以进去操作
	3.对镜像操作的脚本
	1）删除镜像：docker rmi 镜像name/镜像id
	2）查看所有镜像：docker images
	4.对容器的操作脚本
	1）查看所有容器：docker ps -a
	2)查看所有正在运行的容器：docker ps
	3)删除指定容器：docker rm 容器name/id
	4)强制删除指定容器：docker rm -f 容器name/id
	5)进入容器内部：docker exec -it 容器name/id /bin/bash
	6)退出容器内部：在容器内部执行exit
### docker 普遍遇到的问题
	1）docker linux container 在同一个宿主机上
		如何宿主机ping通container?
		如何宿主机ping通同一个宿主机上其他docker linux container?
			尝试多次都失败，未找到解决办法
	2）docker window continer 在同一个宿主上
		container如何ping通外网，宿主机
		container之间如何ping通
			尝试多次都失败，未找到解决办法
	tip:虽然所有container都可以通过端口映射的方式相互连接，但是总会有上午两个问题的需求出现
### 后续我会推出如何在搭建一个基于mesos的k8s用于管理所有docker linux container的集群，类似于武汉卫计委那套集群架构
## 二：spotfire docker步骤
参考：https://community.tibco.com/wiki/tibco-spotfirer-server-docker-scripts
### 2.1 spotfire_server docker linux container 安装
	Docker 版本：2.3.0.3 （docker desktop community）
    Spotfire版本：10.10.0
	安装配置文件位置：resource/spotfire docker linux container
	资料描述如下：
       我提供一个已经弄好了的spotfire_server dockerFile相关文件在都在压缩包中，其中还需要你下载
              Spotfire.Dxp.sdn
              zh-CN.sdn
              tss-10.10.0.x86_64.rpm，放在压缩包中dockerFile同级目录
       备注：你可以查看我提供文件夹中readme.txt，其中就有命令
              生成spotfire_server的docker image的命令
              运行spotfire docker image的命令
              其中数据库我使用的是阿里云上的，所以不需要你再配置了
              只需要准备好上需要下载的spotfire相关安装包即可
	我这里再写下脚本：
		创建镜像脚本：
		docker build -m 3GB -t tsslinux1010v2 --build-arg toolpwd=spotfire --build-arg adminuser=spotfire .
		运行镜像脚本：
		docker run -it -d -p 92:80 -p 9080:9080 -p 9443:9443 -m 3GB --cpus=2 --name tsslinux3gb tsslinux1010
### 2.2 nodeManager 安装
	因为nodeManager没有linux版的所有做不了docker linux container了，我们只能退而求其次，将把他作为一个后天服务运行在网络层面上，直接就以window方式安装，只要在配置中指定了docker_server的相关端口建立连接即可
### 2.3 遇到问题
	现在spotfire_server的docker Linux container已经制作好了，同时nodeManager也已经在其宿主机上安装成功，
    同时解决了nodeManager与spotfire_server之间trust的问题
		只需要在C:\TIBCO\tsnm\10.10.0\nm\config\nodemanager.properties中指定 	nodemanager.host.names=spotfire的ip号一个即可
    现在遇到的问题就在于：
      在宿主机不能ping通docker spotfire Linux container的情况下，如何解决在nodeManager 被 spotfire_server trust后，NodeManager不在是处于offline状态
	目前问题还在持续跟踪中


	