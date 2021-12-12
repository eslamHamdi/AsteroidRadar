package com.eslam.astroidradar

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.M)
fun isNetworkConnected(context: Context): Boolean
{
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities)
                ?: return false
        result = when
        {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    } else
    {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type)
                {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
    return result
}