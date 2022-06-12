#!/bin/sh

echo "********************************************************"
echo "Starting security-jwt-demo  Server " ON PORT: $SERVER_PORT;
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT   \
     -Dspring.profiles.active=$PROFILE -jar /usr/local/security-jwt-demo/@project.build.finalName@.jar
