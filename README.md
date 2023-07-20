# How to integrate JSight in Spring Boot manually

1. Generate pure Spring Boot project using https://start.spring.io.
2. Download JSight binaries:
   - put `libjsight.so` into `/usr/local/lib` folder (or other folder, which
     you can specify later).
   - put `libjsightjava.so` into `/usr/java/packages/lib` (or other folder for java
     binary libraries).
3. Copy three JSight java adapter source files into `./src/main/java/io/jsight` 
   folder: `JSight.java`, `ErrorPosition.java`, `ValidationError.java`.
4. 