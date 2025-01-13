package com.rssfeed.core.dictionary

import androidx.annotation.StringRes

interface Dictionary {
  fun getString(@StringRes key: Int): String
  fun getString(@StringRes key: Int, vararg arguments: Any?): String
}
