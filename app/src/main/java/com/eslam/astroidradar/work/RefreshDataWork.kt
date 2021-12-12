package com.eslam.astroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.eslam.astroidradar.database.AsteroidData
import com.eslam.astroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWork(appContext: Context, params: WorkerParameters):
CoroutineWorker(appContext, params)
{
    companion object{
        const val Name = "RefreshWorker"
    }

    override suspend fun doWork(): Result
    {
        val DataBase = AsteroidData.getInstance(applicationContext)
        val Repo = AsteroidRepository(DataBase)
        return try
        {
            Repo.RefreshData()
            Result.success()
        }catch (e:HttpException)
        {
            Result.retry()
        }


    }




}