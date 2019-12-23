plugins {
    id(Build.Plugin.library)
    id(Build.Plugin.kotlin_android)
    id(Build.Plugin.kotlin_android_extensions)
    id(Build.Plugin.simple_android)
}

dependencies {
    api(Library.exoPlayer)

    implementation(Library.Kotlin.std)
    implementation(Library.Kotlin.coroutines)
    implementation(Library.Kotlin.reflect)
    implementation(Library.AndroidX.constraintLayout)
    implementation(Library.Coil.coil)
}