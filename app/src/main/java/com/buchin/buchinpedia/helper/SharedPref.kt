package com.buchin.buchinpedia.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPref(activity: Activity) {
    val mypref = "MAIN_PRF"
    val sp : SharedPreferences

    val login = "Login"
    val nama = "nama"
    val phone = "phone"
    val email = "email"


    init {
    sp = activity.getSharedPreferences(mypref,Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status:Boolean){
        sp.edit().putBoolean(login,status).apply()
    }

    fun getStatusLogin():Boolean{
        return sp.getBoolean(login,false)
    }

    fun setString(key:String, value: String){
        sp.edit().putString(key,value).apply()

    }
    fun getString(key:String):String{
    return sp.getString(key,"")!!
    }
}