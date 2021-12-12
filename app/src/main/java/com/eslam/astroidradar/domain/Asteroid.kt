package com.eslam.astroidradar.domain

import android.os.Parcelable
import com.eslam.astroidradar.database.Entities
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable



fun ArrayList<Asteroid>.toDataBase():Array<Entities>
{
    return map {
        Entities(id=it.id,codename=it.codename,closeApproachDate=it.closeApproachDate,absoluteMagnitude=it.absoluteMagnitude,
                estimatedDiameter=it.estimatedDiameter,
                relativeVelocity=it.relativeVelocity,distanceFromEarth=it.distanceFromEarth,isPotentiallyHazardous=it.isPotentiallyHazardous)
    }.toTypedArray()
}