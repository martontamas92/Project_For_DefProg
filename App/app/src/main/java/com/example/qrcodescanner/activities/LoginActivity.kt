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
import com.example.qrcodescanner.models.User

import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

class LoginActivity : AppCompatActivity()
{
    private var mAuthTask               : UserLoginTask? = null
    private lateinit var emailStr       : String
    private lateinit var passwordStr    : String

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_login )

        loadToolbar()
        // Set up the login form.
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
        if ( mAuthTask != null )
        {
            return
        }

        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        emailStr = email.text.toString()
        passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr))
        {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if ( cancel )
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        }
        else
        {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            progress_bar.visibility = View.VISIBLE
            mAuthTask               = UserLoginTask( emailStr, passwordStr )

            mAuthTask!!.execute( null as Void? )
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }

    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String) :
        AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                return false
            }

            return true
        }

        override fun onPostExecute( success: Boolean? )
        {
            mAuthTask = null

            if ( success!! )
            {
                doAsync{
                    val client          = OkHttpClient()
                    val url             = URL( MyApplication.URL + MyApplication.LOGIN )
                    val fromBodyBuilder = FormBody.Builder()

                    fromBodyBuilder.add( "uname", emailStr )
                    fromBodyBuilder.add( "passwd", passwordStr )
                    fromBodyBuilder.add( "status", "student" )

                    val request = Request.Builder()
                        //.addHeader("Authorization", "Bearer $token")
                        .url( url)
                        .post( fromBodyBuilder.build() )
                        .build()

                    val response = client.newCall( request ).execute()

                    uiThread{
                        Log.i( "response", response.isSuccessful.toString() )

                        if( !response.isSuccessful )
                        {
                            Toast.makeText( applicationContext, R.string.error_registration, Toast.LENGTH_SHORT  ).show()

                            return@uiThread
                        }
                        else
                        {
                            val jsonData = response.body()!!.string()
                            Log.i( "response", jsonData )
                            Log.i( "response", response.message() )

                            MyApplication.instance.user         = User( jsonData )
                            MyApplication.instance.isLoggedIn   = true

                            Log.i( "response id",  MyApplication.instance.user.id.toString() )
                            finish()
                        }
                    }
                }
            }
            else
            {
                Toast.makeText( this@LoginActivity, R.string.error_registration, Toast.LENGTH_SHORT  ).show()
            }

            progress_bar.visibility = View.GONE
        }

        override fun onCancelled()
        {
            mAuthTask               = null
            progress_bar.visibility = View.GONE
        }
    }
}
