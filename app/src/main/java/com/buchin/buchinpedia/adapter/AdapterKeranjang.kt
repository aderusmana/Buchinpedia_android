package com.buchin.buchinpedia.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.Produk
import com.buchin.buchinpedia.room.MyDatabase
import com.buchin.buchinpedia.util.Config
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class AdapterKeranjang(var activity : Activity, var data : ArrayList<Produk>,var listener : Listeners): RecyclerView.Adapter<AdapterKeranjang.Holder> (){

    class Holder(view: View):RecyclerView.ViewHolder(view){

        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.imgProduk)
        val display = view.findViewById<CardView>(R.id.display)
        val btnPlus = view.findViewById<ImageView>(R.id.btn_plus)
        val btnMin = view.findViewById<ImageView>(R.id.btn_minus)
        val btnDelete = view.findViewById<ImageView>(R.id.btn_delete)
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_keranjang, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val produk = data[position]
        val harga = Integer.valueOf(produk.harga)
        holder.tvNama.text = produk.nama
        holder.tvHarga.text = Helper().gantiRupiah(harga * produk.jumlah)


        var jumlah = data[position].jumlah
        holder.tvJumlah.text = jumlah.toString()

        holder.checkBox.isChecked = produk.selected
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
        produk.selected = isChecked
            update(produk)
        }


        val img_url = Config.produkUrl + data[position].image
        Picasso.get()
                .load(img_url)
                .placeholder(R.drawable.produk)
                .error(R.drawable.produk)
                .into(holder.imgProduk)

        holder.btnPlus.setOnClickListener {
//            if (jumlah >= stok ) return@setOnClickListener
            jumlah++
            produk.jumlah = jumlah
            update(produk)
            holder.tvJumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(harga * jumlah)
        }

        holder.btnMin.setOnClickListener {
            if (jumlah <= 1) return@setOnClickListener
            jumlah--

            produk.jumlah = jumlah
            update(produk)
            holder.tvJumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(harga * jumlah)
        }

        holder.btnDelete.setOnClickListener {
            delete(produk)
            listener.onDelete(position)

        }

    }

    interface Listeners{
        fun onUpdate()
        fun onDelete(position: Int)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun update(data:Produk){
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjang().update(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    listener.onUpdate()
                })
    }

    private fun delete(data:Produk){
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjang().delete(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                })
    }
}