import orwir.gradle.extension.library

plugins {
    id(Build.Plugin.library)
    id(Build.Plugin.kotlin_android)
    id(Build.Plugin.kotlin_android_extensions)
    id(Build.Plugin.simple_android)
}

android {
    dataBinding { isEnabled = true }
}

dependencies {
    library(Starrit.Library.core)
    library(Starrit.Library.view)
    library(Starrit.Library.listing)
    library(Starrit.Library.videoplayer)
    library(Starrit.Library.banner)
    implementation(Library.AndroidX.coordinator_layout)
    implementation(Library.AndroidX.constraint_layout)
    implementation(Library.AndroidX.paging)
}