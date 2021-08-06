package com.buchin.buchinpedia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "keranjang") // the name of tabel
class Produk : Serializable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    var idTb: Int? = null

    var id :Int = 0
    var nama : String = ""
    var harga : String = ""
    var deskripsi : String = ""
    var category_id : Int = 0
    var image :String = ""
    var created_at : String = ""
    var update_at : String = ""
    var jumlah : Int = 1
    var selected : Boolean = true
    var discount : Int = 0
}