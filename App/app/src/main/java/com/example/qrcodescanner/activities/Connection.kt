package com.example.qrcodescanner.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.qrcodescanner.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class Connection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        doAsync{
            val result = URL("http://demo2426395.mockable.io/").readText()
            uiThread{
                Log.i( "Request", result )
            }
        }
    }
}
