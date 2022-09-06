FROM openjdk:14

RUN mkdir /app

COPY build/libs/*.jar /app/concert-poster-app.jar

ENTRYPOINT ["java","-jar","/app/concert-poster-app.jar"]
