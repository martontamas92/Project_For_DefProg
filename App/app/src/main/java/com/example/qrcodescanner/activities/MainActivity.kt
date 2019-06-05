package com.example.qrcodescanner.activities

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.example.qrcodescanner.MyApplication
import com.example.qrcodescanner.R
import com.example.qrcodescanner.models.User
import com.google.zxing.integration.android.IntentIntegrator


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var drawer             : DrawerLayout
    private lateinit var toggle             : ActionBarDrawerToggle
    private lateinit var buttonLogin        : MenuItem
    private lateinit var buttonRegister     : MenuItem
    private lateinit var buttonLogout       : MenuItem
    private lateinit var buttonScan         : MenuItem
    private lateinit var buttonMySubjects   : MenuItem
    private lateinit var buttonSubjects     : MenuItem

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadNavBarAndToolbar()
        loadVariables()
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
        buttonRegister      = menu.findItem( R.id.register )
        buttonLogout        = menu.findItem( R.id.logout )
        buttonMySubjects    = menu.findItem( R.id.my_subject )
        buttonScan          = menu.findItem( R.id.qr_code )
        buttonSubjects      = menu.findItem( R.id.subject )
    }

    override fun onPostCreate(savedInstanceState: Bundle?)
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

        return super.onOptionsItemSelected(item)
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
                MyApplication.instance.isLoggedIn   = true
                MyApplication.instance.user         = User()

                setMenuItemVisibility()
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

    private fun startRegisterActivity()
    {
        val intent = Intent( this, RegisterActivity::class.java )

        startActivity( intent )
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
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onResume()
    {
        super.onResume()
        setMenuItemVisibility()
    }

    private fun setMenuItemVisibility()
    {
        if( MyApplication.instance.isLoggedIn )
        {
            buttonLogin.isVisible       = false
            buttonRegister.isVisible    = false
            buttonLogout.isVisible      = true
            buttonSubjects.isVisible    = true
            buttonScan.isVisible        = true
            buttonMySubjects.isVisible  = true
        }
        else
        {
            buttonLogin.isVisible       = true
            buttonRegister.isVisible    = true
            buttonLogout.isVisible      = false
            buttonSubjects.isVisible    = false
            buttonScan.isVisible        = false
            buttonMySubjects.isVisible  = false
        }
    }
}
