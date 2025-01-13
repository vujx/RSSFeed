package com.rssfeed.di

import com.rssfeed.core.mapper.ErrorMessageMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mapperModule = module {
  factoryOf(::ErrorMessageMapper)
}
