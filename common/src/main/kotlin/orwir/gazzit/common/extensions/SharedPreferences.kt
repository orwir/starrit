package orwir.gazzit.common.extensions

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface Shareable {
    val prefs: SharedPreferences
    val moshi: Moshi
}

class KoinedShareable : KoinComponent, Shareable {
    override val prefs: SharedPreferences by inject()
    override val moshi: Moshi by inject()
}

inline fun <reified T> Shareable.objPref(defaultValue: T?, key: String? = null) =
    objPref(prefs, moshi, key, defaultValue)

inline fun <reified T : Enum<T>> Shareable.enumPref(
    defaultValue: T = enumValues<T>()[0],
    key: String? = T::class.simpleName
) = enumPref(prefs, key, defaultValue)

inline fun <reified T> Shareable.pref(defaultValue: T = defaultForType(), key: String? = null) =
    pref(prefs, key, defaultValue)

// -------------------------------------------------------------------------------------------------

inline fun <reified T : Enum<T>> enumPref(
    prefs: SharedPreferences,
    key: String? = null,
    defaultValue: T = enumValues<T>()[0]
) = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        enumValueOf(prefs[key ?: getKey(thisRef, property), defaultValue.name])

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        prefs[key ?: getKey(thisRef, property)] = value.name
    }
}

inline fun <reified T> objPref(
    prefs: SharedPreferences,
    moshi: Moshi,
    key: String? = null,
    defaultValue: T?
) = object : ReadWriteProperty<Any, T?> {
    private val adapter = moshi.adapter<T>(T::class.java)

    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        val raw = prefs[key ?: getKey(thisRef, property), ""]
        return if (raw.isNotBlank()) {
            adapter.fromJson(raw) ?: defaultValue
        } else {
            defaultValue
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        prefs[key ?: getKey(thisRef, property)] = if (value != null) adapter.toJson(value) else ""
    }
}

/**
 * Creates delegate for property from [prefs] with the key presented
 * as className+propertyName if not passed.
 * @param T type of the property
 * @param defaultValue is used if property not set
 * @return wrapper for property accessors
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
 * Set property [value] to shared preferences by [key].
 * @param T type of the property
 */
inline operator fun <reified T> SharedPreferences.set(key: String, value: T) {
    edit().apply {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            else -> throw UnsupportedOperationException("Type ${T::class} is not supported yet")
        }
    }.apply()
}

/**
 * Get property value by [key]. If property not set return [defaultValue] ("", true, 0 if not given).
 * @param T type of the property
 * @return saved value or [defaultValue].
 */
inline operator fun <reified T> SharedPreferences.get(
    key: String,
    defaultValue: T = defaultForType()
) =
    when (defaultValue) {
        is String -> getString(key, defaultValue) as T
        is Int -> getInt(key, defaultValue) as T
        is Boolean -> getBoolean(key, defaultValue) as T
        is Float -> getFloat(key, defaultValue) as T
        is Long -> getLong(key, defaultValue) as T
        else -> throw UnsupportedOperationException("Type ${T::class} is not supported yet")
    }

/**
 * Default value for typical property types.
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