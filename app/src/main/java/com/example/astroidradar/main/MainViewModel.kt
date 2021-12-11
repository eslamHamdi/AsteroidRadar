package com.example.astroidradar.main

import androidx.lifecycle.*
import com.example.astroidradar.AsteroidRadarApplication
import com.example.astroidradar.Constants
import com.example.astroidradar.database.AsteroidData
import com.example.astroidradar.domain.Asteroid
import com.example.astroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: AsteroidRadarApplication) : AndroidViewModel(application)
{

    private val Data =  AsteroidData.getInstance(application)

   private val Repo :AsteroidRepository = AsteroidRepository(Data)



    var viewData:MutableLiveData<List<Asteroid>?>? = MutableLiveData<List<Asteroid>?>()
    val image = Repo.pic

    fun getData(type:String)
    {

            viewModelScope.launch {
                when (type) {
                   Constants.All -> {
                     Repo.getSavedList().collect {
                         viewData?.value = it
                     }
                   }
                    Constants.DAY -> {

                        viewData?.value =(Repo.dailyFeed())
                    }

                    Constants.WEEK -> {
                        viewData?.value =(Repo.weeklyFeed())
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

            //Repo.Data.dao.deleteAll()
            Repo.RefreshData()
            Repo.imageOfDay()
        Repo.getSavedList().collect {
            viewData?.value = it
        }


    }
    fun deleteAll()
    {
        viewModelScope.launch {
            Repo.Data.dao.deleteAll()
            getFeed()
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