FROM arm64v8/openjdk:11
WORKDIR /opt/app
ARG JAR_FILE=/target/spring-boot-app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]