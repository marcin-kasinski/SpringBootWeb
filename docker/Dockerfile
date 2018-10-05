FROM openjdk:8
MAINTAINER Marcin Kasiński <marcin.kasinski@gmail.com> 

RUN mkdir -p /usr/src/myapp/logs && touch /usr/src/myapp/logs/application.log


########################################## starting Logstash  ##########################################

# install Filebeat
#ENV FILEBEAT_VERSION=filebeat-5.5.2-amd64.deb
ENV FILEBEAT_VERSION=filebeat-6.2.3-amd64.deb
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/${FILEBEAT_VERSION} &&\
dpkg -i ${FILEBEAT_VERSION} && \
  rm ${FILEBEAT_VERSION}

# configure Filebeat
ADD filebeatstokafka.yml /etc/filebeat/filebeat.yml
#ADD filebeatstologstash.yml /etc/filebeat/filebeat.yml
#ADD filebeatstoelasticsearch.yml /etc/filebeat/filebeat.yml
ADD start.sh /usr/src/myapp/start.sh
RUN sed -i -e 's/\r//g' /usr/src/myapp/start.sh &&\
chmod +x /usr/src/myapp/start.sh

COPY SpringBootWeb-0.0.1-SNAPSHOT.jar /usr/src/myapp/SpringBootWeb-0.0.1-SNAPSHOT.jar
WORKDIR /usr/src/myapp
EXPOSE 7777
ENTRYPOINT [ "/usr/src/myapp/start.sh" ]