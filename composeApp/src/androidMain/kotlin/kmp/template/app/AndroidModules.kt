package kmp.template.app

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val androidDbModule = module {
    // This single injects the Android context as a parameter
    single { getDatabaseBuilder(androidApplication()).build() }
}

val androidModules = listOf(androidDbModule)
