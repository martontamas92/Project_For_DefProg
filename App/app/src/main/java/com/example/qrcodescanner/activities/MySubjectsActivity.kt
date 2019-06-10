package com.example.qrcodescanner.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.qrcodescanner.MyActivity
import com.example.qrcodescanner.MyApplication
import com.example.qrcodescanner.R
import com.example.qrcodescanner.adapters.MySubjectAdapter
import com.example.qrcodescanner.models.Message
import com.example.qrcodescanner.models.MySubject
import kotlinx.android.synthetic.main.activity_my_subjects.*
import kotlinx.android.synthetic.main.activity_my_subjects.progress_bar
import kotlinx.android.synthetic.main.activity_my_subjects.swipe_container
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.net.URL

class MySubjectsActivity : MyActivity()
{
    private lateinit var recyclerView           : RecyclerView
    private lateinit var recyclerViewAdapter    : MySubjectAdapter
    var mySubjectList                           = ArrayList<MySubject>()

    override fun onCreate( savedInstanceState: Bundle? )
    {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_my_subjects )

        loadToolbar()
        loadMySubjects()
        refreshList()
    }

    private fun loadInputsAndVariables()
    {
        recyclerView                = findViewById(R.id.recyclerView)
        recyclerView.layoutManager  = LinearLayoutManager( this, LinearLayout.VERTICAL, false )
        recyclerViewAdapter         = MySubjectAdapter( mySubjectList )
        recyclerView.adapter        = recyclerViewAdapter
    }

    private fun loadToolbar()
    {
        val toolbar: Toolbar = findViewById( R.id.toolbar_subject )

        setSupportActionBar( toolbar )
    }

    private fun loadMySubjects()
    {
        progress_bar.visibility = View.VISIBLE

        mySubjectsAsync()
    }

    private fun refreshList()
    {
        swipe_container.setOnRefreshListener {
            mySubjectList.clear()
            loadMySubjects()

            swipe_container.isRefreshing = false
        }
    }

    private fun mySubjectsAsync()
    {
        val client          = OkHttpClient()
        val url             = URL(
            MyApplication.URL +
                    MyApplication.MYSUBJECTSGETTER +
                    "?id=" + MyApplication.instance.user.id
        )

        val token   = MyApplication.instance.bearerToken
        val request = Request.Builder()
            .addHeader("Authorization", "Bearer  $token")
            .url( url )
            .build()

        client.newCall( request ).enqueue( object : Callback
        {
            override fun onFailure( call: Call, e: IOException ){}

            override fun onResponse( call: Call, response: Response )
            {
                val jsonData    = response.body()?.string()

                Log.i( "response", jsonData )

                if ( !response.isSuccessful )
                {
                    val message = Message( jsonData!! )

                    runOnUiThread {
                        if ( message.message == "Lejárt az idő" )
                        {
                            Toast.makeText( applicationContext, message.message, Toast.LENGTH_SHORT  ).show()
                        }

                        Toast.makeText( applicationContext, message.message, Toast.LENGTH_SHORT  ).show()

                        progress_bar.visibility = View.GONE
                    }

                    return
                }

                Log.i( "response id", MyApplication.instance.user.id.toString() )

                val mySubjectsArray = JSONArray( jsonData )

                runOnUiThread {
                    no_my_subjects.visibility  = View.GONE

                    if ( mySubjectsArray.length() == 0 )
                    {
                        no_my_subjects.visibility = View.VISIBLE
                    }
                }
                for ( i in 0..( mySubjectsArray.length() -1 ) )
                {
                    val item = mySubjectsArray.getJSONObject( i )

                    mySubjectList.add( MySubject( item ) )

                    Log.i( "response", item.toString() )
                }

                runOnUiThread {
                    loadInputsAndVariables()

                    progress_bar.visibility = View.GONE
                }
            }
        })
    }
}
