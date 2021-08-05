package com.buchin.buchinpedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.Produk
import com.buchin.buchinpedia.room.MyDatabase
import com.buchin.buchinpedia.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.toolbar

class DetailProdukActivity : AppCompatActivity() {

    lateinit var produk : Produk
    lateinit var myDb : MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)
        myDb = MyDatabase.getInstance(this)!! // call database

        getInfo()
        mainButton()
        checkKeranjang()
    }

    private fun mainButton(){
        btn_keranjang.setOnClickListener {
            val data = myDb.daoKeranjang().getProduk(produk.id)

            if (data == null){
                insert()
            }else{
                data.jumlah = data.jumlah + 1
                update(data)
            }

        }

        btn_favorit.setOnClickListener {
            val listNote = myDb.daoKeranjang().getAll() // get All data
            for(note :Produk in listNote){
                println("-----------------------")
                println(note.nama)
                println(note.harga)
            }
        }

        btn_tokeranjang.setOnClickListener {
            val inten = Intent("event:keranjang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(inten)
            onBackPressed()
        }

    }

    private fun insert(){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this,"Berhasil Menambah Ke Keranjang",Toast.LENGTH_SHORT).show()
            })
    }


    private fun update(data:Produk){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().update(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    checkKeranjang()
                    Log.d("respons", "data inserted")
                    Toast.makeText(this,"Berhasil Menambah Ke Keranjang",Toast.LENGTH_SHORT).show()
                })
    }

    private fun checkKeranjang(){
        val dataKeranjang = myDb.daoKeranjang().getAll()
        if (dataKeranjang.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKeranjang.size.toString()
        }else{
            div_angka.visibility = View.GONE
        }

    }

    fun getInfo(){
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data,Produk::class.java)
        //set value

        tv_nama.text = produk.nama
        tv_harga.text = Helper().gantiRupiah(produk.harga)
        tv_deskripsi.text = produk.deskripsi

        // image
        val img_url = Config.produkUrl  + produk.image
        Picasso.get()
            .load(img_url)
            .placeholder(R.drawable.produk)
            .error(R.drawable.produk)
            .into(image)

        //set toolbar nama produk
        Helper().setToolbar(this,toolbar,produk.nama)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}