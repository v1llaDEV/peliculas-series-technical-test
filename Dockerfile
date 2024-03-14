FROM openjdk:11
WORKDIR /app
COPY demo/target/cleverpy-backend-test-1.3.0.jar /app
EXPOSE 8080
CMD ["java", "-jar", "cleverpy-backend-test-1.3.0.jar"]