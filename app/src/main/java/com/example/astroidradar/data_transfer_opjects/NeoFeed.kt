package com.example.astroidradar.data_transfer_opjects

import com.squareup.moshi.Json

data class NeoFeed(

		@Json(name="estimated_diameter")
	val estimatedDiameter: EstimatedDiameter? = null,

		@Json(name="is_potentially_hazardous_asteroid")
	val isPotentiallyHazardousAsteroid: Boolean? = null,

		@Json(name="name")
	val name: String? = null,

		@Json(name="absolute_magnitude_h")
	val absoluteMagnitudeH: Double? = null,

		@Json(name="id")
	val id: String? = null,

		@Json(name="close_approach_data")
	val closeApproachData: List<CloseApproachDataItem?>? = null
)

data class EstimatedDiameter(

	@Json(name="kilometers")
	val kilometers: Kilometers? = null
)

data class RelativeVelocity(

	@Json(name="kilometers_per_second")
	val kilometersPerSecond: String? = null
)

data class CloseApproachDataItem(

		@Json(name="relative_velocity")
	val relativeVelocity: RelativeVelocity? = null,

		@Json(name="close_approach_date")
	val Date: String? = null,

		@Json(name="miss_distance")
	val missDistance: MissDistance? = null
)

data class Kilometers(

	@Json(name="estimated_diameter_max")
	val estimatedDiameterMax: Double? = null
)

data class MissDistance(

	@Json(name="astronomical")
	val astronomical: String? = null
)
