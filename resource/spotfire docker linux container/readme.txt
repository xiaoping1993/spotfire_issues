��������ű���
docker build -m 3GB -t tsslinux1010v2 --build-arg toolpwd=spotfire --build-arg adminuser=spotfire .
���о���ű���
docker run -it -d -p 92:80 -p 9080:9080 -p 9443:9443 -m 3GB --cpus=2 --name tsslinux3gb tsslinux1010

ע�ͣ�
92:80	������92�˿�ӳ��docker������80�˿ڣ���spotfire client������ϵ����ǰ�˶˿�
9080:9080	�˿�ӳ����������ͬ����������nodes������ϵ��
9443:9443	�˿�ӳ����������ͬ��������nodes֮����ܴ�����Ϣ��

������ping��docker����ip���⻹û�н��