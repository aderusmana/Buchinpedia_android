package com.buchin.buchinpedia.helper

import java.text.NumberFormat
import java.util.*

class Helper {
    fun gantiRupiah(string: String):String{
        return NumberFormat.getCurrencyInstance(Locale("in","ID")).format(Integer.valueOf(string))
    }

    fun gantiRupiah(value: Int):String{
        return NumberFormat.getCurrencyInstance(Locale("in","ID")).format(value)
    }
}