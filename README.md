# HairBnb Project

HairBnb is a platform for accommodation booking (basically an AirBnb clone).

Tech stack:
* **Back-End:** Java + Spring Boot
* **Front-End:** React + Typescript
* **Database:** PostgreSQL

The **_frontend_** repository can be found here https://github.com/denmit99/hairbnb-front

## How to run locally
`docker-compose --profile all up -d` Start all services

`docker-compose --profile api up -d ` Start only backend + database

`docker-compose --profile all up -d --build`  To rebuild all services and then start containers

## Jacoco test coverage
`./gradlew clean build jacocoTestReport`

Report can be found be in `build/reports/jacocoHtml`
