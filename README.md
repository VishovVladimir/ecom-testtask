# Getting Started

### Building application:
command
```
./gradlew bootJar
```

### Building docker image
```
docker build -t vvvishov/ecom-test .
```

### Start application in container 
```
docker run -d -p 8080:8080 --rm vvvishov/ecom-test
```

### Or use buildAndStart script
