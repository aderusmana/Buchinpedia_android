package com.buchin.buchinpedia.app


import com.buchin.buchinpedia.model.ResponseModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded

    @POST("register")
    fun register(
        @Field("name")name:String,
        @Field("email")email:String,
        @Field("phone")phone:String,
        @Field("password")password:String

    ):Call<ResponseModel>


    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email")email:String,
        @Field("password")password:String


    ):Call<ResponseModel>


    @GET("product")
    fun getProduct():Call<ResponseModel>
}