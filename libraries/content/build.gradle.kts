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
    library(Starrit.Library.core)
    library(Starrit.Library.view)
    library(Starrit.Library.access)
    implementation(Library.AndroidX.paging)
    implementation(Library.Squareup.retrofit)
    implementation(Library.Squareup.moshi)
    implementation(Library.Squareup.moshi_kotlin)
    kapt(Library.Squareup.moshi_kotlin_codgen)
}