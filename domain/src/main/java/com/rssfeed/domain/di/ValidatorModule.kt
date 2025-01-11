package com.rssfeed.domain.di

import com.rssfeed.domain.validator.RssFeedUrlValidator
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val validatorModule = module {
  factoryOf(::RssFeedUrlValidator)
}
