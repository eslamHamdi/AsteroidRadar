package com.eslam.astroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eslam.astroidradar.domain.Asteroid

@Entity
data class Entities(@PrimaryKey val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean)


fun List<Entities>.toDomainModel():List<Asteroid>
{
    return map {
        Asteroid(id = it.id,codename = it.codename,closeApproachDate = it.closeApproachDate,
        absoluteMagnitude = it.absoluteMagnitude,estimatedDiameter = it.estimatedDiameter,
        relativeVelocity = it.relativeVelocity,distanceFromEarth = it.distanceFromEarth,
        isPotentiallyHazardous = it.isPotentiallyHazardous)
    }
}