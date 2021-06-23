package com.buchin.buchinpedia.app

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    private const val BASE_URL = "http://192.168.0.125/buchinpedia/public/api/"
    private val client : Retrofit
    get() {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val inteceptor = HttpLoggingInterceptor()
        inteceptor.level = HttpLoggingInterceptor.Level.BODY
        val client : OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(inteceptor)
            .connectTimeout(40,TimeUnit.SECONDS)
            .readTimeout(40,TimeUnit.SECONDS)
            .writeTimeout(40,TimeUnit.SECONDS)
            .build()


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    val instanceRetrofit:ApiService
    get() = client.create(ApiService::class.java)
}