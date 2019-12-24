package orwir.starrit.core.extension

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.starrit.core.ObjectAdapter
import orwir.starrit.core.defaultForType

interface Shareable {
    val prefs: SharedPreferences
    val moshi: Moshi
}

class KoinedShareable : KoinComponent, Shareable {
    override val prefs: SharedPreferences by inject()
    override val moshi: Moshi by inject()
}

inline fun <reified T> adapter(moshi: Moshi) = object :
    ObjectAdapter<T> {
    private val adapter = moshi.adapter(T::class.java)
    override fun to(raw: String): T = adapter.fromJson(raw) ?: throw IllegalStateException()
    override fun from(obj: T): String = adapter.toJson(obj)
}

inline fun <reified T> Shareable.objPref(defaultValue: T? = null, key: String? = null) =
    orwir.starrit.core.objPref(
        prefs,
        adapter(moshi),
        key,
        defaultValue
    )

inline fun <reified T : Enum<T>> Shareable.enumPref(
    defaultValue: T = enumValues<T>()[0],
    key: String? = null
) = orwir.starrit.core.enumPref(prefs, key, defaultValue)

inline fun <reified T> Shareable.pref(defaultValue: T = defaultForType(), key: String? = null) =
    orwir.starrit.core.pref(prefs, key, defaultValue)