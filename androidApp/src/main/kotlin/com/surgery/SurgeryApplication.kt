package com.surgery

import android.app.Application
import com.surgery.di.appModule
import com.surgery.di.initKoin
import org.koin.android.ext.koin.androidContext

class SurgeryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(appModules = listOf(appModule())) {
            androidContext(this@SurgeryApplication)
        }
    }
}