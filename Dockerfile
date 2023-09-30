# syntax=docker/dockerfile:1

FROM ubuntu:18.04

ENV DEBIAN_FRONTEND=noninteractive

WORKDIR /hmsrest

COPY ./HEC-HMS-4.6 ./app/

RUN ln -s /hmsrest/app/bin/jdk-11.0.8+10-jre ./app/jre

RUN /bin/bash ./app/install_dependencies.sh

CMD [ "/bin/bash","./app/hmsrest.sh" ]