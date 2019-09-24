## redireKtor
redireKtor es un "acortador de URL" implementado en Kotlin y Ktor, para practicar ambas cosas.
No esperes muchas funcionalidades (aún).

### Ejecución
compilar con `mvn clean package` (no sé aún cómo hacerlo desde el Intellij IDEA) y luego
ejecutar con `java -jar target/redireKtor-0.0.1-jar-with-dependencies.jar`

Como parámetro se le puede pasar el puerto en donde se desea escuchar (Escucha por defecto en
8080). Ej.:`java -jar target/redireKtor-0.0.1-jar-with-dependencies.jar -port=8180`
 