# How to run this Spring Boot demo server with JSight

1. Go to `./docker` folder: `cd docker`.
2. Run docker compose: `docker compose up` and wait until Spring Boot starts.
3. Send requests to `localhost:8080` according to API spec in
   `./src/main/resources/my-api-spec.jst`. 
   Examples:  
   - Good request:  
     `curl --location 'http://localhost:8080/users' --header "Content-Type: application/json" --data '{"id": 1, "login": "john"}'`.  
     You must get `"OK"` in response. 
   - Bad request:  
     `curl --location 'http://localhost:8080/users' --header "Content-Type: application/json" --data '{"id": 1}'`.  
     You must get JSight error in response.

# How to integrate JSight in Spring Boot manually

1. Generate pure Spring Boot project using https://start.spring.io.
2. Download JSight binaries:
   - put `libjsight.so` into `/usr/local/lib` folder (or other folder, which you can specify later).
   - put `libjsightjava.so` into `/usr/java/packages/lib` (or other folder for java binary
     libraries).
3. Copy JSight java adapter source files into `./src/main/java/io/jsight/` folder:
   - `./src/main/java/io/jsight/ErrorPosition.java`
   - `./src/main/java/io/jsight/JSight.java`
   - `./src/main/java/io/jsight/ValidationError.java`
   - `./src/main/java/io/jsight/spring/CachedHttpServletRequest.java`
   - `./src/main/java/io/jsight/spring/CachedServletInputStream.java`
   - `./src/main/java/io/jsight/spring/JSightFilter.java`
4. Put JSight your API specification file (e. g. `my-api-spec.jst`) into `./src/main/resources`
   folder.
5. Add `"io.jsight"` to `@ComponentScan` annotation in your main application class. (E. g.
   `@ComponentScan(basePackages = {"com.example.demo", "io.jsight"})`). This will tell Spring Boot
   to scan components in `io.jsight` namespace.