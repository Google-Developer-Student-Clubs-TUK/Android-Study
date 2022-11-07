package com.example.toy_proejct

import android.util.Log

object LogHelper {
    fun print(msg: Any?) {
        Log.d("LogHelper", msg.toString())
    }
}