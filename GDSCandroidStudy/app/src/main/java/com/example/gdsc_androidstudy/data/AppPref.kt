package com.example.gdsc_androidstudy.data

import android.content.Context
import android.content.SharedPreferences

class AppPref(context: Context) {
    private val prefFilename = "userPref"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefFilename, 0)

    fun getUserPref(): Boolean {
        return !(prefs.getString("uId", "").isNullOrBlank())
    }

    fun setUserPref(user: User) {
        prefs.edit().putString("id", user.uId).apply()
        prefs.edit().putString("email", user.email).apply()
        prefs.edit().putString("nickname", user.nickname).apply()
    }
}
