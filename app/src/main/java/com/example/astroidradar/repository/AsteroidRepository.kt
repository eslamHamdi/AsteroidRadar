package com.example.astroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.astroidradar.api.Network
import com.example.astroidradar.api.parseAsteroidsJsonResult
import com.example.astroidradar.data_transfer_opjects.PictureOfDay
import com.example.astroidradar.database.AsteroidData
import com.example.astroidradar.database.toDomainModel
import com.example.astroidradar.domain.Asteroid
import com.example.astroidradar.domain.toDataBase
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(val Data: AsteroidData)
{
    private val _asteroids = Transformations.map(Data.dao.getAllList()) {

        it.toDomainModel()
    }

    val asteroids:LiveData<List<Asteroid>>
    get() = _asteroids


    suspend fun RefreshData()
    {

        try
        {
            val Response =  Network.NasaService.getNeoFeed(getCurrentDate())
            val jsonObject = JSONObject(Response.toString())
            val asteroids = parseAsteroidsJsonResult(jsonObject)
            Data.dao.saveAsteroidsList(*asteroids.toDataBase())
        }
        catch (e: Exception)
        {
            Log.e(null, "RefreshData: ${e.localizedMessage} ")
        }


    }

    private fun  getCurrentDate():String
    {
        val c: Date = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("yyyy-MM-DD", Locale.getDefault())
        return df.format(c)
    }

    fun getEndDate():String
    {
        val cal = Calendar.getInstance()
        val s = SimpleDateFormat("yyyy-MM-DD",Locale.getDefault())
        cal.add(Calendar.DAY_OF_YEAR, 1)
        Log.e(null, "getEndDate:${s.format(Date(cal.timeInMillis))} " )
        return s.format(Date(cal.timeInMillis))
    }

    suspend fun imageOfDay():PictureOfDay?
    {
        val image = Network.NasaService.getPictureOfDay()
        if(image.mediaType=="image")
        {
            Log.e(null, "imageOfDay: gotimg")
            return image
        }
        else
        {
            return null


        }


    }

}