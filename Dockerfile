FROM ubuntu:12.04
MAINTAINER Jonny Flowers jonny_flowers@me.com

COPY . /grendel/
WORKDIR /grendel

RUN apt-get update
RUN apt-get -y wget git curl
RUN apt-get install -y default-jre
RUN apt-get install -y maven

RUN wget www.bouncycastle.org/download/crypto-154.tar.gz
RUN tar xopf crypto-154.tar.gz
RUN rm crypto-154.tar.gz