package com.example.astroidradar

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

 fun  getCurrentDate():String
{
    val c: Date = Calendar.getInstance().time
    println("Current time => $c")

    val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return df.format(c)
}

fun getEndDate():String
{
    val cal = Calendar.getInstance()
    val s = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    cal.add(Calendar.DAY_OF_YEAR, 7)
    Log.e(null, "getEndDate: ${s.format(Date(cal.timeInMillis))} ")
    return s.format(Date(cal.timeInMillis))
}