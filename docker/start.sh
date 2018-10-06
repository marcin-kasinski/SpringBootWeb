#!/bin/bash


#ls -l /
#cat /etc/init.d/filebeat
/etc/init.d/filebeat start
#java -jar -Dspring.profiles.active=$SPRING_PROFILE SpringBootWeb-0.0.1-SNAPSHOT.jar --spring.cloud.consul.discovery.ipAddress=$HOST --spring.cloud.consul.discovery.port=$PORT0 --spring.cloud.consul.host=$HOST
java -jar $EXTRA_JAVA_ARGS -Dspring.profiles.active=$SPRING_PROFILE SpringBootWeb-0.0.1-SNAPSHOT.jar
