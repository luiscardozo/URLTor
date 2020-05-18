# redireKtor

redireKtor is an URL shortener implemented in Kotlin and Ktor, as a practice to prepare for my talk on **Kotlin Everywhere** Ciudad del Este 2019.

## Configuration
Currently there is not a separate configuration file. RedireKtor requires a database (and a JDBC Driver), both of with you need to change in `src/Application.kt`, in the `fun dbConnect()` function.

## Running
Compile with `mvn clean package` and then execute with `java -jar target/redireKtor-0.0.1-jar-with-dependencies.jar`

You can pass the listening port as a parameter ( -port= ). Default port is 8080.

For example, to change to port 8081: `java -jar target/redireKtor-0.0.1-jar-with-dependencies.jar -port=8180`
