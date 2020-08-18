#!/bin/bash

#TODO: Change the database settings and connection URL as needed

# Check that bootstrap does not exist
if test ! -f /opt/tibco/tss/10.10.0/tomcat/webapps/spotfire/WEB-INF/bootstrap.xml
then

	pushd /opt/tibco/tss/10.10.0/tomcat/spotfire-bin

	./catalina.sh stop

	DB_DRIVER="tibcosoftwareinc.jdbc.oracle.OracleDriver"
	DB_URL="jdbc:tibcosoftwareinc:oracle://47.103.133.15:1521;SID=orcl"
	DB_USER="spotfire"
	DB_PASSWORD="spotfire"
	CONFIG_TOOL_PASSWORD="spotfire"
	IPADDR="0.0.0.0"
	ALIAS="SHALL940.PERKINELMER.NET"
	echo Creating the database connection configuration
	#./config.sh bootstrap --no-prompt --driver-class=$DB_DRIVER --database-url=$DB_URL --username=$DB_USER --password=$DB_PASSWORD --tool-password=$CONFIG_TOOL_PASSWORD --server-alias=$HOSTNAME  -A$HOSTNAME
	./config.sh bootstrap --no-prompt --driver-class=$DB_DRIVER --database-url=$DB_URL --username=$DB_USER --password=$DB_PASSWORD --tool-password=$CONFIG_TOOL_PASSWORD --server-alias=$ALIAS  -A$IPADDR --force-encryption-password

	echo after bootstrap

	./catalina.sh start

	popd

fi

## need to start server when machine starts up
pushd /opt/tibco/tss/10.10.0/tomcat/bin

./catalina.sh start

popd


# ./config.sh bootstrap --no-prompt --driver-class="tibcosoftwareinc.jdbc.oracle.OracleDriver" --database-url="jdbc:tibcosoftwareinc:oracle://47.103.133.15:1521;SID=orcl" --username="spotfire" --password="spotfire" --tool-password="spotfire" --server-alias="127.0.0.1"  -A"127.0.0.1"  --force-encryption-password
