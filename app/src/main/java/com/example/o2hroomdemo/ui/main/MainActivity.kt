package com.example.o2hroomdemo.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.o2hroomdemo.Model.UserDetails_RoomTable
import com.example.o2hroomdemo.R
import com.example.o2hroomdemo.databinding.ActivityMainBinding
import com.example.o2hroomdemo.ui.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var mContext: Context

    lateinit var bindingMain: ActivityMainBinding
    private val viewItems: ArrayList<UserDetails_RoomTable> = ArrayList()

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mContext = MainActivity@ this

        bindingMain.tvNameAM.setText(com.example.o2hroomdemo.utils.SessionManager.getUserName(mContext))

        addItemsFromJSON()
        setGalleryData()
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private fun setGalleryData() {
        bindingMain.rcVwGallaryPicAM.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        bindingMain.rcVwGallaryPicAM.setLayoutManager(layoutManager)
        mAdapter = GallaryAdapter(this, viewItems)
        bindingMain.rcVwGallaryPicAM.setAdapter(mAdapter)


    }


    private fun addItemsFromJSON() {
        try {
            val jsonDataString = readJSONDataFromFile()
            val jsonArray = JSONArray(jsonDataString)
            for (i in 0 until jsonArray.length()) {
                val itemObj = jsonArray.getJSONObject(i)
                val id = itemObj.getString("profile_id")
                val profile_id = itemObj.getString("profile_id")
                val profile = itemObj.getString("profile")
                val holidays = UserDetails_RoomTable(id!!.toInt(), profile_id, profile)
                viewItems.add(holidays)
            }
        } catch (e: JSONException) {
            Log.d(TAG, "addItemsFromJSON: ", e)
        } catch (e: IOException) {
            Log.d(TAG, "addItemsFromJSON: ", e)
        }
    }

    @Throws(IOException::class)
    private fun readJSONDataFromFile(): String {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonString: String? = null
            inputStream = resources.openRawResource(R.raw.holidays)
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, "UTF-8")
            )
            while (bufferedReader.readLine().also({ jsonString = it }) != null) {
                builder.append(jsonString)
            }
        } finally {
            if (inputStream != null) {
                inputStream.close()
            }
        }
        return String(builder)
    }

    fun logoutClick(view: View) {
        showCustomeDialog(mContext.resources.getString(R.string.logout_message), 1)
    }

    fun showCustomeDialog(message: String, type: Int) {
        AlertDialog.Builder(mContext)
            .setTitle("Alert")
            .setMessage(message)
            .setPositiveButton("Yes", { dialog, whichButton ->
                try {
                    com.example.o2hroomdemo.utils.SessionManager.clearAppSession(mContext)
                    val mainActivity = Intent(mContext, LoginActivity::class.java)
                    mainActivity.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(mainActivity)
                    finish()
                    dialog.dismiss()
                    Firebase.auth.signOut()


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            })
            .setNegativeButton("No", { dialog, whichButton ->
                dialog.dismiss()
            }).show()
    }
}