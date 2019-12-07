// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Build.Dependency.androidGradle)
        classpath(Build.Dependency.kotlinGradle)
        classpath(Build.Dependency.navigationSafeArgs)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean").configure {
    delete("build")
}