package kr.ac.tukorea.weather.utils

import android.text.format.DateFormat
import android.util.Log
import kr.ac.tukorea.weather.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

val imageMap: MutableMap<String, Int> = mapOf<String, Int>(
    "01d" to R.drawable.i01d,
    "01n" to R.drawable.i01n,
    "02d" to R.drawable.i02d,
    "02n" to R.drawable.i02n,
    "03d" to R.drawable.i03d,
    "03n" to R.drawable.i03n,
    "04d" to R.drawable.i04d,
    "04n" to R.drawable.i04n,
    "09d" to R.drawable.i09d,
    "09n" to R.drawable.i09d,
    "10d" to R.drawable.i09d,
    "10n" to R.drawable.i09d,
    "11d" to R.drawable.i11d,
    "11n" to R.drawable.i11d,
    "13d" to R.drawable.i13n,
    "13n" to R.drawable.i13n,
    "50d" to R.drawable.i50n,
    "50n" to R.drawable.i50n
) as MutableMap<String, Int>

fun getWeatherImage(key: String): Int {
    return imageMap[key]!!
}

fun convertDate(dt: String): String? {

    //String date="2017-05-23T06:25:50";
    var dt: String? = dt
    try {
        var spf =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //date format in which your current string is
        var newDate: Date? = null
        newDate = spf.parse(dt)
        spf = SimpleDateFormat("HH:mm") //date format in which you want to convert
        dt = spf.format(newDate)
        println(dt)
        Log.e("FRM_DT", dt)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return dt
}

fun getmDate(time1: Long): String? {
    val time = Date(time1 * 1000)
    val date: String = DateFormat.format("EEEE, dd MMM", time).toString()
    return date + ""
}

fun dateCompare(dt: String, convertFromPattern: String, convertToPattern: String): String? {

    //String date="2017-05-23T06:25:50";
    var dt: String? = dt
    try {
        var spf =
            SimpleDateFormat(convertFromPattern) //date format in which your current string is
        var newDate: Date? = null
        newDate = spf.parse(dt)
        spf = SimpleDateFormat(convertToPattern) //date format in which you want to convert
        dt = spf.format(newDate)
        println(dt)
        Log.e("FRM_DT", dt)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return dt
}