FROM store/oracle/serverjre:8
WORKDIR /home
COPY boot/target/libs /libs
COPY boot/target/scripts /scripts

CMD ['scripts/boot']