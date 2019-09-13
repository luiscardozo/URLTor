package me.luisc.k

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
        get("/hola") {
            call.respondText("Chau!", contentType = ContentType.Text.Plain)
        }

        get("/dbconnect"){
            dbConnect()
            call.respondText("Mirá el log!", contentType = ContentType.Text.Plain)
        }

        get("/{..}") {
            when(call.request.uri.substring(1)){
                /*
                "lc" -> call.respondRedirect("http://luisc.me", true)
                "li" -> call.respondRedirect("http://aprendalinux.com", true)
                "ja" -> call.respondRedirect("http://aprendajava.com", true)
                "c" -> call.respondRedirect("http://lenguajec.com", true)
                 */
                //"favicon.ico" -> call.respondFile("favicon.ico",)
                else -> {
                    val longURL = dbSearchURL(call.request.uri.substring(1))
                    if(longURL != "error"){
                        call.respondRedirect(longURL, true)
                    } else {
                        call.respondText("Enlace incorrecto. Favor verifica. Dirección: ${call.request.uri}", contentType = ContentType.Text.Plain)
                    }
                }
            }
        }
        if(testing) println("Fin del test") //solo para usar (por ahora) la variable que ya venía en el proyecto original (start.ktor.io)
    }
}

object Redir: Table(){
    val shortURL = varchar("shortURL",10).primaryKey()
    val longURL = varchar("longURL",length = 255)
}

fun dbSetupDSL(){
    transaction {
        SchemaUtils.create(Redir)

        //val aLnx =
        Redir.insert {
            it[shortURL] = "aj"
            it[longURL] = "https://aprendajava.com"
        } //get Redir.longURL

        Redir.insert {
            it[shortURL] = "al"
            it[longURL] = "https://aprendalinux.com"
        }

        Redir.insert {
            it[shortURL] = "c"
            it[longURL] = "https://lenguajec.com"
        }

        Redir.insert {
            it[shortURL] = "lc"
            it[longURL] = "https://luisc.me"
        }

        print("Todos los URLs:")
        for(url in Redir.selectAll()){
            print("${url[Redir.shortURL]} ==> ${url[Redir.longURL]}\n")
        }

        val buscarURL = "lc"
        val query = Redir.select{Redir.shortURL eq buscarURL}.limit(1)
        query.forEach{
            println(it[Redir.shortURL])
        }
        println("Cantidad de filas: ${query.count()}")
    }
}

fun dbSearchURL(shortURL: String) : String {

    dbConnect()

    var longURL = "error"

    transaction {
        val query = Redir.select{Redir.shortURL eq shortURL}.limit(1)
        query.forEach {
            longURL = it[Redir.longURL]
        }
    }

    println("longURL: $longURL")
    return longURL
}

fun dbConnect(){
    Database.connect("jdbc:postgresql://localhost:5432/urltor", driver = "org.postgresql.Driver", user = "usrTraza", password =  "winner")

    transaction {
        addLogger(StdOutSqlLogger)
    }
}