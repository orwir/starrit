package orwir.gradle.extension

import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.feature(name: String, type: String = "implementation") {
    add(type, project(":features:$name"))
}

fun DependencyHandlerScope.library(name: String, type: String = "implementation") {
    add(type, project(":libraries:$name"))
}

fun DependencyHandlerScope.widget(name: String, type: String = "implementation") {
    add(type, project(":widgets:$name"))
}