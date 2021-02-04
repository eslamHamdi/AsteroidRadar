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
        @Volatile
        private var database:AsteroidData? = null

        fun getInstance(context:Context):AsteroidData
        {
            if (database != null)
            {
                database = Room.databaseBuilder(context,AsteroidData::class.java,"Data").build()
            }

            return database!!


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