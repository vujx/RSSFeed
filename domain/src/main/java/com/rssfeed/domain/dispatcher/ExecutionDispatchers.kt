package com.rssfeed.domain.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

data class ExecutionDispatchers(
  val io: CoroutineDispatcher,
  val computation: CoroutineDispatcher,
  val main: CoroutineDispatcher,
)
