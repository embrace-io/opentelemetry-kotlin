package io.embrace.opentelemetry.example.compat

import android.app.Application
import io.embrace.opentelemetry.example.compat.createInstrumentationWithOtelJava

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createInstrumentationWithOtelJava()
    }
}
