package com.example.o2hroomdemo.ui.main

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.o2hroomdemo.Model.UserDetails_RoomTable
import com.example.o2hroomdemo.R
import com.example.o2hroomdemo.Utils.MyProgressDialog
import com.example.o2hroomdemo.databinding.ActivityMainBinding
import com.example.o2hroomdemo.ui.login.LoginActivity
import com.example.o2hroomdemo.utils.SessionManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONException
import java.io.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
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

        bindingMain.tvNameAM.setText(SessionManager.getUserName(mContext))

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
        bindingMain.rcVwGallaryPicAM.setLayoutManager(GridLayoutManager(this, 2))
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

                DownloadImage(mContext).execute(profile)
            }
        } catch (e: JSONException) {
            Log.d(TAG, "addItemsFromJSON: ", e)
        } catch (e: IOException) {
            Log.d(TAG, "addItemsFromJSON: ", e)
        }
    }

    private class DownloadImage(var mContext: Context) :
        AsyncTask<String?, Void?, Bitmap?>() {

        private val TAG = "DownloadImage"
        var imageName: String? = ""

        lateinit var pDialog: ProgressDialog


        protected override fun onPreExecute() {
            pDialog =  ProgressDialog(mContext)
            pDialog.setCancelable(false)
            pDialog.setTitle("Please wait...")
            pDialog.show()
        }

        private fun downloadImageBitmap(sUrl: String): Bitmap? {
            imageName = sUrl.substring(sUrl.lastIndexOf('/') + 1)
            var bitmap: Bitmap? = null
            try {
                val inputStream: InputStream = URL(sUrl).openStream() // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream) // Decode Bitmap
                inputStream.close()
            } catch (e: java.lang.Exception) {
                Log.d(TAG, "Exception 1, Something went wrong!")
                e.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            saveImage(mContext, result!!, imageName)
            if( null != pDialog)
                pDialog.dismiss();
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            return downloadImageBitmap(params[0]!!)
        }

        fun saveImage(
            context: Context,
            b: Bitmap,
            fileImageName: String?
        ) {
            var dirName: String = "/ohRoomData"
            // val timeStamp: String = SimpleDateFormat("ddMMyyyy_HHmmss").format(Date())
            //val fileName = "fav$timeStamp.JPG"


            val direct = File(Environment.getExternalStorageDirectory().toString() + dirName)

            if (!direct.exists()) {
                val wallpaperDirectory =
                    File(Environment.getExternalStorageDirectory().toString() + dirName)
                wallpaperDirectory.mkdirs()
            }

            val file = File(File(Environment.getExternalStorageDirectory().toString() + dirName), fileImageName)
            if (file.exists()) {
                file.delete()
            }


            val foStream: FileOutputStream
            try {
                foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE)
                b.compress(Bitmap.CompressFormat.PNG, 100, foStream)
                foStream.close()
            } catch (e: java.lang.Exception) {
                Log.d("saveImage", "Exception 2, Something went wrong!")
                e.printStackTrace()
            }

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
                    SessionManager.clearAppSession(mContext)
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