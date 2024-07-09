FROM openjdk:16-jdk-alpine

WORKDIR /app

COPY . .

COPY settings.xml /root/.m2/settings.xml

RUN ./mvnw clean package -DskipTests

EXPOSE 5556

CMD ["java", "-jar", "target/backendApp-1.0-SNAPSHOT.jar"]
