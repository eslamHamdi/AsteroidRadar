package com.eslam.astroidradar

import android.app.Application
import android.os.Build
import android.os.Build.VERSION_CODES.M
import androidx.work.*
import com.eslam.astroidradar.work.RefreshDataWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication: Application()
{
    override fun onCreate()
    {
        super.onCreate()
           WorkManager.getInstance(applicationContext)
                   .enqueueUniquePeriodicWork(RefreshDataWork.Name,ExistingPeriodicWorkPolicy.KEEP,request)


    }

    private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .apply {

                if (Build.VERSION.SDK_INT >= M)
                {
                    setRequiresDeviceIdle(true)
                }

            }
            .setRequiresCharging(true)
            .build()

private val request = PeriodicWorkRequestBuilder<RefreshDataWork>(1,TimeUnit.DAYS)
        .setConstraints(constraints)
        .setBackoffCriteria(BackoffPolicy.LINEAR,1,TimeUnit.HOURS)
        .build()





}