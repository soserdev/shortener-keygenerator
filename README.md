# KEY GENERATOR SERVICE

## About

A simple Key Generator Service to generate unique keys for an Url Shortener.

This project is based upon:

- Spring Boot
- Redis
- Testcontainers

## Usage Docker

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

Get counter (for counter name "default"):

```bash
curl http://localhost:8080/api/keys/next
```

The response you get looks like:

```bash
{"id":4794,"key":"1fk"}
```

Get counter for a specific counter name (e.g. "my-counter"):

```bash
curl http://localhost:8080/api/keys/next/mycounter
```

Stop the containers for Redis + App and remove the containers:

```bash
docker compose -f docker-compose.yaml down
```

## Usage Kubernetes

Apply Kubernetes configuration for _Redis_:

```bash
kubectl apply -f k8s/redis-deployment.yaml
kubectl apply -f k8s/redis-service.yaml
kubectl apply -f k8s/app-deployment.yaml
kubectl apply -f k8s/app-service.yaml
```

```bash
$> kubectl get all
NAME                                          READY   STATUS    RESTARTS   AGE
pod/redis-76cc599b-6t5s9                      1/1     Running   0          11m
pod/shortener-keygenerator-5547699df4-4bzgb   1/1     Running   0          7m

NAME                             TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)    AGE
service/redis                    ClusterIP   10.108.195.242   <none>        6379/TCP   11m
service/shortener-keygenerator   ClusterIP   10.99.184.102    <none>        80/TCP     7m

NAME                                     READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/redis                    1/1     1            1           11m
deployment.apps/shortener-keygenerator   1/1     1            1           7m

NAME                                                DESIRED   CURRENT   READY   AGE
replicaset.apps/redis-76cc599b                      1         1         1       11h
replicaset.apps/shortener-keygenerator-5547699df4   1         1         1       7m
```

Add port forwarding for key generator:

```bash
kubectl port-forward pod/shortener-keygenerator-5547699df4-4bzgb 8080:80
```

Get next key:

```bash
$> curl http://localhost:8080/api/keys/next
{"id":4784,"key":"1fa"}
```

Connect to _Redis_ and execute a command using `redis-cli`:

```bash
$> kubectl exec -it pod/redis-76cc599b-6t5s9 -- redis-cli
127.0.0.1:6379> GET keygen:counter:default
"4784"
```

Delete deployments and services:

```bash
kubectl delete -f k8s/app-service.yaml
kubectl delete -f k8s/app-deployment.yaml
kubectl delete -f k8s/redis-service.yaml
kubectl delete -f k8s/redis-deployment.yaml
```

## Dev

This project uses Docker Compose:

- See file named `compose.yaml` for Redis configuration to start only Redis.
- See file named `docker-compose.yaml` to start Redis + App.

This project uses [Testcontainers at development time](https://docs.spring.io/spring-boot/3.5.6/reference/features/dev-services.html#features.dev-services.testcontainers).

Just simply start in IntelliJ or use:

```bash
mvn spring-boot:run
```

Run integration tests:

```bash
mvn clean package verify
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

