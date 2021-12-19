package com.eslam.astroidradar.api
import com.eslam.astroidradar.Constants.BASE_URL
import com.eslam.astroidradar.data_transfer_opjects.PictureOfDay
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface NasaApiService
{
    @GET("neo/rest/v1/feed")
   suspend fun getNeoFeed(@Query("start_date")Start:String,@Query("end_date") end:String) : ResponseBody?


   @GET("planetary/apod")
   suspend fun getPictureOfDay() : PictureOfDay
}

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val scalars = ScalarsConverterFactory.create()

private val interceptor = Interceptor { chain ->
    var request: Request = chain.request()

    val url = request.url().newBuilder().addQueryParameter(
            "api_key",
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
            .callTimeout(30L,TimeUnit.SECONDS)
            .readTimeout(30L,TimeUnit.SECONDS)
            .connectTimeout(30L,TimeUnit.SECONDS)
                .build()
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(scalars)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    val NasaService = retrofit.create(NasaApiService::class.java)
}