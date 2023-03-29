package com.rizal.digitalsawitpro.utils

import android.content.SharedPreferences

class AppPreferences(sharedPreferences: SharedPreferences) {

    private var pref: SharedPreferences = sharedPreferences
    private var editor: SharedPreferences.Editor = pref.edit()

    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
        const val IS_LOGIN_ANONYMOUSLY = "IS_VALID_TOKEN"
    }

    var googleApi: String
        get() {
            return pref.getString(KEY_TOKEN, "AIzaSyCfmboPQ-HClsdXgLH9RT8s2pM5Tjz0m0M").orEmpty()
        }
        set(value) {
            editor.putString(KEY_TOKEN, value)
            editor.apply()
        }

    var isLoginAnonymously: Boolean
        get() {
            return pref.getBoolean(IS_LOGIN_ANONYMOUSLY, false)
        }
        set(value) {
            editor.putBoolean(IS_LOGIN_ANONYMOUSLY, value)
            editor.apply()
        }
}