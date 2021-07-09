package com.buchin.buchinpedia.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.adapter.AdapterKeranjang
import com.buchin.buchinpedia.helper.Helper
import com.buchin.buchinpedia.model.Produk
import com.buchin.buchinpedia.room.MyDatabase
import kotlinx.android.synthetic.main.fragment_keranjang.*

/**
 * A simple [Fragment] subclass.
 * Use the [KeranjangFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KeranjangFragment : Fragment() {
    lateinit var myDb : MyDatabase

// oncreate hanya dipanggil sekali ketika activity aktif jd display tidak otomatis refresh
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        // Inflate the layout for this fragment
        init(view)
        myDb = MyDatabase.getInstance(requireActivity())!!
        mainButton()

        return view
    }
    lateinit var adapter  : AdapterKeranjang
    var listProduk = ArrayList<Produk>()
    private fun displayProduk(){

        listProduk = myDb.daoKeranjang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL


        adapter = AdapterKeranjang(requireActivity(), listProduk, object : AdapterKeranjang.Listeners {
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listProduk.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }

        })

        rvProduk.adapter = adapter
        rvProduk.layoutManager = layoutManager
    }

    fun hitungTotal(){
        val listProduk = myDb.daoKeranjang().getAll() as ArrayList

        var totalHarga = 0
        var isSelectAll = true
        for (produk in listProduk) {
            if (produk.selected) {
                val harga = Integer.valueOf(produk.harga)
                totalHarga += (harga * produk.jumlah)
            }else{
                isSelectAll = false
            }
        }
        cbAll.isChecked = isSelectAll
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton(){
        btnDelete.setOnClickListener {

        }
        btnBayar.setOnClickListener {

        }

        cbAll.setOnClickListener {
            for (i in listProduk.indices){
                val produk = listProduk[i]
                produk.selected = cbAll.isChecked
                listProduk[i] = produk
            }
            adapter.notifyDataSetChanged()
        }
    }

    lateinit var btnDelete :ImageView
    lateinit var rvProduk : RecyclerView
    lateinit var tvTotal : TextView
    lateinit var btnBayar : TextView
    lateinit var cbAll : CheckBox

    private fun init(view: View) {
        btnDelete = view.findViewById(R.id.btn_delete)
        btnBayar  = view.findViewById(R.id.tv_bayar)
        rvProduk = view.findViewById(R.id.rv_produk)
        tvTotal = view.findViewById(R.id.tv_total)
        cbAll = view.findViewById(R.id.cb_all)
    }

    override fun onResume() {
        displayProduk()
        hitungTotal()

        super.onResume()
    }


}