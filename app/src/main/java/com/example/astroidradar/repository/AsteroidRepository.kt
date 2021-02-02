package com.example.astroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.astroidradar.api.Network
import com.example.astroidradar.api.parseAsteroidsJsonResult
import com.example.astroidradar.database.AsteroidData
import com.example.astroidradar.database.toDomainModel
import com.example.astroidradar.domain.Asteroid
import com.example.astroidradar.domain.toDataBase
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

        val Response =  Network.NasaService.getNeoFeed(getCurrentDate())
        val asteroids = parseAsteroidsJsonResult(Response)
        Data.dao.saveAsteroidsList(*asteroids.toDataBase())

    }

    private fun  getCurrentDate():String
    {
        val c: Date = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("YYYY-MM-DD", Locale.getDefault())
        return df.format(c)
    }
}