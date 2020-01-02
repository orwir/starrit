import orwir.gradle.extension.library

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
    api(Library.AndroidX.appcompat)
    api(Library.AndroidX.fragment)

    library(Starrit.Library.core)
    implementation(Library.AndroidX.recyclerView)
}