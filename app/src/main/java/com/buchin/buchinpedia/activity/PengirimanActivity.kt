package com.buchin.buchinpedia.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.adapter.AdapterKurir
import com.buchin.buchinpedia.app.ApiConfigAlamat
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.Alamat
import com.buchin.buchinpedia.model.rajaongkir.Costs
import com.buchin.buchinpedia.model.rajaongkir.ResponseOngkir
import com.buchin.buchinpedia.room.MyDatabase
import com.buchin.buchinpedia.util.ApiKey
import kotlinx.android.synthetic.main.activity_pengiriman.*
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class PengirimanActivity :AppCompatActivity() {

    lateinit var myDb :MyDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)
        Helper().setToolbar(this,toolbar ,"Pengiriman")
        myDb = MyDatabase.getInstance(this)!!

        mainButton()
        setSpinner()
    }



    private fun mainButton(){
        btn_tambahAlamat.setOnClickListener {
            startActivity(Intent(this,ListAlamatActivity::class.java))
        }
    }

    fun checkAlamat(){


        if (myDb.daoAlamat().getAlmByStatus(true) !=null){
            div_alamat.visibility = View.VISIBLE
            div_kosong2.visibility = View.GONE
            div_metode.visibility=View.VISIBLE

            val alamat = myDb.daoAlamat().getAlmByStatus(true)!!
            tv_nama.text = alamat.nama + " - "+ alamat.type
            tv_telp.text = alamat.telp
            tv_alamat.text = alamat.alamat + ", " + alamat.kota +","+ alamat.kecamatan + ","+alamat.kelurahan +","+alamat.kodepos+","+alamat.provinsi
            btn_tambahAlamat.text = "Ubah Alamat"

            div_metode.visibility = View.VISIBLE
            getOngkir("JNE")
        }else{
            div_alamat.visibility = View.GONE
            div_kosong2.visibility = View.VISIBLE
            btn_tambahAlamat.text = "Tambah Alamat"

        }
    }

    fun setSpinner(){
        val arrayString = ArrayList<String>()
        arrayString.add("JNE")
        arrayString.add("POS")
        arrayString.add("TIKI")

        val adapter = ArrayAdapter<Any>(this, R.layout.activity_item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_kurir.adapter = adapter
        spn_kurir.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0){

                    getOngkir(spn_kurir.selectedItem.toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    private fun getOngkir(kurir: String){
        val alamat = myDb.daoAlamat().getAlmByStatus(true)
        val origin = "501"
        val destination = "" + alamat!!.idKota.toString()
        val berat = 1000
        ApiConfigAlamat.instanceRetrofit.ongkir(ApiKey.key,origin,destination,berat,kurir.toLowerCase()).enqueue(object : Callback<ResponseOngkir> {
            override fun onResponse(call: Call<ResponseOngkir>, response: Response<ResponseOngkir>) {
                if (response.isSuccessful){
                    Log.d("Sucess","Berhasil:"+response.message())
                    val result = response.body()!!.rajaongkir.results
                    if (result.isNotEmpty()){
                        displayOngkir(result[0].code.toUpperCase(),result[0].costs)
                    }


                }else{
                    Log.d("Gagal","gagal:"+response.message())

                }
            }

            override fun onFailure(call: Call<ResponseOngkir>, t: Throwable) {
                Log.d("Gagal","gagal:"+ t.message)
            }

        })
    }

    private fun displayOngkir(kurir: String,arrayList: ArrayList<Costs>){

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_metode.adapter = AdapterKurir(arrayList,kurir,object : AdapterKurir.Listeners{

            override fun onClicked(data: Alamat) {

            }

        })
        rv_metode.layoutManager = layoutManager

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        checkAlamat()

        super.onResume()
    }
}