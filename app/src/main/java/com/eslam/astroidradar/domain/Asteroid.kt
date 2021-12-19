package com.eslam.astroidradar.domain

import android.os.Parcelable
import com.eslam.astroidradar.database.Entities
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(val id: Long? = null, val codename: String? = null, val closeApproachDate: String? = null,
                    val absoluteMagnitude: Double? = null, val estimatedDiameter: Double? = null,
                    val relativeVelocity: Double? = null, val distanceFromEarth: Double? = null,
                    val isPotentiallyHazardous: Boolean? = null) : Parcelable



fun ArrayList<Asteroid>.toDataBase():Array<Entities>
{
    return map {
        Entities(id=it.id!!,codename=it.codename!!,closeApproachDate=it.closeApproachDate!!,absoluteMagnitude=it.absoluteMagnitude!!,
                estimatedDiameter=it.estimatedDiameter!!,
                relativeVelocity=it.relativeVelocity!!,distanceFromEarth=it.distanceFromEarth!!,isPotentiallyHazardous=it.isPotentiallyHazardous!!)
    }.toTypedArray()
}