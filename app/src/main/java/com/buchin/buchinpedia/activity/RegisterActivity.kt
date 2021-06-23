package com.buchin.buchinpedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.buchin.buchinpedia.MainActivity
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.app.ApiConfig
import com.buchin.buchinpedia.helper.SharedPref
import com.buchin.buchinpedia.model.ResponseModel
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var s: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        s = SharedPref(this)

        btn_register.setOnClickListener {
            register()
        }
    }

    fun register(){
        if (edt_name.text.isEmpty()){
            edt_name.error = "Kolom Nama Tidak Boleh Kosong"
            edt_name.requestFocus()
            return
        }else if (edt_email.text.isEmpty()){
                edt_email.error = "Kolom Email Tidak Boleh Kosong"
                edt_email.requestFocus()
                return

        }else if (edt_phone.text.isEmpty()){
                  edt_phone.error = "Kolom No. Telepon Tidak Boleh Kosong"
                  edt_phone.requestFocus()
                 return
        }else if (edt_name.text.isEmpty()){
                 edt_name.error = "Kolom Nama Tidak Boleh Kosong"
                 edt_name.requestFocus()
                 return
        }else if (edt_password.text.isEmpty()){
            edt_password.error = "Kolom Password Tidak Boleh Kosong"
            edt_password.requestFocus()
            return
    }

        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.register(edt_name.text.toString(),edt_email.text.toString(),edt_phone.text.toString(),edt_password.text.toString())
            .enqueue(object : Callback<ResponseModel>{


                 override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                     pb.visibility = View.GONE
                     val response = response.body()!!

                    if (response.success == 1 ){

                        s.setStatusLogin(true)
                        val inten = Intent(this@RegisterActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(inten)
                        finish()
                        Toast.makeText(this@RegisterActivity,"Selamat datang:" + response.user.name,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@RegisterActivity,"Error:" + response.message,Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    pb.visibility = View.GONE
                   // handle error
                    Toast.makeText(this@RegisterActivity,"Error:" + t.message,Toast.LENGTH_SHORT).show()
                }


            })

    }
}