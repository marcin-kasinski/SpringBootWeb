#!/bin/bash

#aby wykonac skrypt bez podawania hasła:
#sudo visudo
# potem dodajesz wpis: 
#marcin ALL = NOPASSWD: /home/marcin/SpringBootWeb/kubernetes/deploy.sh, /sbin/restart
#
#wc -l $(ls)
# echo  'ABC'$(echo "XXX" )

cd /home/marcin/SpringBootWeb/docker
docker build -f dockerfile -t springbootweb . && docker tag springbootweb marcinkasinski/springbootweb && docker push marcinkasinski/springbootweb
echo "End pushing"


kubectl delete pod $( kubectl get pod | grep springbootweb-deployment | head -n1 | sed -e 's/\s.*$//' )




echo "Pod springbootweb-deployment deleted"
