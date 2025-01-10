package com.rssfeed.data.di

import com.rssfeed.data.api.ApiService
import com.rssfeed.data.api.ApiServiceImpl
import com.rssfeed.data.api.createHttpClient
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::createHttpClient)
    singleOf(::ApiServiceImpl) bind ApiService::class
    factory {
        XML {
            unknownChildHandler = XmlConfig.IGNORING_UNKNOWN_CHILD_HANDLER
            repairNamespaces = true
        }
    }
}
