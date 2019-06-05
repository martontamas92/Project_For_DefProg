package com.example.qrcodescanner.activities

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.qrcodescanner.MyApplication
import com.example.qrcodescanner.R
import com.example.qrcodescanner.adapters.MySubjectAdapter
import com.example.qrcodescanner.models.MySubject
import kotlinx.android.synthetic.main.activity_my_subjects.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.net.URL

class MySubjectsActivity : AppCompatActivity()
{

    private lateinit var recyclerView           : RecyclerView
    private lateinit var recyclerViewAdapter    : MySubjectAdapter
    var mySubjectList                           = ArrayList<MySubject>()
    private var mySubjectsGetterTask            : MySubjectsGetterTask? = null

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
        mySubjectsGetterTask      = MySubjectsGetterTask()

        mySubjectsGetterTask!!.execute( null as Void? )
    }

    private fun refreshList()
    {
        swipe_container.setOnRefreshListener {
            mySubjectList.clear()
            loadMySubjects()

            swipe_container.isRefreshing = false
        }
    }

    inner class MySubjectsGetterTask internal constructor() : AsyncTask<Void, Void, Boolean>()
    {
        override fun doInBackground(vararg params: Void): Boolean?
        {

            try
            {

            } catch ( e: InterruptedException )
            {
                return false
            }

            return true
        }

        override fun onPostExecute( success: Boolean? )
        {
            mySubjectsGetterTask = null

            if( !success!! )
            {
                Toast.makeText( this@MySubjectsActivity, R.string.error_registration, Toast.LENGTH_SHORT  ).show()

                progress_bar.visibility = View.GONE

                return
            }

            doAsync{
                val client          = OkHttpClient()
                val url             = URL(
                    MyApplication.URL +
                            MyApplication.MYSUBJECTSGETTER +
                            "?id=" + MyApplication.instance.user.id
                )

                Log.i( "response", MyApplication.URL +
                        MyApplication.MYSUBJECTSGETTER +
                        "?id=" +
                        MyApplication.instance.user.id )

                val request = Request.Builder()
                    //.addHeader("Authorization", "Bearer $token")
                    .url( url)
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
                        Log.i( "response id", MyApplication.instance.user.id.toString() )

                        val jsonArray = JSONArray( jsonData )

                        for ( i in 0..( jsonArray.length() -1 ) )
                        {
                            var item = jsonArray.getJSONObject( i )

                            mySubjectList.add( MySubject( item ) )

                            Log.i( "response", item.toString() )
                        }

                        loadInputsAndVariables()
                    }
                }
            }
        }

        override fun onCancelled()
        {
            mySubjectsGetterTask      = null
            progress_bar.visibility = View.GONE
        }
    }
}
