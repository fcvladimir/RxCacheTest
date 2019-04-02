package com.test

import android.app.Application
import com.test.di.mainModule
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(mainModule), logger = AndroidLogger())
    }
}