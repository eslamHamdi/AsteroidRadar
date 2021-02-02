package com.example.astroidradar.api

import com.example.astroidradar.data_transfer_opjects.PictureOfDay
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface NasaApiService
{
    @GET("neo/rest/v1/feed")
   suspend fun getNeoFeed(@Query("start_date") Start:String) : JSONObject


   @GET("planetary/apod")
   suspend fun getPictureOfDay() : PictureOfDay
}

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val interceptor = Interceptor { chain ->
    var request: Request = chain.request()

    val url = request.url().newBuilder().addQueryParameter(
            "apiKey",
            "AOrm02qyADnWbfK1OH3M3zKk4i61oc18Jd7u3iZh"
    ).build()

    request = request.newBuilder().url(url)
            .build()

    chain.proceed(request)
}


object Network {

   private val client =
        OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    val NasaService = retrofit.create(NasaApiService::class.java)
}