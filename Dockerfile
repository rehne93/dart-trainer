FROM openjdk:25-jdk-alpine
MAINTAINER rene.baernreuther
COPY build/libs/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]