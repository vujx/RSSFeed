package com.rssfeed.core.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<E> : ViewModel() {

  abstract fun onEvent(event: E)
}

const val TIMEOUT_DELAY = 5_000L
