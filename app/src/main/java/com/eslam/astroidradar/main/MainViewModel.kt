package com.eslam.astroidradar.main

import android.util.Log
import androidx.lifecycle.*
import com.eslam.astroidradar.AsteroidRadarApplication
import com.eslam.astroidradar.Constants
import com.eslam.astroidradar.database.AsteroidData
import com.eslam.astroidradar.domain.Asteroid
import com.eslam.astroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: com.eslam.astroidradar.AsteroidRadarApplication) : AndroidViewModel(application)
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

            try {
                getFeed()
            }catch (e:Exception)
            {
               // Log.e(null, ":fetching failed ", )
                viewData?.value = null
            }


        }

    }



    suspend fun getFeed()
    {

            viewModelScope.launch {
                Repo.RefreshData()
                Repo.getSavedList().collect {
                    viewData?.value = it

                }
            }

        viewModelScope.launch {
            Repo.imageOfDay()
        }







    }
    fun deleteAll()
    {

        viewModelScope.launch {

            try {
                Repo.Data.dao.deleteAll()
                viewData?.value = null
                getFeed()
            }catch (e:Exception)
            {

                Log.e(null, "deleteAll: ${e.message} ", )
                //viewData?.value = null
            }

        }
    }



    class Factory(val app: AsteroidRadarApplication) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}