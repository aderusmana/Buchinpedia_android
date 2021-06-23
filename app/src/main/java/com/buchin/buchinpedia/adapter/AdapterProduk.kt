package com.buchin.buchinpedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.model.Produk

class AdapterProduk(var data : ArrayList<Produk>): RecyclerView.Adapter<AdapterProduk.Holder> (){

    class Holder(view: View):RecyclerView.ViewHolder(view){

        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.imgProduk)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNama.text = data[position].nama
        holder.tvHarga.text = data[position].harga
        holder.imgProduk.setImageResource(data[position].gambar)

    }

    override fun getItemCount(): Int {
        return data.size
    }
}