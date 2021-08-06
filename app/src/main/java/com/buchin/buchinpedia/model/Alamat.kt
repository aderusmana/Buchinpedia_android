package com.buchin.buchinpedia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alamat") // the name of tabel
class Alamat {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    var idTb: Int? = null

    var id = 0
    var nama = ""
    var telp = ""
    var type = ""
    var alamat = ""

    var idProvinsi = 0
    var idKota = 0
//    var idKecamatan = 0
    var provinsi = ""
    var kota = ""
    var kecamatan = ""
    var kelurahan = ""
    var kodepos = ""
    var isSelected = false



}