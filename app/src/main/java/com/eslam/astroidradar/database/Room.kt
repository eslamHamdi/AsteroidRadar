package com.eslam.astroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Database(entities = [Entities::class], version = 3, exportSchema = false)
abstract class AsteroidData: RoomDatabase()
{
    abstract val dao:AsteroidDao

    companion object
    {


        private lateinit var INSTANCE:AsteroidData


        fun getInstance(context: Context):AsteroidData
        {
            kotlin.synchronized(AsteroidData::class.java)
            {
                if (!::INSTANCE.isInitialized)
                {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AsteroidData::class.java, "Data")
                        .fallbackToDestructiveMigration()
                        .build()

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

    @Query("SELECT * FROM Entities ORDER BY closeApproachDate ASC")
    fun getAllList():Flow<List<Entities>>

    @Query("DELETE FROM Entities")
    suspend fun deleteAll():Int

    @Query("SELECT * FROM ENTITIES WHERE closeApproachDate = :date")
    suspend fun getTodayFeed(date: String):List<Entities>

    @Query("SELECT * FROM ENTITIES   WHERE closeApproachDate BETWEEN :start AND :end ORDER BY closeApproachDate ASC  ")
    suspend fun getweeklyFeed(start: String, end: String):List<Entities>

    //var query = SimpleSQLiteQuery("SELECT * FROM Foo WHERE [CONDITION 1] ORDER BY closeApproachDate ASC")
}