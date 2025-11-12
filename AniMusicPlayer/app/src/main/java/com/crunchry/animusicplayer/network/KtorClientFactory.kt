package com.crunchry.animusicplayer.network

import com.crunchry.animusicplayer.network.data.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.appendEncodedPathSegments
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClientFactory {
    private const val CONNECTION_TIMEOUT: Long = 12000
    private const val READ_WRITE_TIMEOUT: Long = 12000

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    fun simpleClient(): HttpClient {
        return HttpClient(Android) {
            expectSuccess = false

            install(ContentNegotiation) {
                json(json)
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.NONE
            }

            install(HttpTimeout) {
                requestTimeoutMillis = CONNECTION_TIMEOUT
                connectTimeoutMillis = CONNECTION_TIMEOUT
                socketTimeoutMillis = READ_WRITE_TIMEOUT
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = Constants.baseUrl()
                }
            }
        }
    }

    fun build(engine: HttpClientEngine = Android.create()): HttpClient {
        return HttpClient(engine) {
            expectSuccess = false

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(json)
            }

            install(HttpTimeout) {
                requestTimeoutMillis = CONNECTION_TIMEOUT
                connectTimeoutMillis = CONNECTION_TIMEOUT
                socketTimeoutMillis = READ_WRITE_TIMEOUT
            }

            defaultRequest {
                accept(ContentType.Application.Json)
                url {
                    protocol = URLProtocol.HTTP
                    host = Constants.baseUrl()
                }
            }
        }
    }
}

object Constants {
    fun baseUrl() = "nonremonstrant-marlo-noncholeric.ngrok-free.dev"
}

/**
 * Post the data using the Ktor [HttpClient].
 */
@Suppress("TooGenericExceptionCaught")
suspend inline fun <reified T> HttpClient.sendPost(
    path: String,
    requestBuilder: HttpRequestBuilder.() -> Unit = {}
): ApiResult<T> {
    return try {
        val response: HttpResponse = this.post {
            url {
                appendEncodedPathSegments(path)
            }
            apply(requestBuilder)
        }
        response.asResult()
    } catch (e: Exception) {
        handleNetworkException(e)
    }
}

suspend inline fun <reified T> HttpResponse.asResult(): ApiResult<T> {
    return runCatching<ApiResult<T>> {
        if (this.status.isSuccess()) {
            ApiResult.Success(this.body<T>(), this.status.value)
        } else {
            ApiResult.Error(this.status.value, this.status.description)
        }
    }.getOrElse {
        handleNetworkException(it)
    }
}

fun handleNetworkException(exception: Throwable): ApiResult.Error {
    return ApiResult.Error(500, exception.message ?: "Network error")
}
