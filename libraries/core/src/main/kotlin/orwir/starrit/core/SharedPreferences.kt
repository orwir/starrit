package orwir.starrit.core

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Wrapper for converting [T] to/from [String]
 * @param T type of object
 */
interface ObjectAdapter<T> {
    fun to(raw: String): T
    fun from(obj: T): String
}

/**
 * Creates delegate for object property in [SharedPreferences].
 * If [key] not passed simpleName+propertyName of holder class will be used.
 * @param T type of the property
 * @param prefs instance of [SharedPreferences]
 * @param adapter object converter
 * @param key unique identifier
 * @param defaultValue is used if property not set
 * @return property delegate [ReadWriteProperty]
 */
inline fun <reified T> objPref(
    prefs: SharedPreferences,
    adapter: ObjectAdapter<T>,
    key: String? = null,
    defaultValue: T? = null
) = object : ReadWriteProperty<Any, T?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T? =
        prefs[key ?: getKey(thisRef, property), ""]
            .takeIf { it.isNotBlank() }
            ?.let(adapter::to)
            ?: defaultValue

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        prefs[key ?: getKey(thisRef, property)] = value?.let(adapter::from) ?: ""
    }

}

/**
 * Creates delegate for enum property in [SharedPreferences].
 * If [key] not passed simpleName+propertyName of holder class will be used.
 * @param T type of the property
 * @param prefs instance of [SharedPreferences]
 * @param key unique identifier
 * @param defaultValue is used if property not set
 * @return property delegate [ReadWriteProperty]
 */
inline fun <reified T : Enum<T>> enumPref(
    prefs: SharedPreferences,
    key: String? = null,
    defaultValue: T = enumValues<T>().first()
) = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        prefs[key ?: getKey(thisRef, property), defaultValue.name].let(::enumValueOf)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        prefs[key ?: getKey(thisRef, property)] = value.name
    }
}

/**
 * Creates delegate for property in [SharedPreferences].
 * If [key] not passed simpleName+propertyName of holder class will be used.
 * @param T type of the property
 * @param prefs instance of [SharedPreferences]
 * @param key unique identifier
 * @param defaultValue is used if property not set
 * @return property delegate [ReadWriteProperty]
 */
inline fun <reified T> pref(
    prefs: SharedPreferences,
    key: String? = null,
    defaultValue: T = defaultForType()
) = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        prefs[key ?: getKey(thisRef, property), defaultValue]

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        prefs[key ?: getKey(thisRef, property)] = value
    }
}

/**
 * Set [value] into [SharedPreferences] by [key].
 * @param T type of the property
 * @param key unique identifier
 * @param value - new value
 */
inline operator fun <reified T> SharedPreferences.set(key: String, value: T) {
    edit().apply {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            else -> throw UnsupportedOperationException("Type ${T::class} is not supported yet!")
        }
    }.apply()
}

/**
 * Get saved value from [SharedPreferences] by [key].
 * If property not set returns [defaultValue] (see [defaultForType] for default).
 * @param T type of the property
 * @param key unique identifier
 * @param defaultValue default value
 * @return saved value or [defaultValue].
 */
inline operator fun <reified T> SharedPreferences.get(key: String, defaultValue: T = defaultForType()) =
    when (defaultValue) {
        is String -> getString(key, defaultValue) as T
        is Int -> getInt(key, defaultValue) as T
        is Boolean -> getBoolean(key, defaultValue) as T
        is Float -> getFloat(key, defaultValue) as T
        is Long -> getLong(key, defaultValue) as T
        else -> throw UnsupportedOperationException("Type ${T::class} is not supported yet")
    }

/**
 * Returns default value for typical property types.
 * @param T [String] -> empty string
 * @param T [Int] / [Float] / [Long] -> 0
 * @param T [Boolean] -> false
 */
inline fun <reified T> defaultForType(): T =
    when (T::class) {
        String::class -> "" as T
        Int::class -> 0 as T
        Boolean::class -> false as T
        Float::class -> 0F as T
        Long::class -> 0L as T
        else -> throw IllegalArgumentException("Default value not found for type ${T::class}")
    }

fun getKey(thisRef: Any, property: KProperty<*>) =
    "${thisRef::class.simpleName}.${property.name}"