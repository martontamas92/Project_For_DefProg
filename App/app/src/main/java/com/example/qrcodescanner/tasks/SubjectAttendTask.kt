package com.example.qrcodescanner.tasks

import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.qrcodescanner.MyApplication
import com.example.qrcodescanner.R
import com.example.qrcodescanner.activities.SubjectActivity
import com.example.qrcodescanner.adapters.SubjectAdapter
import kotlinx.android.synthetic.main.activity_subject.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class SubjectAttendTask internal constructor(
    private val id: Int,
    private val position: Int,
    private val activity: SubjectActivity,
    private val adapter: SubjectAdapter) :
    AsyncTask<Void, Void, Boolean>()
{
    override fun doInBackground( vararg params: Void ): Boolean?
    {
        try
        {
            Thread.sleep(2000)
        }
        catch ( e: InterruptedException )
        {
            return false
        }

        return true
    }

    override fun onPostExecute( success: Boolean? )
    {

        if ( success!! )
        {
            doAsync{
                val client          = OkHttpClient()
                val url             = URL(MyApplication.URL + MyApplication.SUBJECTATTEND )
                val json            = MediaType.get( "application/json; charset=utf-8" )
                val body            = RequestBody.create( json, adapter.createJsonForAttendSubject( id ).toString() )
                val request         = Request.Builder()
                    //.addHeader("Authorization", "Bearer $token")
                    .url( url)
                    .post( body )
                    .build()

                val response = client.newCall( request ).execute()

                uiThread{
                    Log.i( "response", response.isSuccessful.toString() )

                    if( !response.isSuccessful )
                    {
                        Toast.makeText( activity.applicationContext, R.string.error_registration, Toast.LENGTH_SHORT  ).show()

                        return@uiThread
                    }
                    else
                    {
                        adapter.removeItemInList( position )
                        activity.progress_bar.visibility = View.GONE
                    }
                }
            }
        }
        else
        {
            Toast.makeText( activity.applicationContext, R.string.error_registration, Toast.LENGTH_SHORT  ).show()
        }
    }
}