package io.embrace.opentelemetry.kotlin.telescope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import io.embrace.opentelemetry.kotlin.telescope.ui.theme.TelescopeTheme

class MainActivity : ComponentActivity() {

    private lateinit var sessionTelemetry: SessionTelemetry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionTelemetry = SessionTelemetry(resources)
        setupLifecycleObserver()
        enableEdgeToEdge()
        setContent {
            TelescopeTheme {
                TelescopeShopApp(sessionTelemetry)
            }
        }
    }

    private fun setupLifecycleObserver() {
        lifecycle.addObserver(LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    sessionTelemetry.onAppStart()
                }

                Lifecycle.Event.ON_STOP -> {
                    sessionTelemetry.onAppStop()
                }

                else -> {}
            }
        })
    }
}

