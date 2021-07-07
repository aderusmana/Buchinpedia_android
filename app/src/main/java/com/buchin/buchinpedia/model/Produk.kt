package com.buchin.buchinpedia.model

import java.io.Serializable

class Produk : Serializable{

    var id :Int = 0
    var nama : String = ""
    var harga : String = ""
    var deskripsi : String = ""
    var category_id : Int = 0
    var image :String = ""
    var created_at : String = ""
    var update_at : String = ""
}