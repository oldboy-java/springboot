FROM openjdk:8-jdk-slim
LABEL maintainer=liuli
ARG JAR_FILE
COPY ${JAR_FILE}   app.jar

ENTRYPOINT ["java","-jar","/app.jar"]