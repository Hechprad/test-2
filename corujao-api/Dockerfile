FROM maven:3.6.2-jdk-8
LABEL maintainer="hecherat@gmail.com"
COPY . /urs/src/mymaven
WORKDIR /urs/src/mymaven
RUN mvn -Dmaven.test.skip clean package
WORKDIR /urs/src/mymaven/target
EXPOSE 5000
ENTRYPOINT ["java","-jar","corujao-api-0.0.1-SNAPSHOT.jar"]
