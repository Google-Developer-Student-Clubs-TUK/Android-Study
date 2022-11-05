package com.example.gdsc_androidstudy.data

import android.content.Context
import android.content.SharedPreferences
import android.service.autofill.UserData

class SharedPreference(context: Context){
    private val prefFilename = "userPref"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefFilename, 0)

    fun getUserPref(): Boolean {
        return !(prefs.getString("uId", "").isNullOrBlank())
    }

    fun serUserPref(user: User){
        prefs.edit().putString("id", user.uId).apply()
        prefs.edit().putString("email", user.email).apply()
        prefs.edit().putString("nickname", user.nickname).apply()
    }
}
