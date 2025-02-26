package com.surgery.di

import app.cash.sqldelight.db.SqlDriver
import com.surgery.data.local.Database
import com.surgery.data.local.DatabaseDriverFactory
import com.surgery.data.local.SqlDelightDatabase
import com.surgery.data.remote.ProcedureApiService
import com.surgery.data.remote.ProcedureSource
import com.surgery.data.repository.ProcedureRemoteRepository
import com.surgery.db.SurgeryDatabase
import com.surgery.domain.repository.ProcedureRepository
import com.surgery.domain.usecase.GetFavouriteProcedureListUseCase
import com.surgery.domain.usecase.GetProcedureDetailsUseCase
import com.surgery.domain.usecase.GetProcedureListUseCase
import com.surgery.domain.usecase.RefreshProcedureListUseCase
import com.surgery.domain.usecase.ToggleProcedureFavoriteUseCase
import com.surgery.view_model.ProceduresListViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect fun createHttpClient(): HttpClient

expect fun createDatabaseDriverFactory(): Module

fun commonModule() = module {
    single { createHttpClient() }
    single<ProcedureSource> { ProcedureApiService(get(), get(named("SERVER_URL"))) }
    single<ProcedureRepository> { ProcedureRemoteRepository(get(), get()) }
    single { GetProcedureListUseCase(get()) }
    single { GetProcedureDetailsUseCase(get()) }
    single { ToggleProcedureFavoriteUseCase(get()) }
    single { RefreshProcedureListUseCase(get()) }
    single { GetFavouriteProcedureListUseCase(get()) }
    factory { ProceduresListViewModel(get(), get(), get(), get(), get()) }

    single { SurgeryDatabase(get()) }
    single<Database> { SqlDelightDatabase(get()) }
    single<SqlDriver> { get<DatabaseDriverFactory>().create() }
}