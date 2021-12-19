package com.eslam.astroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eslam.astroidradar.api.Network
import com.eslam.astroidradar.api.parseAsteroidsJsonResult
import com.eslam.astroidradar.data_transfer_opjects.PictureOfDay
import com.eslam.astroidradar.database.AsteroidData
import com.eslam.astroidradar.database.toDomainModel
import com.eslam.astroidradar.domain.Asteroid
import com.eslam.astroidradar.domain.toDataBase
import com.eslam.astroidradar.getCurrentDate
import com.eslam.astroidradar.getEndDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(val Data: AsteroidData)
{






    private val _pic: MutableLiveData<PictureOfDay?> = MutableLiveData()

    val pic: LiveData<PictureOfDay?>
        get() = _pic




    suspend fun RefreshData()
    {


        try
        {

            withContext(Dispatchers.IO)
            {
                val Response = Network.NasaService.getNeoFeed(getCurrentDate(), getEndDate())

                if (Response != null) {
                    Log.e(null, "RefreshData: entered ", )
                    val jsonObject = JSONObject(Response?.string())
                    val asteroids = parseAsteroidsJsonResult(jsonObject)
                    asteroids?.let {
                        Data.dao.saveAsteroidsList(*it.toDataBase())
                    }

                }


            }

        } catch (e: Exception)
        {
           Log.e(null, "RefreshData: ${e.localizedMessage} ")

        }


    }



    fun getSavedList(): Flow<List<Asteroid>>
   {
       return Data.dao.getAllList().map {
           it.toDomainModel()
       }.catch {
           Log.e(null, "getSavedList: Error", )
           flowOf<List<Asteroid>>(listOf())

       }
   }




    suspend fun imageOfDay()
    {

        withContext(Dispatchers.IO)
        {
            val image = async {Network.NasaService.getPictureOfDay()

            }.await()

            Log.e("image", "imageOfDay: $image ", )
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
                    _pic.value = PictureOfDay()
                }
            }

        }




    }

    suspend fun dailyFeed():List<Asteroid>
    {
        return try {
            Data.dao.getTodayFeed(getCurrentDate()).toDomainModel()
        }catch (e:Exception) {
            listOf()

        }



        }


        suspend fun weeklyFeed():List<Asteroid>
        {
            return try {
                Data.dao.getweeklyFeed(getCurrentDate(),getEndDate()).toDomainModel()
            }catch (e:Exception) {
                listOf()
            }

        }


    }


