FROM amazoncorretto:17.0.8

COPY build/libs/app.jar /app.jar
RUN chmod +x /app.jar

EXPOSE 8400

CMD ["java", "-jar", "/app.jar"]
