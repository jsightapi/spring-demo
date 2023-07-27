# Spring Boot Mock Server

## How to run this Spring Boot mock server with JSight

1. `cd docker` and `docker compose up`.
2. Open in Postman `./tests/JSight.postman_collection.json`. Check all the requests on `localhost:8080`.
3. Open in JMeter `./tests/load-tests.jmx`. Run tests on `localhost:8080`.
4. Open in browser http://localhost:8080/_stat to get pid and statistical info from `libjsight.so` library (`curl http://localhost:8080/_stat`).
5. Go into test stand docker: `sudo docker exec -it jsight_spring_boot bash`  
   Check memory in `/proc/[pid]/statm` file, parameter `resident` (2nd figure).
   Loop this using e. g. the following command: `while true; do cat /proc/53/statm; sleep 1; done`.
   Explanation of figures in this file:
   - `size` - total program size (pages) (same as VmSize in status)
   - `resident` - size of memory portions (pages) (same as VmRSS in status)
   - `shared` - number of pages that are shared (i.e. backed by a file, same as RssFile+RssShmem in status) includes data segment)
   - `lrs` - number of pages of library (always 0 on 2.6)
   - `drs` - number of pages of data/stack (including libs; broken, includes library text)
   - `dt` - number of dirty pages (always 0 on 2.6)


