FROM dockerfile/java:oracle-java8

ADD payment_api.jar /opt/payment_api/
EXPOSE 8080
WORKDIR /opt/payment_api/
CMD ["java", "-Xms512m", "-Xmx1g", "-jar", "payment_api.jar"]