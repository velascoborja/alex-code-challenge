package com.surgery.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(appModules: List<Module> = emptyList(), config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(appModules)
        modules(commonModule(), createDatabaseDriverFactory())
    }
}
