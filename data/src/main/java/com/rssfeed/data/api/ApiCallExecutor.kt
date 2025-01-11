package com.rssfeed.data.api

import arrow.core.Either
import com.rssfeed.domain.error.RssFeedError
import io.ktor.client.plugins.ResponseException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
  apiCall: suspend () -> T,
): Either<RssFeedError, T> = Either.catch { apiCall() }.mapLeft { error ->
  when (error) {
    is ResponseException -> {
      when (error.response.status.value) {
        NOT_FOUND_ERROR_CODE -> RssFeedError.NotFoundError
        else -> RssFeedError.UnknownError
      }
    }

    is UnknownHostException -> RssFeedError.ConnectivityError
    else -> RssFeedError.UnknownError
  }
}

private const val NOT_FOUND_ERROR_CODE = 404
