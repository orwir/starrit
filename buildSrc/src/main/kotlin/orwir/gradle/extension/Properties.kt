package orwir.gradle.extension

import org.gradle.api.Project

inline fun <reified T> Project.gradleProperty(name: String, default: T = defaultForType()): T =
    if (project.hasProperty(name)) {
        property(name)
    } else {
        default
    } as T

inline fun <reified T> Project.gradleProperty(name: String): T {
    if (project.hasProperty(name)) {
        return property(name) as T
    }
    throw IllegalArgumentException("Property [$name] not found!")
}

inline fun <reified T> defaultForType(): T =
    when (T::class) {
        String::class -> "" as T
        Int::class -> 0 as T
        Boolean::class -> false as T
        Float::class -> 0F as T
        Long::class -> 0L as T
        else -> throw IllegalArgumentException("Default value not found for type ${T::class}")
    }