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

        val schema = "gazzit"
        val host = "authorization"

        manifestPlaceholders = mapOf(
            "schema" to schema,
            "host" to host
        )

        stringField("SCHEMA", schema)
        stringField("HOST", host)
        stringField("REDIRECT_URI", "$schema://$host")
        stringField("CLIENT_ID", gradleProperty("clientID"))
        stringField("SCHEMA", gradleProperty("credentialsB64"))
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

    compileOptions {
        sourceCompatibility = Android.javaVersion
        targetCompatibility = Android.javaVersion
    }

    kotlinOptions {
        jvmTarget = Android.javaVersion.toString()
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":common"))
}

fun DefaultConfig.stringField(name: String, value: String) {
    buildConfigField("String", name, "\"$value\"")
}