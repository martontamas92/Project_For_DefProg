package com.example.qrcodescanner.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import com.example.qrcodescanner.MyApplication
import com.example.qrcodescanner.R
import com.example.qrcodescanner.models.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.progress_bar
import okhttp3.*
import java.io.IOException
import java.net.URL


class LoginActivity : AppCompatActivity()
{
    private lateinit var emailStr       : String
    private lateinit var passwordStr    : String

    override fun onCreate( savedInstanceState: Bundle? )
    {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_login )

        loadToolbar()
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { attemptLogin() }
    }

    private fun loadToolbar()
    {
        val toolbar: Toolbar = findViewById( R.id.toolbar_login )

        setSupportActionBar( toolbar )
    }

    private fun attemptLogin()
    {

        email.error     = null
        password.error  = null
        emailStr        = email.text.toString()
        passwordStr     = password.text.toString()

        var cancel              = false
        var focusView: View?    = null

        if ( !TextUtils.isEmpty( passwordStr ) && !isPasswordValid( passwordStr ) )
        {
            password.error  = getString(R.string.error_invalid_password)
            focusView       = password
            cancel          = true
        }

        if ( TextUtils.isEmpty( emailStr ) )
        {
            email.error = getString( R.string.error_field_required )
            focusView   = email
            cancel      = true
        }
        else if ( !isEmailValid( emailStr ) )
        {
            email.error = getString( R.string.error_invalid_email )
            focusView   = email
            cancel      = true
        }

        if( cancel )
        {
            focusView?.requestFocus()
        }
        else
        {
            progress_bar.visibility = View.VISIBLE

            loginAsync()
        }
    }

    private fun isEmailValid( email: String ): Boolean
    {
        return email.contains("@")
    }

    private fun isPasswordValid( password: String ): Boolean
    {
        return password.length > 4
    }

    private fun loginAsync()
    {
        val client          = OkHttpClient()
        val url             = URL( MyApplication.URL + MyApplication.LOGIN )
        val fromBodyBuilder = FormBody.Builder()

        fromBodyBuilder.add( "uname", emailStr )
        fromBodyBuilder.add( "passwd", passwordStr )
        fromBodyBuilder.add( "status", "student" )

        val token   = MyApplication.instance.bearerToken
        val request = Request.Builder()
            .addHeader("Authorization", "Bearer  $token")
            .url( url )
            .post( fromBodyBuilder.build() )
            .build()

        client.newCall( request ).enqueue( object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                val jsonData    = response.body()!!.string()
                Log.i( "response", jsonData )

                if (!response.isSuccessful){
                    runOnUiThread {
                        Toast.makeText( applicationContext, R.string.error_login, Toast.LENGTH_SHORT  ).show()
                        progress_bar.visibility = View.GONE
                    }

                    return
                }

                var token                           = response.header( "Authorization" )
                token                               = token!!.substringAfter( "Bearer " )
                MyApplication.instance.user         = User( jsonData )
                MyApplication.instance.isLoggedIn   = true
                MyApplication.instance.bearerToken  = token

                Log.i( "response id",  MyApplication.instance.user.id.toString() )
                Log.i( "response", token )
                finish()
            }
        })
    }
}
