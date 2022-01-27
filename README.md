# TourGuide

Application for finding touristic attractions, and giving rewards to users according to the attraction they visited.

## Getting Started

### Dependencies

-Java 11\
-Gradle 7.3.3

Optional :\
-Docker 20.10.12, for use in docker container.\
-Docker-compose, for easy launch of all docker container.

### Installing

-If you dont already have, clone the github repository at address https://github.com/Kafeinedev/JavaPathENProject8.git

Follow the installation process for GpsUtil and RewardCentral described respectively in :\
projectroot/GpsUtil/README.md\
projectroot/RewardCentral/README.md\

-Inside projectroot/TourGuide run the command
 ```
 gradle build
 ```

 Docker only :\
-Inside projectroot/TourGuide run the command

```
docker build -t tourguide .
```


### Executing program

-Option one : launch with docker-composer :\
Inside projectroot run the command
```
docker-compose up -d
```

-Option two : use as a java application :\
Necessitate :\
    -a GpsUtil application listening on port 9001.\
    -a RewardCentral application listening on port 9002.\
Inside projectroot/GpsUtil/build/libs run the command

```
java -jar TourGuide-1.0.0.jar
```

You can now do the following request on port 9000 :\
GET /\
GET /getLocation with parameter String userName.\
GET /getNearbyAttractions with parameter String userName.\
GET /getRewards with parameter String userName.\
GET /getAllCurrentLocations\
GET /getTripDeals with parameter String userName.
