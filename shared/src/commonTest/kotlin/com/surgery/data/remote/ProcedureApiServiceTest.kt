package com.surgery.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProcedureApiServiceTest {

    private val mockEngine = MockEngine { request ->
        when (request.url.encodedPath) {
            "/api/v3/procedures" -> respond(
                content = proceduresJson,
                status = HttpStatusCode.OK,
                headers = headersOf("Content-Type", ContentType.Application.Json.toString()),
            )

            "/api/v3/procedures/ProcedureDetails" -> respond(
                content = procedureDetailsDtoJson,
                status = HttpStatusCode.OK,
                headers = headersOf("Content-Type", ContentType.Application.Json.toString()),
            )

            "/api/v3/procedures/Unauthorized" -> respondError(
                status = HttpStatusCode.Unauthorized
            )

            else -> respondError(
                status = HttpStatusCode.NotFound
            )
        }
    }

    private val mockClient = HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private val service = ProcedureApiService(mockClient, "http://localhost")

    @Test
    fun `test getProcedureList success`() = runBlocking {
        val result = service.getProcedureList()
        assertTrue(result.isSuccess)
        assertEquals(procedureDtoList, result.getOrNull())
    }

    @Test
    fun `test getProcedureDetails success`() = runBlocking {
        val result = service.getProcedureDetails("ProcedureDetails")
        assertTrue(result.isSuccess)
        assertEquals(procedureDetailsDto, result.getOrNull())
    }

    @Test
    fun `test getProcedureDetails not found`() = runBlocking {
        val result = service.getProcedureDetails("2")
        assertTrue(result.isFailure)
        assertEquals("Not Found", result.exceptionOrNull()?.message)
    }

    @Test
    fun `test getProcedureDetails Unauthorized`() = runBlocking {
        val result = service.getProcedureDetails("Unauthorized")
        assertTrue(result.isFailure)
        assertEquals("Unauthorized", result.exceptionOrNull()?.message)
    }
}