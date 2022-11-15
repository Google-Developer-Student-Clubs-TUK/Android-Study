package com.example.toy_proejct.utils

import java.text.NumberFormat
import java.util.*

object UnitHelper {
    fun getStringFromMoneyInteger(money: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale.KOREA)
        return format.format(money).substring(1) + "원"
    }
}
