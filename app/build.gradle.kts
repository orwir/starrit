import orwir.gradle.extension.feature
import orwir.gradle.extension.library

plugins {
    id(Build.Plugin.application)
    id(Build.Plugin.kotlin_android)
    id(Build.Plugin.kotlin_android_extensions)
    id(Build.Plugin.kotlin_navigation_safeargs)
    id(Build.Plugin.simple_android)
}

android {
    defaultConfig {
        applicationId = Starrit.applicationID
        versionCode = Android.Application.versionCode(project)
        versionName = Android.Application.versionName(project)

        manifestPlaceholders = mapOf(
            "authorizationSchema" to Starrit.DeepLink.Authorization.schema,
            "authorizationHost" to Starrit.DeepLink.Authorization.host
        )
    }
    dataBinding { isEnabled = true }
}

dependencies {
    library(Starrit.Library.core)
    library(Starrit.Library.view)
    library(Starrit.Library.access)
    library(Starrit.Library.listing)

    feature(Starrit.Feature.splash)
    feature(Starrit.Feature.login)
    feature(Starrit.Feature.feed)

    implementation(Library.AndroidX.browser)
    implementation(Library.Squareup.okhttp)
    implementation(Library.Squareup.loggin_interceptor)
    implementation(Library.Squareup.moshi)
    implementation(Library.Squareup.moshi_kotlin)
    implementation(Library.Squareup.retrofit)
    implementation(Library.Squareup.retrofit_converter_moshi)
}