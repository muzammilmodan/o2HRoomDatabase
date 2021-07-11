package com.example.o2hroomdemo.data.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.o2hroomdemo.Dao.User_roomDao
import com.example.o2hroomdemo.Model.UserDetails_RoomTable
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.Flow

class MainRepository(private val wordDao: User_roomDao) {

    suspend fun insert(
        id: String,
        profile: String
    ) {
        return wordDao.insert(id,profile)
    }

    suspend fun getAllDetails(): ArrayList<UserDetails_RoomTable> {
        val customArray: ArrayList<UserDetails_RoomTable> = arrayListOf();
        customArray.addAll(wordDao.getAllData())
        return customArray
    }


}