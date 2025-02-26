package com.surgery.di

import com.surgery.view_model.ProceduresListViewModel
import org.koin.mp.KoinPlatform

fun getProceduresListViewModel(): ProceduresListViewModel = KoinPlatform.getKoin().get()

fun getProcedureDetailsViewModel(): ProcedureDetailsViewModel = KoinPlatform.getKoin().get()