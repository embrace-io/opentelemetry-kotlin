package io.embrace.opentelemetry.example.kotlin

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createInstrumentationWithOtelKotlin()
    }
}
