package com.surgery.di

import com.surgery.data.local.DatabaseDriverFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

actual fun createHttpClient(): HttpClient {
    return HttpClient(Darwin.create()) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
            )
        }
        install(WebSockets)
    }
}

actual fun createDatabaseDriverFactory() = module {
    single { DatabaseDriverFactory() }
}