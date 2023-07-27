# Spring Boot Mock Server

## How to run this Spring Boot mock server with JSight

1. `cd docker` and `docker compose up`.
2. Open in Postman `./tests/JSight.postman_collection.json`. Check all the requests on `localhost:8080`.
3. Open in JMeter `./tests/load-tests.jmx`. Run tests on `localhost:8080`.
4. Open in browser http://localhost:8080/_stat to get pid and statistical info from `libjsight.so` library (`curl http://localhost:8080/_stat`).
5. Check memory in `/proc/[pid]/statm file`.


