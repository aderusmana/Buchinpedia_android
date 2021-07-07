package com.buchin.buchinpedia.model

class ResponseModel {
    var success = 0
    lateinit var message:String
    var user = User()
    var products:ArrayList<Produk> = ArrayList()
}