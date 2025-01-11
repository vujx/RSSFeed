package com.rssfeed.data.di

import com.rssfeed.data.Database
import com.rssfeed.data.db.ArticleDao
import com.rssfeed.data.db.ArticleDaoImpl
import com.rssfeed.data.db.ChannelDao
import com.rssfeed.data.db.ChannelDaoImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
  single { Database(get()) }
  singleOf(::ChannelDaoImpl) bind ChannelDao::class
  singleOf(::ArticleDaoImpl) bind ArticleDao::class
}
