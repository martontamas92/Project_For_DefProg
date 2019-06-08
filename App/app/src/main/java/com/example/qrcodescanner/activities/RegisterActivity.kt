package com.example.qrcodescanner.activities

import android.support.v7.app.AppCompatActivity
import android.os.AsyncTask
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
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.email
import kotlinx.android.synthetic.main.activity_register.email_sign_in_button
import kotlinx.android.synthetic.main.activity_register.password
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

class RegisterActivity : AppCompatActivity()
{
    private lateinit var firstNameStr       : String
    private lateinit var middleNameStr      : String
    private lateinit var lastNameStr        : String
    private lateinit var neptunCodeStr      : String
    private lateinit var emailStr           : String
    private lateinit var passwordStr        : String
    private lateinit var confirmPasswordStr : String
    private var mAuthTask: RegisterTask?    = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loadToolbar()
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if ( id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL )
            {
                attemptRegister()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { attemptRegister() }
    }

    private fun loadToolbar()
    {
        val toolbar: Toolbar = findViewById( R.id.toolbar_register )

        setSupportActionBar( toolbar )
    }

    private fun attemptRegister()
    {
        if ( mAuthTask != null )
        {
            return
        }

        first_name.error        = null
        middle_name.error       = null
        last_name.error         = null
        neptun_code.error       = null
        email.error             = null
        password.error          = null
        password_confirm.error  = null

        firstNameStr            = first_name.text.toString()
        middleNameStr           = middle_name.text.toString()
        lastNameStr             = last_name.text.toString()
        neptunCodeStr           = neptun_code.text.toString()
        emailStr                = email.text.toString()
        passwordStr             = password.text.toString()
        confirmPasswordStr      = password_confirm.text.toString()

        var cancel              = false
        var focusView: View?    = null

        if ( !TextUtils.isEmpty( passwordStr ) && !isPasswordValid( passwordStr ) )
        {
            password.error = getString( R.string.error_invalid_password )
            focusView = password
            cancel = true
        }

        if ( !TextUtils.isEmpty( neptunCodeStr ) && !isNeptunCodeValid( neptunCodeStr ) )
        {
            neptun_code.error   = getString(R.string.error_invalid_password)
            focusView           = neptun_code
            cancel              = true
        }

        if ( !isPasswordMatch( passwordStr, confirmPasswordStr ) )
        {
            password.error   = getString( R.string.error_password_match )
            focusView        = password
            cancel           = true
        }
        if ( TextUtils.isEmpty( lastNameStr ) )
        {
            last_name.error = getString(R.string.error_field_required)
            focusView       = last_name
            cancel          = true
        }

        if ( TextUtils.isEmpty( firstNameStr ) )
        {
            first_name.error    = getString( R.string.error_field_required )
            focusView           = first_name
            cancel              = true
        }

        if ( TextUtils.isEmpty( emailStr ) )
        {
            email.error = getString( R.string.error_field_required )
            focusView   = email
            cancel      = true
        }
        else if ( !isEmailValid( emailStr ) )
        {
            email.error = getString(R.string.error_invalid_email)
            focusView   = email
            cancel      = true
        }

        if ( cancel )
        {
            focusView?.requestFocus()
        }
        else
        {
            progress_bar.visibility = View.VISIBLE
            mAuthTask               = RegisterTask()

            mAuthTask!!.execute( null as Void? )
        }
    }

    private fun isEmailValid( email: String ): Boolean
    {
        return ( email.contains( "@" ) || email.contains( "." ) ) && email.length > 4
    }

    private fun isPasswordValid( password: String): Boolean
    {
        return password.length >= 6
    }

    private fun isPasswordMatch( password: String, confirmPassword : String ): Boolean
    {
        return password == confirmPassword
    }

    private fun isNeptunCodeValid( neptunCode: String): Boolean
    {
        return neptunCode.length == 6
    }

    private fun createJsonForRegister(): JSONObject
    {
        var jsonObject = JSONObject(
            """{"Name": {
                |       "firstName": "$firstNameStr",
                |       "middleName": "$middleNameStr",
                |       "lastName": "$lastNameStr"
                |   },
                |   "Neptun": {
                |       "neptun": "$neptunCodeStr"
                |   },
                |   "Auth": {
                |       "uname": "$emailStr",
                |       "passwd": "$passwordStr"
                |   }
                |}
                |""".trimMargin()
        )
        return jsonObject
    }

    inner class RegisterTask internal constructor() : AsyncTask<Void, Void, Boolean>()
    {
        override fun doInBackground( vararg params: Void ): Boolean?
        {
            try
            {
                Thread.sleep( 2000 )
            }
            catch ( e: InterruptedException )
            {
                return false
            }

            return true
        }

        override fun onPostExecute( success: Boolean? )
        {
            mAuthTask = null

            if( !success!! )
            {
                Toast.makeText( this@RegisterActivity, R.string.error_registration, Toast.LENGTH_SHORT  ).show()

                return
            }

            doAsync{
                val client  = OkHttpClient()
                val url     = URL( MyApplication.URL + MyApplication.REGISTER )
                val json    = MediaType.get( "application/json; charset=utf-8" )
                val body    = RequestBody.create( json, createJsonForRegister().toString() )
                val request = Request.Builder()
                    //.addHeader("Authorization", "Bearer $token")
                    .url( url)
                    .post( body )
                    .build()
                val response = client.newCall( request ).execute()

                uiThread{
                    Log.i( "response", response.request().toString() )
                    //Log.i( "response", response.body()!!.string() )
                    Log.i( "response", response.message() )
                    Log.i( "response", response.isSuccessful.toString() )

                    if( !response.isSuccessful )
                    {
                        Toast.makeText( applicationContext, R.string.error_registration, Toast.LENGTH_SHORT  ).show()

                        return@uiThread
                    }
                    else
                    {
                        finish()
                        Toast.makeText( applicationContext, R.string.success, Toast.LENGTH_SHORT  ).show()
                    }
                }
            }
        }

        override fun onCancelled()
        {
            mAuthTask               = null
            progress_bar.visibility = View.GONE
        }
    }
}
