# GpsUtil

Microservice for GpsUtil. This application can be run as standalone application, or be run inside a docker container.

## Getting Started

### Dependencies

-Java 11\
-Gradle 7.3.3

Optional :\
-Docker 20.10.12, for use in docker container.

### Installing

-If you dont already have, clone the github repository at address https://github.com/Kafeinedev/JavaPathENProject8.git

-Inside projectroot/GpsUtil run the command
 ```
 gradle build
 ```

 Docker only :\
-Inside projectroot/GpsUtil run the command

```
docker build -t gpsutil .
```


### Executing program

-Option one : use as standalone application :\
Inside projectroot/GpsUtil/build/libs run the command

```
java -jar GpsUtil-1.0.0.jar
```

-Option two : use inside a docker container :\
Run the command
```
docker run -p 9001:9001 -d gpsutil
```

You can now do the following request on port 9001 :\
GET /getAttractions\
GET /getUserLocation with parameter UUID userUUID.
