package kmp.template.app

import androidx.compose.ui.window.ComposeUIViewController
import di.commonModules
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController { App() }

fun doInitKoin() {
    startKoin {
        modules(commonModules + iosModules)
    }
}
