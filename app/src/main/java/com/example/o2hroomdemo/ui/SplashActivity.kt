package com.example.o2hroomdemo.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.multidex.MultiDex
import com.example.o2hroomdemo.R
import com.example.o2hroomdemo.ui.login.LoginActivity
import com.example.o2hroomdemo.ui.main.MainActivity
import com.example.o2hroomdemo.utils.SessionManager
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mContext = SplashActivity@ this

        if (checkPermission()) {
            splashTimeOut()
        }
    }

    private fun splashTimeOut() {
        try {
            val timer = Timer()
            timer.schedule(object : TimerTask() {

                override fun run() {
                    if(SessionManager.getIsUserLoggedin(mContext)) {
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }else{
                        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                }
            }, 5000)
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

    private val MY_PERMISSIONS_REQUEST = 152

    private fun checkPermission(): Boolean {
        try {

            val ReadPermission =
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
            val WritePermission =
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            val listPermissionsNeeded = ArrayList<String>()

            if (ReadPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (WritePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    listPermissionsNeeded.toTypedArray(), MY_PERMISSIONS_REQUEST
                )
                return false
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MY_PERMISSIONS_REQUEST) {
            splashTimeOut()
        }
    }

}