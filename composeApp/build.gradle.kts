import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room.gradle.plugin)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.compose.navigation)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core) // Use the latest stable version

            // Koin Dependencies
            implementation(project.dependencies.platform(libs.koin.bom)) // Add the latest version here
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Ktor Dependencies
            implementation(project.dependencies.platform(libs.ktor.bom))
            implementation(libs.ktor.client.core) // Core client library
            implementation(libs.ktor.client.cio) // CIO engine (can be replaced with another like OkHttp or iOS engine)
            implementation(libs.ktor.client.content.negotiation) // For serialization support
            implementation(libs.ktor.serialization.kotlinx.json) // For JSON serialization`

            // Coroutines
            implementation(libs.coroutines.extensions)

            // Room
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
        }
        val iosMain by creating {
            dependencies{
                implementation(libs.ktor.client.darwin) // Use Darwin for iOS
            }
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        // Common compiler options applied to all Kotlin source sets
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}
// TODO: Make these hard values
android {
    namespace = "kmp.template.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "kmp.template.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        implementation(libs.koin.android)
        debugImplementation(compose.uiTooling)

    }
}

dependencies {
    add("kspAndroid", libs.room.compiler) // Room compiler for Android (KSP)
    add("kspIosSimulatorArm64", libs.room.compiler) // Room compiler for iOS Simulator
    add("kspIosX64", libs.room.compiler) // Room compiler for iOS X64
    add("kspIosArm64", libs.room.compiler) // Room compiler for iOS Arm64
}

room {
    schemaDirectory("$projectDir/schemas")
}

