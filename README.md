# Spring Boot Mock Server

## How to run this Spring Boot mock server with JSight

1. `cd docker` and `docker compose up`.
2. Copy the mock files `server.jst`, `response_code`, `response_headers` and `response_body` to
   the `./mock` folder.
3. Send test request to `localhost:8080`. 
   Examples: `curl --location 'http://localhost:8080/users' --header "Content-Type: application/json" --data '{"id": 1, "login": "john"}'`.
4. Open in browser `http://localhost:8080/_stat` to get pid and statistical info from `libjsight.so`
   library (`curl http://localhost:8080/_stat`);
   Check memory in `/proc/[pid]/statm` file.
5. Run auto tests using `https://github.com/jsightapi/validation-tests-http` repo using this
   command:

```bash
MOCK_SERVER_URL=http://localhost:8080 \
SPEC_EXCHANGE_DIR=/mnt/wind/opt/jsight/spring-demo/mock \
RESPONSE_EXCHANGE_DIR=/mnt/wind/opt/jsight/spring-demo/mock \
go test -v -timeout 99999s -run Test_Repo_Run
```