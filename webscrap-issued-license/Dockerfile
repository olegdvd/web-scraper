FROM java:8
EXPOSE 8080:8080
ADD /target/webscrap-issued-license-0.0.1-SNAPSHOT.jar issued-license.jar
ENTRYPOINT ["java","-jar", "issued-license.jar"]