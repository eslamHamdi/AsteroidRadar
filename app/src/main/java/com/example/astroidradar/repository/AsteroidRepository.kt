package com.example.astroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.astroidradar.api.Network
import com.example.astroidradar.api.parseAsteroidsJsonResult
import com.example.astroidradar.data_transfer_opjects.PictureOfDay
import com.example.astroidradar.database.AsteroidData
import com.example.astroidradar.database.toDomainModel
import com.example.astroidradar.domain.Asteroid
import com.example.astroidradar.domain.toDataBase
import com.example.astroidradar.getCurrentDate
import com.example.astroidradar.getEndDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(val Data: AsteroidData)
{




    private val _asteroids = Transformations.map(Data.dao.getAllList()) {

        it.toDomainModel()
    }


    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pic: MutableLiveData<PictureOfDay?> = MutableLiveData(null)

    val pic: LiveData<PictureOfDay?>
        get() = _pic




    suspend fun RefreshData()
    {


        try
        {

            val Response = Network.NasaService.getNeoFeed(getCurrentDate())
            val jsonObject = JSONObject(Response.string())
            val asteroids = parseAsteroidsJsonResult(jsonObject)
            Data.dao.saveAsteroidsList(*asteroids.toDataBase())
        } catch (e: Exception)
        {
            Log.e(null, "RefreshData: ${e.localizedMessage} ")
        }


    }


    suspend fun imageOfDay()
    {
        val image = Network.NasaService.getPictureOfDay()
        if (image.mediaType == "image")
        {
            Log.e(null, "imageOfDay: gotimg")
            withContext(Dispatchers.Main)
            {
                _pic.value = image
            }
        } else
        {
            withContext(Dispatchers.Main)
            {
                _pic.value = null
            }
        }


    }

    suspend fun dailyFeed():List<Asteroid>
    {


        return Data.dao.getTodayFeed(getCurrentDate()).toDomainModel()


        }


        suspend fun weeklyFeed():List<Asteroid>
        {


            return Data.dao.getweeklyFeed(getCurrentDate(),getEndDate()).toDomainModel()
        }


    }


