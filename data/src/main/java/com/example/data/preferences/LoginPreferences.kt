package com.example.data.preferences

import android.content.SharedPreferences
import com.example.domain.entities.local.MyUser
import com.example.domain.extensions.fromJson
import com.example.domain.extensions.toJson
import javax.inject.Inject

class LoginPreferences @Inject constructor(sharedPreferences: SharedPreferences) :
    PreferencesManager(sharedPreferences) {

    companion object {
        const val USER = "user"
    }

    fun saveUser(user: MyUser) {
        savePreferenceKey(USER, user.toJson())
    }

    fun getUser(): MyUser? {
        val userJson = preferences.getString(USER, null)
        userJson?.let {
            return userJson.fromJson<MyUser>()
        }
        return null
    }

    fun destroyUserSession() {
        removePreferenceKey(USER)
    }


}