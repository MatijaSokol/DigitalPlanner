package hr.ferit.matijasokol.digitalplanner.persistence

import android.content.Context
import hr.ferit.matijasokol.digitalplanner.app.App

object PreferenceManager {

    private const val PREFS_NAME = "APP_PREFS"
    private val sharedPrefs = App.instance.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveValue(key: String, value: Boolean) {
        sharedPrefs.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun getValue(key: String, defaultValue: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, defaultValue)
    }
}