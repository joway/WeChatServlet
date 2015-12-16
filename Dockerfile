FROM tomcat:8-jre8

MAINTAINER "Joway Wong"

ADD classes/artifacts/weixin_java_servlet_war/weixin-java-servlet_war.war /usr/local/tomcat/webapps/