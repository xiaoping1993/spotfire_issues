创建镜像脚本：
docker build -m 3GB -t tsslinux1010v2 --build-arg toolpwd=spotfire --build-arg adminuser=spotfire .
运行镜像脚本：
docker run -it -d -p 92:80 -p 9080:9080 -p 9443:9443 -m 3GB --cpus=2 --name tsslinux3gb tsslinux1010

注释：
92:80	宿主机92端口映射docker容器中80端口，与spotfire client建立联系属于前端端口
9080:9080	端口映射与上面相同，功能是与nodes建立联系的
9443:9443	端口映射与上面相同，功能是nodes之间加密传输信息用

宿主机ping到docker主机ip问题还没有解决