package com.example.o2hroomdemo.ui.main

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.o2hroomdemo.Model.UserDetails_RoomTable
import com.example.o2hroomdemo.data.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(var repository: MainRepository) : ViewModel() {
    // private var repository: LoginRepository? = null
    private var getAllData: LiveData<List<UserDetails_RoomTable?>?>? = null

    var authData: MutableLiveData<UserDetails_RoomTable>? = null
    var apiResponse: UserDetails_RoomTable? = null


     suspend fun insert(
         id: String,
         profile: String
    ) {
        return repository.insert(id,profile)
    }

    suspend fun getTaskListData(): ArrayList<UserDetails_RoomTable> {
        //LogM.e("Home repo lang size checking ??" + taskListDao.getAllData().size)
        val customArray: ArrayList<UserDetails_RoomTable> = arrayListOf();
        customArray.addAll(repository.getAllDetails())
        return customArray
    }


}