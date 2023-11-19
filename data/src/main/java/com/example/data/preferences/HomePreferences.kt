package com.example.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class HomePreferences @Inject constructor(sharedPreferences: SharedPreferences) :
    PreferencesManager(sharedPreferences) {

    companion object {
        const val LAST_QUERy = "lastQuery"
    }

    fun saveLastQuery(lastQuery: String) {
        savePreferenceKey(LAST_QUERy, lastQuery)
    }

    fun getLastQuery(): String? {
        val lastQuery = preferences.getString(LAST_QUERy, null)
        lastQuery?.let {
            return lastQuery
        }
        return null
    }

}