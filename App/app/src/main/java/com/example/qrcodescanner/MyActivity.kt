package com.example.qrcodescanner

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.qrcodescanner.activities.LoginActivity
import com.example.qrcodescanner.activities.RegisterActivity
import com.example.qrcodescanner.models.User
import kotlinx.android.synthetic.main.nav_header_main.*


open class MyActivity : AppCompatActivity()
{
    lateinit var buttonLogin                : MenuItem
    lateinit var buttonRegister             : MenuItem
    lateinit var buttonLogout               : MenuItem
    lateinit var buttonScan                 : MenuItem
    lateinit var buttonScanMain             : Button
    lateinit var textMainScreen             : TextView
    lateinit var buttonMySubjects           : MenuItem
    lateinit var buttonSubjects             : MenuItem

    protected fun setMenuItemVisibility()
    {
        if( MyApplication.instance.isLoggedIn )
        {
            buttonLogin.isVisible       = false
            buttonRegister.isVisible    = false
            buttonScanMain.visibility   = View.VISIBLE
            textMainScreen.visibility   = View.GONE
            buttonLogout.isVisible      = true
            buttonSubjects.isVisible    = true
            buttonScan.isVisible        = true
            buttonMySubjects.isVisible  = true
        }
        else
        {
            buttonLogin.isVisible       = true
            buttonRegister.isVisible    = true
            buttonScanMain.visibility   = View.GONE
            textMainScreen.visibility   = View.VISIBLE
            buttonLogout.isVisible      = false
            buttonSubjects.isVisible    = false
            buttonScan.isVisible        = false
            buttonMySubjects.isVisible  = false
        }
    }

    protected fun logout()
    {
        MyApplication.instance.isLoggedIn   = false
        MyApplication.instance.bearerToken  = ""
        MyApplication.instance.user         = User()

        setMenuItemVisibility()
    }

    fun startRegisterActivity()
    {
        val intent = Intent( this, RegisterActivity::class.java )

        startActivity( intent )
    }

    fun startLoginActivity()
    {
        val intent = Intent( this, LoginActivity::class.java )

        startActivity( intent )
    }

    fun setUserDetails()
    {
        if( MyApplication.instance.isLoggedIn )
        {
            username.text    = MyApplication.instance.user.getName()
            neptun_code.text = MyApplication.instance.user.neptun
        }
    }
}