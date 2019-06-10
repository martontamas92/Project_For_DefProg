package com.example.qrcodescanner

import android.content.Context
import android.content.SharedPreferences

class MyPreference( val context: Context )
{
    private val PREFERENCE_NAME         = "sharedPreferenceForLogin"
    private val PREFERENCE_EMAIL        = "email"
    private val PREFERENCE_PASSWORD     = "password"
    private val PREFERENCE_CHECKBOX     = "checkbox"
    val preference: SharedPreferences   = context.getSharedPreferences( PREFERENCE_NAME, Context.MODE_PRIVATE )

    fun getEmail(): String
    {
        return preference.getString( PREFERENCE_EMAIL, "" )!!
    }

    fun setEmail( email: String )
    {
        val editor = preference.edit()

        editor.putString( PREFERENCE_EMAIL, email )
        editor.apply()
    }

    fun getPassword(): String
    {
        return preference.getString( PREFERENCE_PASSWORD, "" )!!
    }

    fun setPassword( password: String )
    {
        val editor = preference.edit()

        editor.putString( PREFERENCE_PASSWORD, password )
        editor.apply()
    }

    fun getCheckbox(): Boolean
    {
        return preference.getBoolean( PREFERENCE_CHECKBOX, false )!!
    }

    fun setCheckBox( checkBox: Boolean )
    {
        val editor = preference.edit()

        editor.putBoolean( PREFERENCE_CHECKBOX, checkBox )
        editor.apply()
    }

    fun clearSharedPreference()
    {
        val editor = preference.edit()

        editor.clear()
        editor.apply()
    }
}