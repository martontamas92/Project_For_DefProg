package com.example.qrcodescanner

import android.app.Application
import com.example.qrcodescanner.models.User

class MyApplication : Application()
{
    lateinit var user   : User
    var isLoggedIn      = false

    override fun onCreate()
    {
        super.onCreate()
        instance = this
    }

    companion object
    {
        val URL                 = "http://192.168.0.185:8080/WS/home/"
        val REGISTER            = "student/registrate"
        val LOGIN               = "authentication/login"
        val SUBJECTSGETTER      = "subject/student-subjectList"
        val SUBJECTATTEND       = "subject/attend"
        lateinit var instance   : MyApplication
            private set
    }
}