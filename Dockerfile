FROM ibm-semeru-runtimes:open-25-jre
MAINTAINER rene.baernreuther
COPY build/libs/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]