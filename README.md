# HairBnb — Search and Rent Properties

**HairBnb** is a platform for accommodation booking (basically an _AirBnb_ clone).

---
## Features

* Posting a listing
* Search listings with filters

---

## Tech Stack

* **Back-End:** Java + Spring Boot
* **Front-End:** React + Typescript
* **Database:** PostgreSQL
* **Caching:** Redis

The **_frontend_** repository can be found here https://github.com/denmit99/hairbnb-front

---

## How to run locally
`docker compose --profile all up -d` Start all services

`docker compose --profile api up -d ` Start only backend + database

`docker compose --profile infra up -d ` Start only infrastructure (redis + database)

`docker compose --profile all up -d --build`  Rebuild all services and then start containers

---

## Development
When developing locally, make sure to activate the `dev` profile.

Set environment variable in Intellij: `SPRING_PROFILES_ACTIVE=dev`

---

### Jacoco test coverage
`./gradlew clean build jacocoTestReport`

Report can be found be in `build/reports/jacocoHtml`

---

## API Docs
https://available-mechanic-6c2.notion.site/HairBnb-API-Documentation-10c12d058faa809d902cd772c4e1f535

---
