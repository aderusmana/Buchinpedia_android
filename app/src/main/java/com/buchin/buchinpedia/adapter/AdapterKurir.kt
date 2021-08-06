package com.buchin.buchinpedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.Alamat
import com.buchin.buchinpedia.model.rajaongkir.Costs
import kotlin.collections.ArrayList

class AdapterKurir(var data: ArrayList<Costs>, var kurir:String, var listener: Listeners): RecyclerView.Adapter<AdapterKurir.Holder> (){

    class Holder(view: View):RecyclerView.ViewHolder(view){

        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvDurasi = view.findViewById<TextView>(R.id.tv_durasi)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvBerat = view.findViewById<TextView>(R.id.tv_berat)
        val display = view.findViewById<LinearLayout>(R.id.display)
        val radio = view.findViewById<RadioButton>(R.id.rb)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_kurir, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val alamat  = data[position]
//        holder.radio.isChecked = alamat.isSelected
        holder.tvNama.text = kurir +" " + alamat.service
        val cost = alamat.cost[0]
        holder.tvDurasi.text = cost.etd + " hari kerja"
        holder.tvHarga.text = Helper().gantiRupiah(cost.value)
        holder.tvBerat.text = "1 kg" + Helper().gantiRupiah(cost.value)
//        holder.tvAlamat.text =  alamat.alamat + "\n " + alamat.kota +"\n"+ alamat.kecamatan + "\n"+alamat.kelurahan +"\n"+alamat.kodepos+"\n"+alamat.provinsi
//
//        holder.radio.setOnClickListener {
//            alamat.isSelected = true
//            listener.onClicked(alamat)
//        }
//        holder.display.setOnClickListener {
//        alamat.isSelected = true
//            listener.onClicked(alamat)
//        }

    }



    interface  Listeners{
        fun onClicked(data:Alamat)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}