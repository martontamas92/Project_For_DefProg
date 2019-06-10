package com.example.qrcodescanner.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.example.qrcodescanner.MyActivity
import com.example.qrcodescanner.MyApplication
import com.example.qrcodescanner.R
import com.example.qrcodescanner.models.Message
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.nav_header_main.*
import okhttp3.*
import java.io.IOException
import java.net.URL


class MainActivity : MyActivity(), NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var drawer : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate( savedInstanceState: Bundle? )
    {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        loadNavBarAndToolbar()
        loadVariables()
        setOnClickListeners()
    }

    private fun loadNavBarAndToolbar()
    {
        val toolbar: Toolbar                = findViewById( R.id.toolbar_main )
        val navigationView: NavigationView  = findViewById( R.id.nav_view )

        navigationView.setNavigationItemSelectedListener( this )
        setSupportActionBar( toolbar )

        drawer = findViewById( R.id.drawer_layout )
        toggle = ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close )

        drawer.addDrawerListener( toggle )
        supportActionBar?.setDisplayHomeAsUpEnabled( true )
        supportActionBar?.setHomeButtonEnabled( true )
    }

    private fun loadVariables()
    {
        val navigationView  = findViewById<NavigationView>( R.id.nav_view )
        val menu            = navigationView.menu
        buttonLogin         = menu.findItem( R.id.login )
        buttonScanMain      = findViewById( R.id.scan )
        buttonRegister      = menu.findItem( R.id.register )
        buttonLogout        = menu.findItem( R.id.logout )
        buttonMySubjects    = menu.findItem( R.id.my_subject )
        buttonScan          = menu.findItem( R.id.qr_code )
        buttonSubjects      = menu.findItem( R.id.subject )
        textMainScreen      = findViewById( R.id.text_main )
    }

    private fun setOnClickListeners()
    {
        buttonScanMain.setOnClickListener{
            startScan()
        }
    }

    override fun onPostCreate( savedInstanceState: Bundle? )
    {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged( newConfig: Configuration? )
    {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected( item: MenuItem? ): Boolean
    {
        if ( toggle.onOptionsItemSelected( item ) )
        {
            return true
        }

        return super.onOptionsItemSelected( item )
    }

    override fun onNavigationItemSelected( item: MenuItem ): Boolean
    {
        when ( item.itemId )
        {
            R.id.qr_code ->
            {
               startScan()
            }
            R.id.subject ->
            {
                startSubjectActivity()
            }
            R.id.my_subject ->
            {
                startMySubjectsActivity()
            }
            R.id.register ->
            {
                startRegisterActivity()
            }
            R.id.login ->
            {
                startLoginActivity()
            }
            R.id.logout ->
            {
                logout()
            }
        }

        drawer.closeDrawer( GravityCompat.START )

        return true
    }

    override fun onBackPressed()
    {
        if ( drawer.isDrawerOpen( GravityCompat.START ) )
        {
            drawer.closeDrawer( GravityCompat.START )
        }
        else
        {
            super.onBackPressed()
        }
    }

    private fun startScan()
    {
        val scanner = IntentIntegrator( this )

        scanner.setBeepEnabled( false )
        scanner.initiateScan()
    }

    private fun startSubjectActivity()
    {
        val intent = Intent( this, SubjectActivity::class.java )

        startActivity( intent )
    }

    private fun startMySubjectsActivity()
    {
        val intent = Intent( this, MySubjectsActivity::class.java )

        startActivity( intent )
    }

    private fun startLoginActivity()
    {
        val intent = Intent( this, LoginActivity::class.java )

        startActivity( intent )
    }

    override fun onActivityResult( requestCode: Int, resultCode: Int, data: Intent? )
    {
        super.onActivityResult( requestCode, resultCode, data )

        val result  = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val link    = result.contents

        if( result == null )
        {
            super.onActivityResult( requestCode, resultCode, data )

            return
        }

        if ( link == null )
        {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()

            return
        }

        scanLink(link)

        Log.i( "scan_link", link )
    }

    private fun scanLink(link:String )
    {
        val urlString   = link + "&st_id=" + MyApplication.instance.user.id

        if( ! URLUtil.isValidUrl( urlString ) )
        {
            Toast.makeText( applicationContext, R.string.error_in_link, Toast.LENGTH_LONG ).show()

            return
        }

        val client  = OkHttpClient()
        val url     = URL( urlString )
        val token   = MyApplication.instance.bearerToken
        val json    = MediaType.get( "application/json; charset=utf-8" )
        val body    = RequestBody.create( json, "" )
        Log.i( "response", link + "&st_id=" + MyApplication.instance.user.id )

        val request = Request.Builder()
            .addHeader("Authorization", "Bearer $token")
            .url(url)
            .post(body)
            .build()

        client.newCall( request ).enqueue( object: Callback
        {
            override fun onFailure( call: Call, e: IOException )
            {
                Toast.makeText( applicationContext, R.string.error_in_link, Toast.LENGTH_LONG ).show()
            }

            override fun onResponse(call: Call, response: Response)
            {
                val jsonData = response.body()?.string()
                val message  = Message(jsonData!!)
                Log.i( "response", jsonData )

                if ( !response.isSuccessful )
                {
                    runOnUiThread {
                        if ( message.message == "Lejárt az idő" )
                        {
                            Toast.makeText( applicationContext, message.message, Toast.LENGTH_LONG ).show()
                            logout()

                            return@runOnUiThread
                        }

                        Toast.makeText( applicationContext, message.message, Toast.LENGTH_LONG ).show()
                    }

                    return
                }

                runOnUiThread {
                    Toast.makeText( applicationContext, message.message, Toast.LENGTH_LONG ).show()
                }
            }
        })
    }

    override fun onResume()
    {
        super.onResume()
        setMenuItemVisibility()
        setUserDetails()
    }

    private fun setUserDetails()
    {
        if( MyApplication.instance.isLoggedIn )
        {
            username.text    = MyApplication.instance.user.getName()
            neptun_code.text = MyApplication.instance.user.neptun
        }
    }
}
