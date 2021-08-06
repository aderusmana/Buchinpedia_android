package com.buchin.buchinpedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.adapter.AdapterAlamat
import com.buchin.buchinpedia.adapter.AdapterProduk
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.Alamat
import com.buchin.buchinpedia.model.Produk
import com.buchin.buchinpedia.room.MyDatabase
import com.buchin.buchinpedia.util.Config.alamat
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.ArrayList

class ListAlamatActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alamat)
        Helper().setToolbar(this,toolbar ,"Pilih Alamat")

        mainButton()
    }

    private fun mainButton(){
        btn_tambahAlamat2.setOnClickListener {
            startActivity(Intent(this,TambahAlamatActivity::class.java))
        }
    }

    private fun displayAlamat(){
        val myDb = MyDatabase.getInstance(this)!!
        val arrayList = myDb.daoAlamat().getAll() as ArrayList<Alamat>

        if (arrayList.isEmpty()) div_kosong.visibility = View.VISIBLE
        else div_kosong.visibility = View.GONE

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_alamat.adapter = AdapterAlamat( arrayList,object :AdapterAlamat.Listeners{

            override fun onClicked(data: Alamat) {

                for (alamat in arrayList){
                    alamat .isSelected = false
                    update(alamat)
                }
                data.isSelected = true
                update(data)
                onBackPressed()

            }

        })
        rv_alamat.layoutManager = layoutManager

    }

    private fun update(data: Alamat){
        val myDb = MyDatabase.getInstance(this)!!
        CompositeDisposable().add(Observable.fromCallable { myDb.daoAlamat().update(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                })
    }

    override fun onResume() {
        displayAlamat()
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}