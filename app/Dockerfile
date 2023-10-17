FROM amazoncorretto:17.0.8

COPY app/build/libs/app.jar /app.jar
RUN chmod +x /app.jar

EXPOSE 8400

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app.jar"]
