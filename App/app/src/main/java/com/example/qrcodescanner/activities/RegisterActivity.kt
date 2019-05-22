package com.example.qrcodescanner.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import com.example.qrcodescanner.MyApplication
import com.example.qrcodescanner.R

import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

class RegisterActivity : AppCompatActivity(), LoaderCallbacks<Cursor>
{
    lateinit var firstNameStr               : String
    lateinit var middleNameStr              : String
    lateinit var lastNameStr                : String
    lateinit var neptunCodeStr              : String
    lateinit var emailStr                   : String
    lateinit var passwordStr                : String
    private var mAuthTask: RegisterTask?   = null

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

        first_name.error    = null
        middle_name.error   = null
        last_name.error     = null
        neptun_code.error   = null
        email.error         = null
        password.error      = null

        // Store values at the time of the login attempt.
        firstNameStr        = first_name.text.toString()
        middleNameStr       = middle_name.text.toString()
        lastNameStr         = last_name.text.toString()
        neptunCodeStr       = neptun_code.text.toString()
        emailStr            = email.text.toString()
        passwordStr         = password.text.toString()
        var cancel              = false
        var focusView: View?    = null

        // Check for a valid password, if the user entered one.
        if ( !TextUtils.isEmpty( passwordStr ) && !isPasswordValid( passwordStr ) )
        {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid password, if the user entered one.
        if ( !TextUtils.isEmpty( neptunCodeStr ) && !isNeptunCodeValid( neptunCodeStr ) )
        {
            neptun_code.error   = getString(R.string.error_invalid_password)
            focusView           = neptun_code
            cancel              = true
        }

        // Check for a valid email address.
        if ( TextUtils.isEmpty( lastNameStr ) )
        {
            last_name.error = getString(R.string.error_field_required)
            focusView       = last_name
            cancel          = true
        }

        // Check for a valid email address.
        if ( TextUtils.isEmpty( firstNameStr ) )
        {
            first_name.error    = getString( R.string.error_field_required )
            focusView           = first_name
            cancel              = true
        }

        // Check for a valid email address.
        if ( TextUtils.isEmpty( emailStr ) )
        {
            email.error = getString( R.string.error_field_required )
            focusView = email
            cancel = true
        }
        else if ( !isEmailValid( emailStr ) )
        {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if ( cancel )
        {
            focusView?.requestFocus()
        }
        else
        {
            showProgress( true )

            mAuthTask = RegisterTask( emailStr, passwordStr )
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility   = if (show) View.VISIBLE else View.GONE
            login_form.visibility       = if (show) View.GONE else View.VISIBLE
        }
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<Cursor> {
        return CursorLoader(
            this,
            // Retrieve data rows for the device user's 'profile' contact.
            Uri.withAppendedPath(
                ContactsContract.Profile.CONTENT_URI,
                ContactsContract.Contacts.Data.CONTENT_DIRECTORY
            ), ProfileQuery.PROJECTION,

            // Select only email addresses.
            ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(
                ContactsContract.CommonDataKinds.Email
                    .CONTENT_ITEM_TYPE
            ),

            // Show primary email addresses first. Note that there won't be
            // a primary email address if the user hasn't specified one.
            ContactsContract.Contacts.Data.IS_PRIMARY + " DESC"
        )
    }

    override fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor) {
        val emails = ArrayList<String>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS))
            cursor.moveToNext()
        }

        addEmailsToAutoComplete(emails)
    }

    override fun onLoaderReset(cursorLoader: Loader<Cursor>) {

    }

    private fun addEmailsToAutoComplete(emailAddressCollection: List<String>) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        val adapter = ArrayAdapter(
            this@RegisterActivity,
            android.R.layout.simple_dropdown_item_1line, emailAddressCollection
        )

        email.setAdapter(adapter)
    }

    object ProfileQuery {
        val PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.IS_PRIMARY
        )
        val ADDRESS = 0
        val IS_PRIMARY = 1
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    inner class RegisterTask internal constructor(private val mEmail: String, private val mPassword: String) :
        AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground( vararg params: Void ): Boolean?
        {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000)
            } catch (e: InterruptedException)
            {
                return false
            }

            return DUMMY_CREDENTIALS
                .map { it.split(":") }
                .firstOrNull { it[0] == mEmail }
                ?.let {
                    // Account exists, return true if the password matches.
                    it[1] == mPassword
                }
                ?: true
        }

        override fun onPostExecute( success: Boolean? )
        {
            mAuthTask = null

            showProgress( false )

            if ( success!! )
            {
                doAsync{
                    val client  = OkHttpClient()
                    val url     = URL( MyApplication.url + MyApplication.urlRegister )
                    val json    = MediaType.get( "application/json; charset=utf-8" )
                    val body    = RequestBody.create( json, createJsonForRegister().toString() )
                    val request = Request.Builder()
                        //.addHeader("Authorization", "Bearer $token")
                        .url( url)
                        .post(body)
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
            else
            {
                Toast.makeText( this@RegisterActivity, R.string.error_registration, Toast.LENGTH_SHORT  ).show()
            }
        }

        override fun onCancelled()
        {
            mAuthTask = null

            showProgress( false )
        }
    }

    companion object {

        /**
         * A dummy authentication store containing known user names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }
}
