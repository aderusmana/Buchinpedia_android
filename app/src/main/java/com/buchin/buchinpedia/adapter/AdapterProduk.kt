package com.buchin.buchinpedia.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.buchin.buchinpedia.MainActivity
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.activity.DetailProdukActivity
import com.buchin.buchinpedia.activity.RegisterActivity
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.Produk
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterProduk(var activity : Activity,var data : ArrayList<Produk>): RecyclerView.Adapter<AdapterProduk.Holder> (){

    class Holder(view: View):RecyclerView.ViewHolder(view){

        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.imgProduk)
        val display = view.findViewById<CardView>(R.id.display)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNama.text = data[position].nama
        holder.tvHarga.text = Helper().gantiRupiah(data[position].harga)
//        holder.imgProduk.setImageResource(data[position].image)
        val img_url = "http://192.168.0.125/buchinpedia/public/storage/product/" + data[position].image
        Picasso.get()
                .load(img_url)
                .placeholder(R.drawable.produk)
                .error(R.drawable.produk)
                .into(holder.imgProduk)

        holder.display.setOnClickListener {
            val inten = Intent(activity, DetailProdukActivity::class.java)
            val str = Gson().toJson(data[position],Produk::class.java)
            inten.putExtra("extra",str)
            activity.startActivity(inten)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}