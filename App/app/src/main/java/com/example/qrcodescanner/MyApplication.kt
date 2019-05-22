package com.example.qrcodescanner

import android.app.Application

class MyApplication : Application()
{
    companion object
    {
        val url         = "http://192.168.0.185:8080/WS/home/"
        val urlRegister = "student/registrate"
    }

    override fun onCreate()
    {
        super.onCreate()
    }
}