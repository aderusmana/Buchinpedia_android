package com.buchin.buchinpedia.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.app.ApiConfigAlamat
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.ResponseModel
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahAlamatActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_alamat)

        Helper().setToolbar(this,toolbar ,"Pengiriman")

        getProvinsi()

    }

    private fun getProvinsi(){
        ApiConfigAlamat.instanceRetrofit.getProvinsi().enqueue(object : Callback<ResponseModel> {


            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {

                if (response.isSuccessful){
                    pb.visibility = View.GONE
                    div_provinsi.visibility =View.VISIBLE
                    val res = response.body()!!
                    val arrayString = ArrayList<String>()
                    arrayString.add("Pilih Provinsi")
                    val listProvinsi = res.provinsi
                    for (prov in res.provinsi){
                        arrayString.add(prov.nama)
                    }


                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity,R.layout.activity_item_spinner,arrayString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_provinsi.adapter = adapter

                    spn_provinsi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            if (position != 0){
                                val idProv = listProvinsi[position - 1].id
                                Log.d("respon","Provinsi Id:"+ idProv  + " " +"name:"+ listProvinsi[position - 1].nama)
                                getKota(idProv)
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }
                }else{
                    Log.d("Error","gagal : " +response.message())

                }


            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
            }
        })
    }

    private fun getKota(id: Int){
        ApiConfigAlamat.instanceRetrofit.getKota(id).enqueue(object : Callback<ResponseModel> {


            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {

                if (response.isSuccessful) {
                    pb.visibility = View.GONE
                    div_kota.visibility = View.VISIBLE
                    val res = response.body()!!
                    val arrayString = ArrayList<String>()
                    arrayString.add("Pilih Kabupaten Kota")
                    val listKota = res.kota_kabupaten
                    for (kota in listKota) {
                        arrayString.add(kota.nama)
                    }


                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity, R.layout.activity_item_spinner, arrayString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kota.adapter = adapter
                    spn_kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            if (position != 0){
                                val idKota = listKota[position - 1].id
                                Log.d("respon","Kota Id:"+ idKota  + " " +"name:"+ listKota[position - 1].nama)
                                getKecamatan(idKota)
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }


                } else {
                    Log.d("Error", "gagal : " + response.message())

                }


            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
            }
        })
    }

    private fun getKecamatan(id:Int){
        ApiConfigAlamat.instanceRetrofit.getKecamatan(id).enqueue(object : Callback<ResponseModel> {


            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {

                if (response.isSuccessful){
                    pb.visibility = View.GONE
                    div_kecamatan.visibility =View.VISIBLE
                    val res = response.body()!!
                    val arrayString = ArrayList<String>()
                    arrayString.add("Pilih Kecamatan")
                    val listKecamatan = res.kecamatan
                    for (kecamatan in listKecamatan){
                        arrayString.add(kecamatan.nama)
                    }


                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity,R.layout.activity_item_spinner,arrayString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kecamatan.adapter = adapter
                    spn_kecamatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            if (position != 0){
                                val idKecamatan = listKecamatan[position - 1].id
                                Log.d("respon","Kecamatan Id:"+ idKecamatan  + " " +"name:"+ listKecamatan[position - 1].nama)
                                getKelurahan(idKecamatan)
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }
                }else{
                    Log.d("Error","gagal : " +response.message())

                }


            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
            }
        })
    }

    private fun getKelurahan(id: Int){
        ApiConfigAlamat.instanceRetrofit.getKelurahan(id).enqueue(object : Callback<ResponseModel> {


            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {

                if (response.isSuccessful) {
                    pb.visibility = View.GONE
                    div_kelurahan.visibility = View.VISIBLE
                    val res = response.body()!!
                    val arrayString = ArrayList<String>()
                    arrayString.add("Pilih Kelurahan")
                    val listKelurahan = res.kelurahan
                    for (kelurahan in listKelurahan) {
                        arrayString.add(kelurahan.nama)
                    }


                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity, R.layout.activity_item_spinner, arrayString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kelurahan.adapter = adapter

                } else {
                    Log.d("Error", "gagal : " + response.message())

                }


            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
            }
        })
    }





    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}