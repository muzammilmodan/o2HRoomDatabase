package com.example.o2hroomdemo.utils

import android.content.Context

object SessionManager {
    private val PREFS_NAME = "App Preference"

    private val PARAM_USERID = "user_id"
    private val PARAM_EMAIL = "email"
    private val PARAM_MOBILE_NUMBER = "mobile_number"
    private val KEY_SHARED_ISLOGGEDIN = "logIn"
    private val PARAM_USER_NAME = "user_name"
    private val PARAM_PROFILE="image"


    //.........................
    fun setIsUserLoggedin(context: Context, isSelected: Boolean) {
        try {
            val preferences = context.getSharedPreferences(com.example.o2hroomdemo.utils.SessionManager.PREFS_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean(com.example.o2hroomdemo.utils.SessionManager.KEY_SHARED_ISLOGGEDIN, isSelected)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getIsUserLoggedin(context: Context): Boolean {
        val preferences = context
            .getSharedPreferences(com.example.o2hroomdemo.utils.SessionManager.PREFS_NAME, Context.MODE_PRIVATE)
        return preferences.getBoolean(com.example.o2hroomdemo.utils.SessionManager.KEY_SHARED_ISLOGGEDIN, false)
    }



    fun setUserName(context: Context, `val`: String) {
        try {
            val preferences = context.getSharedPreferences(com.example.o2hroomdemo.utils.SessionManager.PREFS_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(com.example.o2hroomdemo.utils.SessionManager.PARAM_USER_NAME, `val`)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getUserName(context: Context): String {
        val preferences = context
            .getSharedPreferences(com.example.o2hroomdemo.utils.SessionManager.PREFS_NAME, Context.MODE_PRIVATE)
        return preferences.getString(com.example.o2hroomdemo.utils.SessionManager.PARAM_USER_NAME, "")!!
    }


    fun clearAppSession(context: Context) {
        try {
            val preferences =
                context.applicationContext.getSharedPreferences(com.example.o2hroomdemo.utils.SessionManager.PREFS_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

    }


}
