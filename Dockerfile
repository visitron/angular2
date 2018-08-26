FROM openjdk:11-jre-slim

WORKDIR /home

ADD boot/target/distributions/boot.tar .

CMD sh boot/bin/boot