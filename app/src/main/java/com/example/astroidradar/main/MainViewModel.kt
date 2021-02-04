package com.example.astroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.astroidradar.database.AsteroidData
import com.example.astroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application)
{

    val Data =  AsteroidData.getInstance(getApplication())

    val Repo :AsteroidRepository = AsteroidRepository(Data)

    init
    {
        viewModelScope.launch{
            getFeed()
        }

    }

    val feedLiveData = Repo.asteroids










    suspend fun getFeed()
    {
        withContext(Dispatchers.IO)
        {

            Repo.RefreshData()

        }


    }
}