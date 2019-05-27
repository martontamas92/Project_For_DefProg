package com.example.qrcodescanner

import android.app.Application
import com.example.qrcodescanner.models.User

class MyApplication : Application()
{
    var user: User = User()

    companion object
    {
        val URL             = "http://192.168.0.185:8080/WS/home/"
        val REGISTER        = "student/registrate"
        val LOGIN           = "authentication/login"
        val SUBJECTSGETTER  = "subject/get"
        val MYSUBJECTS      = "subject/my"
    }
}