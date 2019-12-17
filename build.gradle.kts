// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Build.Library.androidGradle)
        classpath(Build.Library.kotlinGradle)
        classpath(Build.Library.navSafeArgs)
        classpath(Build.Library.junit5)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}