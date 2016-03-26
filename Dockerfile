FROM java:7
MAINTAINER Jonny Flowers jonny_flowers@me.com

COPY . /grendel/
WORKDIR /grendel

RUN apt-get update
RUN apt-get install -y maven

RUN cp bcprov-jdk12-145.jar /usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/bcprov-jdk12-145.jar

RUN mv /usr/lib/jvm/java-7-openjdk-amd64/jre/lib/security/java.security /usr/lib/jvm/java-7-openjdk-amd64/jre/lib/security/java.security.old
RUN cp java.security /usr/lib/jvm/java-7-openjdk-amd64/jre/lib/security/java.security

RUN mvn clean package

EXPOSE 8080

ENTRYPOINT java -jar target/grendel-0.4.0-SNAPSHOT.jar server -c grendel.properties -p 8080