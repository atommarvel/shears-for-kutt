package com.radiantmood.kuttit.util

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
inline fun <reified T> getPrefDelegate(
    prefs: SharedPreferences,
    key: String,
    default: T?
): PrefDelegate<T> {
    val pref: Pref<T> = when (T::class) {
        String::class -> StringPref(prefs, key, default as? String) as Pref<T>
        Long::class -> LongPref(prefs, key, default as? Long) as Pref<T>
        Boolean::class -> BooleanPref(prefs, key, default as? Boolean) as Pref<T>
        else -> throw IllegalArgumentException("unable to handle type ${T::class.java.canonicalName}")
    }
    return PrefDelegate(pref)
}

class PrefDelegate<T>(private val pref: Pref<T>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = pref.get()
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = pref.set(value)
}

interface Pref<T> {
    fun get(): T?
    fun set(value: T)
}

class BooleanPref(
    private val prefs: SharedPreferences,
    private val key: String,
    private val default: Boolean?
) :
    Pref<Boolean> {
    override fun get(): Boolean? =
        if (prefs.contains(key)) prefs.getBoolean(key, false) else default

    override fun set(value: Boolean) = prefs.edit { putBoolean(key, value) }
}

class LongPref(
    private val prefs: SharedPreferences,
    private val key: String,
    private val default: Long?
) : Pref<Long> {
    override fun get(): Long? = if (prefs.contains(key)) prefs.getLong(key, 0) else default
    override fun set(value: Long) = prefs.edit { putLong(key, value) }
}

class StringPref(
    private val prefs: SharedPreferences,
    private val key: String,
    private val default: String?
) :
    Pref<String> {
    override fun get(): String? = if (prefs.contains(key)) prefs.getString(key, "") else default
    override fun set(value: String) = prefs.edit { putString(key, value) }
}