package io.embrace.opentelemetry.example.java

import android.app.Application
import io.embrace.opentelemetry.example.java.createInstrumentationWithOtelJava

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createInstrumentationWithOtelJava()
    }
}
