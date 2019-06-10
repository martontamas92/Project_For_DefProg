package com.example.qrcodescanner.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.LinearLayout
import com.example.qrcodescanner.adapters.SubjectAdapter
import com.example.qrcodescanner.models.Subject
import com.example.qrcodescanner.R
import android.view.View
import android.widget.Toast
import com.example.qrcodescanner.MyActivity
import com.example.qrcodescanner.MyApplication
import kotlinx.android.synthetic.main.activity_subject.*
import org.json.JSONArray
import java.net.URL
import okhttp3.*
import java.io.IOException


class SubjectActivity : MyActivity()
{
    private lateinit var recyclerView           : RecyclerView
    private lateinit var recyclerViewAdapter    : SubjectAdapter
    var subjectList                             = ArrayList<Subject>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_subject )

        loadToolbar()
        loadSubjects()
        refreshList()
    }

    private fun loadInputsAndVariables()
    {
        recyclerView                = findViewById( R.id.recyclerView )
        recyclerView.layoutManager  = LinearLayoutManager( this, LinearLayout.VERTICAL, false )
        recyclerViewAdapter         = SubjectAdapter( subjectList, this )
        recyclerView.adapter        = recyclerViewAdapter
    }

    private fun loadToolbar()
    {
        val toolbar: Toolbar = findViewById( R.id.toolbar_subject )

        setSupportActionBar( toolbar )
    }

    private fun loadSubjects()
    {
        progress_bar.visibility = View.VISIBLE

        subjectsAsync()
    }

    private fun refreshList()
    {
        swipe_container.setOnRefreshListener {
                subjectList.clear()
                loadSubjects()

                swipe_container.isRefreshing = false
        }
    }

    private fun subjectsAsync()
    {
        val client          = OkHttpClient()
        val url             = URL(
            MyApplication.URL +
                    MyApplication.SUBJECTSGETTER +
                    "?id=" + MyApplication.instance.user.id
        )

        val token   = MyApplication.instance.bearerToken
        val request = Request.Builder()
            .addHeader("Authorization", "Bearer  $token")
            .url( url )
            .build()

        client.newCall( request ).enqueue( object : Callback {
            override fun onFailure( call: Call, e: IOException ) {}

            override fun onResponse( call: Call, response: Response )
            {
                val jsonData = response.body()!!.string()

                Log.i( "response", jsonData )

                if ( !response.isSuccessful )
                {
                    runOnUiThread {
                        Toast.makeText( applicationContext, R.string.error_subjects, Toast.LENGTH_SHORT ).show()

                        progress_bar.visibility = View.GONE
                    }

                    return
                }

                Log.i( "response id", MyApplication.instance.user.id.toString() )
                val subjectsArray = JSONArray( jsonData )

                runOnUiThread {
                    no_subjects.visibility  = View.GONE

                    if ( subjectsArray.length() == 0 )
                    {
                        no_subjects.visibility = View.VISIBLE
                    }
                }

                for ( i in 0..( subjectsArray.length() -1 ) )
                {
                    val item = subjectsArray.getJSONObject( i )

                    subjectList.add( Subject( item ) )

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
