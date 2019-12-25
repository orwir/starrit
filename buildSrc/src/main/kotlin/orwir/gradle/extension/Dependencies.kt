package orwir.gradle.extension

import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.feature(name: String) {
    add("implementation", project(":features:$name"))
}

fun DependencyHandlerScope.library(name: String) {
    add("implementation", project(":libraries:$name"))
}

fun DependencyHandlerScope.unitTestsLibraries() {
    add("testImplementation", Library.junit)
    add("testImplementation", Library.truth)
    add("testImplementation", Library.mockk)
}