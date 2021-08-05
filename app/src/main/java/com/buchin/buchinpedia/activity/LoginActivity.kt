package com.buchin.buchinpedia.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.buchin.buchinpedia.MainActivity
import com.buchin.buchinpedia.R
import com.buchin.buchinpedia.app.ApiConfig
import com.buchin.buchinpedia.helper.SharedPref
import com.buchin.buchinpedia.model.ResponseModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var s:SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        s = SharedPref(this)

        mainButton()
    }

   private fun mainButton(){

       btn_login.setOnClickListener {
           login()
       }
       btn_register.setOnClickListener {
       startActivity(Intent(this, RegisterActivity::class.java))
       }

    }

    fun login(){
        if (edt_email.text.isEmpty()){
            edt_email.error = "Kolom Email Tidak Boleh Kosong"
            edt_email.requestFocus()
            return

        }else if (edt_password.text.isEmpty()){
            edt_password.error = "Kolom Password Tidak Boleh Kosong"
            edt_password.requestFocus()
            return
        }

        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.login(edt_email.text.toString(),edt_password.text.toString())
                .enqueue(object : Callback<ResponseModel> {


                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                        pb.visibility = View.GONE
                        val response = response.body()!!

                        if (response.success == 1 ){
                            s.setStatusLogin(true)
                            s.setUser(response.user)

                            val inten = Intent(this@LoginActivity,MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(inten)
                            finish()
                            Toast.makeText(this@LoginActivity,"Selamat datang:" + response.user.name, Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@LoginActivity,"Error:" + response.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        pb.visibility = View.GONE
                        // handle error
                        Toast.makeText(this@LoginActivity,"Error:" + t.message, Toast.LENGTH_SHORT).show()
                    }


                })

    }
}