FROM maven:3.9.3-amazoncorretto-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM amazoncorretto:17-al2-native-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]