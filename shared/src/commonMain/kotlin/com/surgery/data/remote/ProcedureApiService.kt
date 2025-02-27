package com.surgery.data.remote

import com.surgery.data.model.ProcedureDetailsDto
import com.surgery.data.model.ProcedureDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

internal class ProcedureApiService(private val client: HttpClient, private val serverUrl: String) :
    ProcedureSource {
    override suspend fun getProcedureList(): Result<List<ProcedureDto>> {
        return client.getRequest(urlString = "$serverUrl/api/v3/procedures?format=json")
    }

    override suspend fun getProcedureDetails(procedureId: String): Result<ProcedureDetailsDto> {
        return client.getRequest(urlString = "$serverUrl/api/v3/procedures/${procedureId}?format=json")
    }
}

private suspend inline fun <reified T> HttpClient.getRequest(urlString: String): Result<T> {
    return try {
        val response: HttpResponse = get(urlString = urlString)
        when (response.status) {
            HttpStatusCode.OK -> Result.success(response.body())
            HttpStatusCode.NotFound -> Result.failure(Exception("Not Found"))
            HttpStatusCode.Unauthorized -> Result.failure(Exception("Unauthorized"))
            else -> Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}