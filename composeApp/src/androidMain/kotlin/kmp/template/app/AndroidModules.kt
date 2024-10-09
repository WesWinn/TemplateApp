package kmp.template.app

import android.content.Context
import org.koin.dsl.module

val androidDbModule = module {
    single { (context: Context) -> getDatabaseBuilder(context) } // Provide the Android-specific builder
}

val androidModules = listOf(androidDbModule)