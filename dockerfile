# Здесь используется простой файл, который собирает образ и плоного JAR файла, для оптимизации сборки можно разделить на слои
FROM openjdk:11

EXPOSE 8080

ARG JAR_FILE=build/libs/task-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]