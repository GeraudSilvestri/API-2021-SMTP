FROM openjdk:8-jre
MAINTAINER Loïc Rosset <loic.rosset@heig-vd.ch>

RUN apt-get update && \
    apt-get clean

COPY MockMock-1.4.0.one-jar.jar /opt/app/mock.jar

# Port utilisé par mockmock
EXPOSE 25

# Launch the mockmock jar
CMD ["java", "-jar", "/opt/app/mock.jar"]