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
        applicationId = Gazzit.applicationID
        versionCode = Android.Application.versionCode(project)
        versionName = Android.Application.versionName(project)

        manifestPlaceholders = mapOf(
            "authorizationSchema" to Gazzit.DeepLink.Authorization.schema,
            "authorizationHost" to Gazzit.DeepLink.Authorization.host
        )
    }
    dataBinding { isEnabled = true }
}

dependencies {
    library(Gazzit.Library.core)
    library(Gazzit.Library.view)
    library(Gazzit.Library.authorization)
    library(Gazzit.Library.listing)

    feature(Gazzit.Feature.splash)
    feature(Gazzit.Feature.login)
    feature(Gazzit.Feature.feed)

    implementation(Library.AndroidX.appcompat)
    implementation(Library.AndroidX.fragment)
    implementation(Library.AndroidX.navigationFragment)
    implementation(Library.AndroidX.navigationUi)
    implementation(Library.AndroidX.browser)

    implementation(Library.Squareup.okhttp)
    implementation(Library.Squareup.logginInterceptor)
    implementation(Library.Squareup.moshi)
    implementation(Library.Squareup.moshiKotlin)
}