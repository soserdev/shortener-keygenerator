# KEYGENERATOR SERVICE

A simple Keygenerator Service to generate unique keys for a Url Shortener based upon a Redis database. 

## About

### Docker Compose support

This project uses Docker Compose. 
- See file named `compose.yaml` for Redis configuration to start only Redis.
- See file named `docker-compose.yaml` to start Redis + App.

### Testcontainers support

This project uses [Testcontainers at development time](https://docs.spring.io/spring-boot/3.5.6/reference/features/dev-services.html#features.dev-services.testcontainers).


## Usage 

Generate Docker Image:

```bash
docker build  -t soserdev/shortener-keygenerator:latest -t soserdev/shortener-keygenerator:0.0.1 -f Dockerfile .
```

Start Redis + App:

```bash
docker compose -f docker-compose.yaml up
```

Start Redis + App in background:

```bash
docker compose -f docker-compose.yaml up -d
```

Stop the containers for Redis + App and remove the containers:

```bash
docker compose -f docker-compose.yaml down
```

## Dev

Just simply start in IntelliJ or use:

```bash
mvn spring-boot:run
```

Run integration tests:

```bash
mvn clean package verify
```

## Test It

Get counter (for counter name "default"):

```bash
curl http://localhost:8080/api/keys/next
```

The response you get looks like:

```bash
{"id":4794,"key":"1fk"}
```

Get counter for a specific counter name (e.g. "mycounter"):

```bash
curl http://localhost:8080/api/keys/next/mycounter
```


## Wipe Stored Data

If you want to wipe Redis data stored in named volumes run:

```bash
docker compose -f docker-compose.yaml down --volumes
```

>⚠️ This will delete all persisted Redis data.

Clean Everything — Images, Networks, Volumes — full reset:

```bash
docker compose -f docker-compose.yaml down --volumes --rmi all --remove-orphans
```

>⚠️ This will delete all persisted Redis data and all Images!

