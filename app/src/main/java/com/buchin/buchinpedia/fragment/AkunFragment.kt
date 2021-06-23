package com.buchin.buchinpedia.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.helper.SharedPref

/**
 * A simple [Fragment] subclass.
 * Use the [AkunFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AkunFragment : Fragment() {
    lateinit var s:SharedPref
    lateinit var btnLogout : TextView
    lateinit var tvNama : TextView
    lateinit var tvPhone : TextView
    lateinit var tvEmail : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)
        init(view)

        s = SharedPref(requireActivity())
        btnLogout.setOnClickListener {
            s.setStatusLogin(false)

        }

        setData()

        return view
    }

    fun setData(){
        tvNama.text = s.getString(s.nama)
        tvPhone.text = s.getString(s.phone)
        tvEmail.text = s.getString(s.email)
    }

    private fun init(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        tvNama = view.findViewById(R.id.tv_nama)
        tvPhone = view.findViewById(R.id.tv_phone)
        tvEmail = view.findViewById(R.id.tv_email)


    }


}