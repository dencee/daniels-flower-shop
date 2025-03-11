FROM maven:3.9.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/flower-shop-1.0.jar mod2-final.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "mod2-final.jar"]