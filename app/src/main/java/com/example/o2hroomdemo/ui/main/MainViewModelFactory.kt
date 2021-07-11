package com.example.o2hroomdemo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.o2hroomdemo.data.repository.MainRepository

class MainViewModelFactory(private val loginRepository : MainRepository)
    : ViewModelProvider.NewInstanceFactory() {

    //Todo: Using InstanceFactory as per using ViewModel Crete
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(loginRepository) as T
        }
        throw IllegalArgumentException("MainViewModelFactory exception")
    }

}