package com.example.astroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Database(entities = [Entities::class] ,version = 1,exportSchema = false)
abstract class AsteroidData: RoomDatabase()
{
    abstract val dao:AsteroidDao

    companion object
    {

        private lateinit var INSTANCE:AsteroidData


        fun getInstance(context:Context):AsteroidData
        {
            kotlin.synchronized(AsteroidData::class.java)
            {
                if (!::INSTANCE.isInitialized)
                {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,AsteroidData::class.java,"Data").build()

                }

            }

            return INSTANCE


        }


    }
}

@Dao
interface AsteroidDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAsteroidsList(vararg asteroids: Entities)

    @Query("SELECT * FROM Entities ORDER BY id ASC")
    fun getAllList():LiveData<List<Entities>>
}