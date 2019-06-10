package com.example.qrcodescanner

import android.app.Application
import com.example.qrcodescanner.models.User

class MyApplication : Application()
{
    lateinit var user   : User
    var isLoggedIn      = false
    var bearerToken     = ""

    override fun onCreate()
    {
        super.onCreate()
        instance = this
    }

    companion object
    {
        val URL                 = "http://192.168.0.185:8080/WS/home/"
        val REGISTER            = "student/registration"
        val LOGIN               = "authentication/login"
        val SUBJECTSGETTER      = "student/subjectList"
        val SUBJECTATTEND       = "student/attend"
        val MYSUBJECTSGETTER    = "student/attendedClasses"
        lateinit var instance   : MyApplication
            private set
    }
}