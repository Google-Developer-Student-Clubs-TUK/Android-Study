package com.example.gdsc_androidstudy.data

import android.content.Context
import android.content.SharedPreferences

class AppPref(context: Context) {
    private val prefFilename = "userPref"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefFilename, 0)

    fun getUserPref(): User? {
        val userId = prefs.getString("uId", null)
        val nickname = prefs.getString("nickname", "")
        val email = prefs.getString("email", "")
        return if (userId != null) {
            User(userId = userId, nickname = nickname ?: "", email = email ?: "", profileImg = null, createdAt = "")
        } else {
            null
        }
    }

    fun setUserPref(user: User) {
        prefs.edit().putString("id", user.userId).apply()
        prefs.edit().putString("email", user.email).apply()
        prefs.edit().putString("nickname", user.nickname).apply()
    }
}
