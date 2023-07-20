FROM maven:latest 
WORKDIR /app
# The next lines are the fake test run which allows maven to download all 
# its dependencies and save them in the docker image layer.
# Real application is run in docker-compose.yaml file, where the actual code
# stuff is binded via volume.
COPY ./ /app/
RUN mvn clean install

