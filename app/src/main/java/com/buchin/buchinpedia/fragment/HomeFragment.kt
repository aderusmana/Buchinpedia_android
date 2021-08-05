package com.buchin.buchinpedia.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.buchin.buchinpedia.MainActivity
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.adapter.AdapterProduk
import com.buchin.buchinpedia.adapter.AdapterSlider
import com.buchin.buchinpedia.app.ApiConfig
import com.buchin.buchinpedia.model.Produk
import com.buchin.buchinpedia.model.ResponseModel
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var vpSlider : ViewPager
    lateinit var rv_produk : RecyclerView
    lateinit var rv_produkTerlaris : RecyclerView
    lateinit var rv_produkElektronik : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        init(view)
        getProduct()



        return view
    }

    private var listProduk:ArrayList<Produk> = ArrayList()
    fun init(view: View){
        vpSlider = view.findViewById(R.id.vp_slider)
        rv_produk = view.findViewById(R.id.rv_produk)
        rv_produkTerlaris = view.findViewById(R.id.rv_produkTerlaris)
        rv_produkElektronik  = view.findViewById(R.id.rv_produkElektronik)

    }

    fun getProduct(){
        ApiConfig.instanceRetrofit.getProduct().enqueue(object : Callback<ResponseModel> {


                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                        val res = response.body()!!
                        if (res.success == 1){
                            listProduk = res.products
                            displayProduct()
                        }
                    }
                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    }
                })
    }

    fun displayProduct(){
        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.slider1)
        arrSlider.add(R.drawable.slider2)
        arrSlider.add(R.drawable.slider3)
        arrSlider.add(R.drawable.slider4)

        val adapterSlider = AdapterSlider(arrSlider,activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager3 = LinearLayoutManager(activity)
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL

        rv_produk.adapter = AdapterProduk(requireActivity(), listProduk)
        rv_produk.layoutManager = layoutManager

        rv_produkTerlaris.adapter = AdapterProduk(requireActivity(),listProduk)
        rv_produkTerlaris.layoutManager = layoutManager2

        rv_produkElektronik.adapter = AdapterProduk(requireActivity(),listProduk)
        rv_produkElektronik.layoutManager = layoutManager3

    }






//    val arrProduk: ArrayList<Produk>get() {
//
//        val arr = ArrayList<Produk>()
//        val p1= Produk()
//        p1.nama = "ROG "
//        p1.harga = "Rp.15.900.000"
//        p1.gambar = R.drawable.roglp
//
//        val p2= Produk()
//        p2.nama = "Razer "
//        p2.harga = "Rp.10.900.000"
//        p2.gambar = R.drawable.razer
//
//        val p3= Produk()
//        p3.nama = "Macbook "
//        p3.harga = "Rp.25.900.000"
//        p3.gambar = R.drawable.macbook
//
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//
//
//        return arr
//    }
//
//    val arrProdukTerlaris: ArrayList<Produk>get() {
//
//        val arr = ArrayList<Produk>()
//        val p1= Produk()
//        p1.nama = "ROG "
//        p1.harga = "Rp.15.900.000"
//        p1.gambar = R.drawable.roglp
//
//        val p2= Produk()
//        p2.nama = "Razer "
//        p2.harga = "Rp.10.900.000"
//        p2.gambar = R.drawable.razer
//
//        val p3= Produk()
//        p3.nama = "Macbook "
//        p3.harga = "Rp.25.900.000"
//        p3.gambar = R.drawable.macbook
//
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//
//
//        return arr
//    }
//
//    val arrProdukElektronik: ArrayList<Produk>get() {
//
//        val arr = ArrayList<Produk>()
//        val p1= Produk()
//        p1.nama = "ROG "
//        p1.harga = "Rp.15.900.000"
//        p1.gambar = R.drawable.roglp
//
//        val p2= Produk()
//        p2.nama = "Razer "
//        p2.harga = "Rp.10.900.000"
//        p2.gambar = R.drawable.razer
//
//        val p3= Produk()
//        p3.nama = "Macbook "
//        p3.harga = "Rp.25.900.000"
//        p3.gambar = R.drawable.macbook
//
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//
//
//        return arr
//    }



}