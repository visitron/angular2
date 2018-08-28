FROM jetbrains/teamcity-agent:2018.1.1
RUN apt update && \
    apt install wget && \
    wget 'https://download.java.net/java/early_access/jdk11/28/GPL/openjdk-11+28_linux-x64_bin.tar.gz' -O /tmp/jdk-11.tar.gz && \
    mkdir -p /usr/lib/java/jdk-11 && \
    tar -xvf /tmp/jdk-11.tar.gz -C /usr/lib/java/jdk-11 && \
    rm /tmp/jdk-11.tar.gz