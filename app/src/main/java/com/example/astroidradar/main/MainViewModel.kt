package com.example.astroidradar.main

import androidx.lifecycle.*
import com.example.astroidradar.AsteroidRadarApplication
import com.example.astroidradar.data_transfer_opjects.PictureOfDay
import com.example.astroidradar.database.AsteroidData
import com.example.astroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: AsteroidRadarApplication) : AndroidViewModel(application)
{

    val Data =  AsteroidData.getInstance(application)

    val Repo :AsteroidRepository = AsteroidRepository(Data)

     private val _pic : MutableLiveData<PictureOfDay?>?=null

    val pic:LiveData<PictureOfDay?>?
    get() = _pic

   // var pic :PictureOfDay?  =null

    val feedLiveData = Repo.asteroids



    init
    {
        viewModelScope.launch{
            getFeed()


        }

    }



    suspend fun getFeed()
    {
        withContext(Dispatchers.IO)
        {

            Repo.RefreshData()
           val img = Repo.imageOfDay()
            withContext(Dispatchers.Main)
            {
                if (img!=null)
                {
                    _pic?.value=img
                }

            }
        }


    }

    class Factory(val app: AsteroidRadarApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}