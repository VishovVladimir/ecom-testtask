./gradlew bootJar
docker build -t vvvishov/ecom-test .
docker run -d -p 8080:8080 --rm vvvishov/ecom-test