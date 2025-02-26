package com.surgery.di

import org.koin.core.qualifier.named
import org.koin.dsl.module

fun appModule() = module {
    single(named(name = "SERVER_URL")) { "https://staging.touchsurgery.com" }
}