FROM centos

ARG oc_download_url="https://github.com/openshift/origin/releases/download/v3.9.0/openshift-origin-client-tools-v3.9.0-191fece-linux-64bit.tar.gz"
ENV OC_DOWNLOAD_URL "${oc_download_url}"

ARG maven_download_url="http://mirror.dkd.de/apache/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz"
ENV MAVEN_DOWNLOAD_URL "${maven_download_url}"

RUN yum install -y yum-utils && \
    yum install -y iputils && \
    yum install -y curl && \
    yum install -y wget && \
    yum install -y java-1.8.0-openjdk-devel && \
    yum install -y bzip2 && \
    yum install -y net-tools && \
    yum install -y bind-utils && \
    yum install -y nc && \
    yum install -y git && \
    yum makecache fast

# Install Maven
RUN curl ${MAVEN_DOWNLOAD_URL} -o /tmp/maven.tgz && \
    su -c "tar -zxvf /tmp/maven.tgz -C /usr/local"  && \
    ln -s /usr/local/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/local/bin/mvn && \
    ln -s /usr/local/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/local/bin/maven && \
    rm /tmp/maven.tgz

## Install OC client
#RUN curl ${OC_DOWNLOAD_URL} -o /tmp/oc.tgz && \
#    su -c "tar -zxvf /tmp/oc.tgz -C /usr/local" && \
#    ln -s

RUN mkdir -p /opt/shashi && \
    mkdir -p /opt/shashi/work && \
    mkdir -p /opt/shashi/libs