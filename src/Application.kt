package me.luisc.k

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import io.ktor.http.*

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
        get("/{..}") {
            when(call.request.uri.substring(1)){
                "lc" -> call.respondRedirect("http://luisc.me", true)
                "li" -> call.respondRedirect("http://aprendalinux.com", true)
                "ja" -> call.respondRedirect("http://aprendajava.com", true)
                "c" -> call.respondRedirect("http://lenguajec.com", true)
                else -> call.respondText("Epaaa!. Llamaste a ${call.request.uri}", contentType = ContentType.Text.Plain)
            }
        }
    }
}

