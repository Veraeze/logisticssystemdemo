FROM maven:3.8.7 as build
COPY . .
RUN mvn clean package -B -DskipTest

FROM opendjdk:17-jdk-slim
COPY --from=build ./target/*.jar logistic.jar
ENTRYPOINT ["java", "-jar","logistic.jar"]