package com.buchin.buchinpedia.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.adapter.AdapterSlider

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var vpSlider : ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment

        vpSlider = view.findViewById(R.id.vp_slider)
        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.slider1)
        arrSlider.add(R.drawable.slider2)
        arrSlider.add(R.drawable.slider3)
        arrSlider.add(R.drawable.slider4)

        val adapterSlider = AdapterSlider(arrSlider,activity)
        vpSlider.adapter = adapterSlider
        return view
    }




}