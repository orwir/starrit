package orwir.gazzit.common.extensions

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import org.koin.core.KoinComponent
import org.koin.core.inject
import orwir.gazzit.common.*

interface Shareable {
    val prefs: SharedPreferences
    val moshi: Moshi
}

class KoinedShareable : KoinComponent, Shareable {
    override val prefs: SharedPreferences by inject()
    override val moshi: Moshi by inject()
}

inline fun <reified T> adapter(moshi: Moshi) = object : ObjectAdapter<T> {
    private val adapter = moshi.adapter(T::class.java)
    override fun to(raw: String): T = adapter.fromJson(raw) ?: throw IllegalStateException()
    override fun from(obj: T): String = adapter.toJson(obj)
}

inline fun <reified T> Shareable.pref(defaultValue: T = defaultForType(), key: String? = null) =
    pref(prefs, key, defaultValue)

inline fun <reified T> Shareable.objPref(defaultValue: T? = null, key: String? = null) =
    objPref(prefs, adapter(moshi), key, defaultValue)

inline fun <reified T : Enum<T>> Shareable.enumPref(
    defaultValue: T = enumValues<T>()[0],
    key: String? = null
) = enumPref(prefs, key, defaultValue)