# HairBnb project

## How to run

`docker-compose --profile api up -d ` Start only backend + database

`docker-compose --profile all up -d` Start all services

`docker-compose --profile all up -d --build`  To rebuild all services and then start containers

## Jacoco test coverage
`./gradlew clean build jacocoTestReport`

Report will be in `build/reports/jacocoHtml`
