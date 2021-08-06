package com.buchin.buchinpedia.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.app.ApiConfigAlamat
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.Alamat
import com.buchin.buchinpedia.model.ModelAlamat
import com.buchin.buchinpedia.model.ResponseModel
import com.buchin.buchinpedia.room.MyDatabase
import com.buchin.buchinpedia.util.ApiKey
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.activity_tambah_alamat.pb
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahAlamatActivity:AppCompatActivity() {

    var provinsi = ModelAlamat.Results()
    var kota = ModelAlamat.Results()
    var kecamatan = ModelAlamat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_alamat)

        Helper().setToolbar(this,toolbar ,"Pengiriman")
        mainButton()

        getProvinsi()

    }

    private fun mainButton(){
        btn_simpan.setOnClickListener {
        simpan()
        }
    }

    private fun simpan(){
        when {
            edt_nama.text.isEmpty() -> {
                edt_nama.error = "Kolom Nama Tidak Boleh Kosong"
                edt_nama.requestFocus()
                return
            }
            edt_telp.text.isEmpty() -> {
                edt_telp.error = "Kolom No. Telepon Tidak Boleh Kosong"
                edt_telp.requestFocus()
                return

            }
            edt_type.text.isEmpty() -> {
                edt_type.error = "Kolom Type Tidak Boleh Kosong"
                edt_type.requestFocus()
                return
            }
            edt_alamat.text.isEmpty() -> {
                edt_alamat.error = "Kolom Alamat Tidak Boleh Kosong"
                edt_alamat.requestFocus()
                return
            }
            edt_kecamatan.text.isEmpty()->{
                edt_kecamatan.error = "Kolom Kecamatan Tidak Boleh Kosong"
                edt_kecamatan.requestFocus()
                return
            }
            edt_kelurahan.text.isEmpty() -> {
                edt_kelurahan.error = "Kolom Kelurahan Tidak Boleh Kosong"
                edt_kelurahan.requestFocus()
                return
            }
            edt_pos.text.isEmpty() -> {
                edt_pos.error = "Kolom Kode Pos Tidak Boleh Kosong"
                edt_pos.requestFocus()
                return
            }
        }

        if (provinsi.province_id == "0"){
            toast("Silahkan Pilih Provinsi")
        return
        }

        if (kota.city_id == "0"){
            toast("Silahkan Pilih Kota")
            return
        }

//        if (kecamatan.id == 0){
//            toast("Silahkan Pilih Kecamatan")
//            return
//        }
        val alamat = Alamat()
        alamat.nama = edt_nama.text.toString()
        alamat.telp = edt_telp.text.toString()
        alamat.type = edt_type.text.toString()
        alamat.alamat = edt_alamat.text.toString()
        alamat.kecamatan = edt_kecamatan.text.toString()
        alamat.kelurahan = edt_kelurahan.text.toString()
        alamat.kodepos = edt_pos.text.toString()
        alamat.idProvinsi = Integer.valueOf(provinsi.province_id)
        alamat.provinsi = provinsi.province
        alamat.idKota = Integer.valueOf(kota.city_id)
        alamat.kota = kota.city_name
//        alamat.idKecamatan = kecamatan.id


        insert(alamat)


    }

    fun toast(string: String){
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show()
    }

    private fun getProvinsi(){
        ApiConfigAlamat.instanceRetrofit.getProvinsi(ApiKey.key).enqueue(object : Callback<ResponseModel> {


            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {

                if (response.isSuccessful){
                    pb.visibility = View.GONE
                    div_provinsi.visibility =View.VISIBLE
                    val res = response.body()!!
                    val arrayString = ArrayList<String>()
                    arrayString.add("Pilih Provinsi")
                    val listProvinsi = res.rajaongkir.results
                    for (prov in listProvinsi){
                        arrayString.add(prov.province)
                    }


                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity,R.layout.activity_item_spinner,arrayString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_provinsi.adapter = adapter

                    spn_provinsi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            if (position != 0){
                                provinsi = listProvinsi[position - 1]
                                val idProv = provinsi.province_id
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

    private fun getKota(id: String){
        ApiConfigAlamat.instanceRetrofit.getKota(ApiKey.key, id).enqueue(object : Callback<ResponseModel> {


            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {

                if (response.isSuccessful) {
                    pb.visibility = View.GONE
                    div_kota.visibility = View.VISIBLE
                    val res = response.body()!!
                    val arrayString = ArrayList<String>()
                    arrayString.add("Pilih Kabupaten Kota")
                    val listKota = res.rajaongkir.results
                    for (kota in listKota) {
                        arrayString.add(kota.type+" - "+ kota.city_name)
                    }


                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity, R.layout.activity_item_spinner, arrayString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kota.adapter = adapter
                    spn_kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            if (position != 0){
                                kota = listKota[position - 1]
                                val kodePos = kota.postal_code
                                edt_pos.setText(kodePos)
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

//    private fun getKecamatan(id:String){
//        ApiConfigAlamat.instanceRetrofit.getKecamatan(id).enqueue(object : Callback<ResponseModel> {
//
//
//            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
//
//                if (response.isSuccessful){
//                    pb.visibility = View.GONE
//                    div_kecamatan.visibility =View.VISIBLE
//                    val res = response.body()!!
//                    val arrayString = ArrayList<String>()
//                    arrayString.add("Pilih Kecamatan")
//                    val listKecamatan = res.kecamatan
//                    for (kecamatan in listKecamatan){
//                        arrayString.add(kecamatan.nama)
//                    }
//
//
//                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity,R.layout.activity_item_spinner,arrayString.toTypedArray())
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                    spn_kecamatan.adapter = adapter
//                    spn_kecamatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                            if (position != 0){
//                                kecamatan = listKecamatan[position - 1]
//
//                            }
//                        }
//
//                        override fun onNothingSelected(parent: AdapterView<*>?) {
//                            TODO("Not yet implemented")
//                        }
//
//                    }
//                }else{
//                    Log.d("Error","gagal : " +response.message())
//
//                }
//
//
//            }
//            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
//            }
//        })
//    }

//    private fun getKelurahan(id: Int){
//        ApiConfigAlamat.instanceRetrofit.getKelurahan(id).enqueue(object : Callback<ResponseModel> {
//
//
//            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
//
//                if (response.isSuccessful) {
//                    pb.visibility = View.GONE
//                    div_kelurahan.visibility = View.VISIBLE
//                    val res = response.body()!!
//                    val arrayString = ArrayList<String>()
//                    arrayString.add("Pilih Kelurahan")
//                    val listKelurahan = res.kelurahan
//                    for (kelurahan in listKelurahan) {
//                        arrayString.add(kelurahan.nama)
//                    }
//
//
//                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity, R.layout.activity_item_spinner, arrayString.toTypedArray())
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                    spn_kelurahan.adapter = adapter
//
//                } else {
//                    Log.d("Error", "gagal : " + response.message())
//
//                }
//
//
//            }
//            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
//            }
//        })
//    }

    private fun insert(data : Alamat){
        val myDb = MyDatabase.getInstance(this)!!
        if (myDb.daoAlamat().getAlmByStatus(true) == null){
            data.isSelected = true
        }
        CompositeDisposable().add(Observable.fromCallable { myDb.daoAlamat().insert(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    toast("Insert Data Success")
                    onBackPressed()
                })
    }





    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}