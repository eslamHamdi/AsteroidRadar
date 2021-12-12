package com.eslam.astroidradar.data_transfer_opjects

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureOfDay(

	@Json(name="date")
	val date: String? = null,

	@Json(name="copyright")
	val copyright: String? = null,

	@Json(name="media_type")
	val mediaType: String? = null,

	@Json(name="hdurl")
	val hdurl: String? = null,

	@Json(name="service_version")
	val serviceVersion: String? = null,

	@Json(name="explanation")
	val explanation: String? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="url")
	val url: String? = null
) : Parcelable


