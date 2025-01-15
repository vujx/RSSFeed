package com.rssfeed.core.dictionary

import androidx.annotation.StringRes

interface Dictionary {
  fun getString(@StringRes key: Int): String
}
