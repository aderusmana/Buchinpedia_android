package com.buchin.buchinpedia.app


import com.buchin.buchinpedia.model.ResponseModel
import com.buchin.buchinpedia.model.rajaongkir.ResponseOngkir
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

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

    @GET("province")
    fun getProvinsi(
            @Header("key") key: String
    ):Call<ResponseModel>

    @GET("city")
    fun getKota(
            @Header("key") key: String,
            @Query("province") id :String
    ):Call<ResponseModel>

    @GET("kecamatan")
    fun getKecamatan(
            @Query("id_kota") id:Int
    ):Call<ResponseModel>

    @GET("kelurahan")
    fun getKelurahan(
            @Query("id_kecamatan") id:Int
    ):Call<ResponseModel>

    // Cost

    @FormUrlEncoded
    @POST("cost")
    fun ongkir(
            @Header("key") key: String,
            @Field("origin")origin:String,
            @Field("destination")destination:String,
            @Field("weight")weight:Int,
            @Field("courier")courier:String

    ):Call<ResponseOngkir>
}