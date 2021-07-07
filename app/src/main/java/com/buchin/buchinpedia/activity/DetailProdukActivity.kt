package com.buchin.buchinpedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.Produk
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailProdukActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)

        getInfo()
    }

    fun getInfo(){
        val data = intent.getStringExtra("extra")
        val produk = Gson().fromJson<Produk>(data,Produk::class.java)
        //set value

        tv_nama.text = produk.nama
        tv_harga.text = Helper().gantiRupiah(produk.harga)
        tv_deskripsi.text = produk.deskripsi

        // image
        val img_url = "http://192.168.0.125/buchinpedia/public/storage/product/" + produk.image
        Picasso.get()
            .load(img_url)
            .placeholder(R.drawable.produk)
            .error(R.drawable.produk)
            .into(image)

        //set toolbar nama produk

        setSupportActionBar(toolbar)
        supportActionBar!!.title = produk.nama
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}