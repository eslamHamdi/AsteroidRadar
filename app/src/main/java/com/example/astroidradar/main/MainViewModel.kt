package com.example.astroidradar.main

import androidx.lifecycle.*
import com.example.astroidradar.AsteroidRadarApplication
import com.example.astroidradar.Constants
import com.example.astroidradar.database.AsteroidData
import com.example.astroidradar.domain.Asteroid
import com.example.astroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: AsteroidRadarApplication) : AndroidViewModel(application)
{

    private val Data =  AsteroidData.getInstance(application)

   private val Repo :AsteroidRepository = AsteroidRepository(Data)



    var viewData:MutableLiveData<List<Asteroid>>? = Repo.asteroids as MutableLiveData<List<Asteroid>>
    val image = Repo.pic

    fun getData(type:String)
    {

            viewModelScope.launch {
                when (type) {
                    Constants.All -> {
                        viewData?.postValue(Repo.asteroids.value)
                    }
                    Constants.DAY -> {

                        viewData?.postValue(Repo.dailyFeed())
                    }

                    Constants.WEEK -> {
                        viewData?.postValue(Repo.weeklyFeed())
                    }

                }

            }


    }





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
            //Repo.Data.dao.deleteAll()
            Repo.RefreshData()
            Repo.imageOfDay()

        }


    }
    fun deleteAll()
    {
        viewModelScope.launch {
            Repo.Data.dao.deleteAll()
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