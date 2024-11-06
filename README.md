# HairBnb — Search and Rent Properties

**HairBnb** is a platform for accommodation booking (basically an _AirBnb_ clone).

---
## Features

* Posting a listing
* Search listings with filters

---

## Tech stack

* **Back-End:** Java + Spring Boot
* **Front-End:** React + Typescript
* **Database:** PostgreSQL

The **_frontend_** repository can be found here https://github.com/denmit99/hairbnb-front

---

## How to run locally
`docker compose --profile all up -d` Start all services

`docker compose --profile api up -d ` Start only backend + database

`docker compose --profile all up -d --build`  Rebuild all services and then start containers

---

## API Docs
https://available-mechanic-6c2.notion.site/HairBnb-API-Documentation-10c12d058faa809d902cd772c4e1f535

---

## Jacoco test coverage
`./gradlew clean build jacocoTestReport`

Report can be found be in `build/reports/jacocoHtml`
