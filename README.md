Sort of a Hello World style learning to set up the foundation/plumbing for an app. 

The goal here is to get this turned into a bootstrap script - Enter app name, boom, all set up ready to go.

Architecturally, fairly straightforward:
* MVVM / Repository Pattern
* Not using MVI or Use Cases. These aren't universal and different app complexity may not need any.
* Using KMP standards such as Koin for DI, Ktor for networking, kotlinx.serialization for json

* `/composeApp` is for shared code across platforms (just iOS/Android now, but can support web/desktop).
  It contains a few subfolders:
  - `commonMain` is for code that’s common for all targets.
  - `androidMain` will hold very little, but does need to extend `Application` for running Koin at startup.
  - `iosMain` also holds very little, currently just setup for Koin in `MainViewController.kt`

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.
* Note: There is no `/androidApp`. Some people keep an extra piece here, but I don't see much point. In the `composeApp` `build.gradle.kts` 
you have an Android section for anything you need included specific to Android, and Kotlin plays way nicer with Android anyways.