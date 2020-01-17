import orwir.gradle.extension.library
import orwir.gradle.extension.widget

plugins {
    id(Build.Plugin.library)
    id(Build.Plugin.kotlin_android)
    id(Build.Plugin.kotlin_android_extensions)
    id(Build.Plugin.kotlin_kapt)
    id(Build.Plugin.simple_android)
}

android {
    dataBinding { isEnabled = true }
}

dependencies {
    // Common widgets
    api(Library.AndroidX.appcompat)
    api(Library.AndroidX.material)
    api(Library.AndroidX.fragment)
    api(Library.AndroidX.fragment_ktx)
    api(Library.AndroidX.lifecycle_viewModel)
    api(Library.AndroidX.recycler_view)
    api(Library.AndroidX.coordinator_layout)
    api(Library.AndroidX.constraint_layout)
    api(Library.AndroidX.navigation_fragment)
    api(Library.AndroidX.navigation_ui)
    widget(Starrit.Widget.banner, "api")
    widget(Starrit.Widget.videoplayer, "api")

    // DI ViewModel extensions
    api(Library.Koin.android_viewmodel)

    // Image Loader
    api(Library.Coil.base)
    implementation(Library.Coil.gif)

    library(Starrit.Library.core)
}