FROM openjdk:8-jdk-alpine
RUN mkdir -p /usr/local/security-jwt-demo
ADD @project.build.finalName@.jar /usr/local/security-jwt-demo/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
