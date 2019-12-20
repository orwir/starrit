import com.android.build.gradle.internal.dsl.DefaultConfig

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Android.Sdk.compile)
    buildToolsVersion = Android.buildToolsVersion

    defaultConfig {
        minSdkVersion(Android.Sdk.min)
        targetSdkVersion(Android.Sdk.target)

        val schema = Android.Application.schema
        val host = Android.Application.authorizationHost
        val clientID: String by project
        val credentialsB64: String by project

        stringField("SCHEMA", schema)
        stringField("HOST", host)
        stringField("REDIRECT_URI", "$schema://$host")
        stringField("CLIENT_ID", clientID)
        stringField("CREDENTIALS_B64", credentialsB64)
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

    compileOptions {
        sourceCompatibility = Android.javaVersion
        targetCompatibility = Android.javaVersion
    }

    kotlinOptions {
        jvmTarget = Android.javaVersion.toString()
    }

    dataBinding {
        isEnabled = true
    }

}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":common"))
    implementation(Library.AndroidX.constraintLayout)
    implementation(Library.Squareup.retrofit)
}

fun DefaultConfig.stringField(name: String, value: String) {
    buildConfigField("String", name, "\"$value\"")
}