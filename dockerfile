FROM openjdk:20
LABEL authors="artur"
RUN mkdir /app
COPY build/libs/users-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
ENV JAVA_OPTS=""
WORKDIR /app
CMD ["java", "-jar", "users-0.0.1-SNAPSHOT.jar"]