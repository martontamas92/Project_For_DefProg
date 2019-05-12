package com.example.qrcodescanner.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import com.example.qrcodescanner.R
import com.google.zxing.integration.android.IntentIntegrator


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_scan.setOnClickListener {
            val scanner = IntentIntegrator(this)

            scanner.setBeepEnabled(false)
            scanner.initiateScan()
        }

        button_connection.setOnClickListener {
            val intent = Intent( this, Connection::class.java )

            startActivity( intent )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if ( result != null )
        {
            if (result.contents == null)
            {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            }
            else
            {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                link.text = result.contents
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
