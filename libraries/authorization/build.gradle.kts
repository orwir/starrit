import org.bouncycastle.util.encoders.Base64
import orwir.gradle.extension.library
import orwir.gradle.extension.stringField

plugins {
    id(Build.Plugin.library)
    id(Build.Plugin.kotlin_android)
    id(Build.Plugin.kotlin_android_extensions)
    id(Build.Plugin.kotlin_kapt)
    id(Build.Plugin.simple_android)
}

android {
    defaultConfig {
        val clientID: String by project
        val credentialsB64: String = "$clientID:".toByteArray().run(Base64::toBase64String)
        val schema = Starrit.DeepLink.Authorization.schema
        val host = Starrit.DeepLink.Authorization.host

        stringField("SCHEMA", schema)
        stringField("HOST", host)
        stringField("REDIRECT_URI", "$schema://$host")
        stringField("CLIENT_ID", clientID)
        stringField("CREDENTIALS_B64", credentialsB64)
    }
}

dependencies {
    library(Starrit.Library.core)
    implementation(Library.Squareup.okhttp)
    implementation(Library.Squareup.retrofit)
    implementation(Library.Squareup.moshi)
    implementation(Library.Squareup.moshiKotlin)
    kapt(Library.Squareup.moshiKotlinCodgen)
}