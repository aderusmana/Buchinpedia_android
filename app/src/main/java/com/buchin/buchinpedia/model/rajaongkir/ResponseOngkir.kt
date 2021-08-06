package com.buchin.buchinpedia.model.rajaongkir

import com.buchin.buchinpedia.model.ModelAlamat

class ResponseOngkir {
    val rajaongkir = RajaOngkir()

    class RajaOngkir{
        val results = ArrayList<Results>()
    }
}