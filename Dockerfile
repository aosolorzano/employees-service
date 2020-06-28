FROM openjdk:11.0.6-jre-buster
MAINTAINER "Andres Solorzano"
COPY target/employees-service-0.4.0-thorntail.jar /opt/thorntail.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/thorntail.jar", "-Djava.net.preferIPv4Stack=true"]