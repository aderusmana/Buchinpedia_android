package com.buchin.buchinpedia.model

class ModelAlamat {

    val id = 0
    val nama = ""

    val status = Status()
    val results = ArrayList<Results>()

    class Results{
        val province_id = "0"
        val province = ""

        //
        val city_id = ""
        val type = ""
        val city_name = ""
        val postal_code = ""
    }

    class Status{
        val code = 0
        val description = ""
    }
}