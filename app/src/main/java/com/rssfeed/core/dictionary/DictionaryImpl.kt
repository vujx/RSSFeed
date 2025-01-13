package com.rssfeed.core.dictionary

import android.content.Context

class DictionaryImpl(
  private val context: Context,
) : Dictionary {
  override fun getString(key: Int): String = context.getString(key)
  override fun getString(key: Int, vararg arguments: Any?): String =
    context.getString(key, *arguments)
}
