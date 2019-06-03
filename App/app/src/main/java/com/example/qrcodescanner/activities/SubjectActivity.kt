package com.example.qrcodescanner.activities

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
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
import com.example.qrcodescanner.MyApplication
import kotlinx.android.synthetic.main.activity_subject.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.net.URL


class SubjectActivity : AppCompatActivity()
{
    private var subjectsGetterTask              : SubjectsGetterTask? = null
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
        subjectsGetterTask      = SubjectsGetterTask()

        subjectsGetterTask!!.execute( null as Void? )
    }

    private fun refreshList()
    {
        swipe_container.setOnRefreshListener {
                subjectList.clear()
                loadSubjects()

                swipe_container.isRefreshing = false
        }
    }

    inner class SubjectsGetterTask internal constructor() : AsyncTask<Void, Void, Boolean>()
    {

        override fun doInBackground(vararg params: Void): Boolean?
        {

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
            subjectsGetterTask = null

            if ( success!! )
            {
                doAsync{
                    val client          = OkHttpClient()
                    val url             = URL(
                            MyApplication.URL +
                                MyApplication.SUBJECTSGETTER +
                                "?id=" + MyApplication.instance.user.id
                    )

                    Log.i( "response", MyApplication.URL +
                            MyApplication.SUBJECTSGETTER +
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

                                subjectList.add( Subject( item ) )

                                Log.i( "response", item.toString() )
                            }

                            loadInputsAndVariables()
                        }
                    }
                }
            }
            else
            {
                Toast.makeText( this@SubjectActivity, R.string.error_registration, Toast.LENGTH_SHORT  ).show()
            }

            progress_bar.visibility = View.GONE
        }

        override fun onCancelled()
        {
            subjectsGetterTask      = null
            progress_bar.visibility = View.GONE
        }
    }
}
