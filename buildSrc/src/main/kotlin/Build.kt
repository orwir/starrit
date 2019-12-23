object Build {

    const val kotlinVersion = "1.3.61"

    object Plugin {
        const val application = "com.android.application"
        const val library = "com.android.library"
        const val kotlin_android = "org.jetbrains.kotlin.android"
        const val kotlin_android_extensions = "org.jetbrains.kotlin.android.extensions"
        const val kotlin_kapt = "org.jetbrains.kotlin.kapt"
        const val kotlin_navigation_safeargs = "androidx.navigation.safeargs.kotlin"
        const val simple_android = "orwir.android.simple"
    }

    object Library {
        const val androidGradle = "com.android.tools.build:gradle:3.5.3"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val navSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:2.1.0"
    }

}