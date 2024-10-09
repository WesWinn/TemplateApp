package kmp.template.app

import org.koin.dsl.module


val iosDbModule = module {
    single { getDatabaseBuilder() } // Provide the iOS-specific builder
}

val iosModules = listOf(iosDbModule)
