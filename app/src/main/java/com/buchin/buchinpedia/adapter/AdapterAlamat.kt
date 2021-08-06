package com.buchin.buchinpedia.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.buchin.buchinpedia.MainActivity
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.activity.DetailProdukActivity
import com.buchin.buchinpedia.activity.RegisterActivity
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.Alamat
import com.buchin.buchinpedia.model.Produk
import com.buchin.buchinpedia.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterAlamat( var data : ArrayList<Alamat>,var listener:Listeners): RecyclerView.Adapter<AdapterAlamat.Holder> (){

    class Holder(view: View):RecyclerView.ViewHolder(view){

        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvTelp = view.findViewById<TextView>(R.id.tv_telp)
        val tvAlamat = view.findViewById<TextView>(R.id.tv_alamat)
        val display = view.findViewById<CardView>(R.id.display)
        val radio = view.findViewById<RadioButton>(R.id.rb_alamat)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_alamat, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val alamat  = data[position]
        holder.radio.isChecked = alamat.isSelected
        holder.tvNama.text = alamat.nama + " - "+ alamat.type
        holder.tvTelp.text = alamat.telp
        holder.tvAlamat.text =  alamat.alamat + "\n " + alamat.kota +"\n"+ alamat.kecamatan + "\n"+alamat.kelurahan +"\n"+alamat.kodepos+"\n"+alamat.provinsi

        holder.radio.setOnClickListener {
            alamat.isSelected = true
            listener.onClicked(alamat)
        }
        holder.display.setOnClickListener {
        alamat.isSelected = true
            listener.onClicked(alamat)
        }

    }



    interface  Listeners{
        fun onClicked(data:Alamat)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}