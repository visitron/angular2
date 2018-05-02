FROM openjdk:8-jre-alpine

WORKDIR /home

ADD boot/target/distributions/boot.tar .

CMD sh boot/bin/boot