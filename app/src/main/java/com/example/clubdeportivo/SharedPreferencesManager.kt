package com.example.clubdeportivo

import android.content.Context

object SharedPreferencesManager {
    private const val PREF_NAME = "ClubDeportivoPrefs"

    fun saveUserData(username: String, userType: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("USERNAME_KEY", username)
            putString("USER_TYPE_KEY", userType) // "cliente" o "administrador"
            apply()
        }
    }

    fun getUserData(context: Context): Pair<String, String> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME_KEY", "") ?: ""
        val userType = sharedPreferences.getString("USER_TYPE_KEY", "") ?: ""
        return Pair(username, userType)
    }
}
