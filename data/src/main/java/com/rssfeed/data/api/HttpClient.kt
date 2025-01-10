package com.rssfeed.data.api

import com.rssfeed.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE

fun createHttpClient() = HttpClient(OkHttp) {
    install(HttpTimeout) {
        requestTimeoutMillis = NETWORK_TIMEOUT_MILLIS
        connectTimeoutMillis = NETWORK_TIMEOUT_MILLIS
        socketTimeoutMillis = NETWORK_TIMEOUT_MILLIS
    }

    if (BuildConfig.DEBUG) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}

private const val NETWORK_TIMEOUT_MILLIS = 10_000L
