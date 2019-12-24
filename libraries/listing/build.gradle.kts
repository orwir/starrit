import orwir.gradle.extension.library

plugins {
    id(Build.Plugin.library)
    id(Build.Plugin.kotlin_android)
    id(Build.Plugin.kotlin_android_extensions)
    id(Build.Plugin.kotlin_kapt)
    id(Build.Plugin.simple_android)
}

dependencies {
    library(Starrit.Library.core)
    implementation(Library.Squareup.retrofit)
    implementation(Library.Squareup.moshi)
    implementation(Library.Squareup.moshiKotlin)
    kapt(Library.Squareup.moshiKotlinCodgen)
}