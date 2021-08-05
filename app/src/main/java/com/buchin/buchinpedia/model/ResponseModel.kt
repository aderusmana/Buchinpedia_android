package com.buchin.buchinpedia.model

class ResponseModel {

    var success = 0
    lateinit var message:String
    var user = User()
    var products:ArrayList<Produk> = ArrayList()

    var provinsi:ArrayList<ModelAlamat> = ArrayList()
    var kota_kabupaten:ArrayList<ModelAlamat> = ArrayList()
    var kecamatan:ArrayList<ModelAlamat> = ArrayList()
    var kelurahan:ArrayList<ModelAlamat> = ArrayList()
}