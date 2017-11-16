FROM store/oracle/serverjre:8

ADD boot/target/distributions/boot.tar /home

WORKDIR /home/boot/bin/

CMD ./boot

EXPOSE 3002:3002