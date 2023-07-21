FROM maven:3-eclipse-temurin-17
WORKDIR /app
# The next lines are the fake test run which allows maven to download all 
# its dependencies and save them in the docker image layer.
# Real application is run in docker-compose.yaml file, where the actual code
# stuff is binded via volume.
COPY ./jsight-lib/libjsight/1.0.0/libjsight.so /usr/local/lib/
COPY ./jsight-lib/libjsightjava/1.0.0/libjsightjava.so /usr/java/packages/lib/
COPY ./ /app/
RUN mvn clean install

