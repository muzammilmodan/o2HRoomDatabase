package com.example.o2hroomdemo

import android.app.Application
import com.example.o2hroomdemo.Database.AppDatabase
import com.example.o2hroomdemo.data.network.MyApi
import com.example.o2hroomdemo.data.network.NetworkConnectionInterceptor
import com.example.o2hroomdemo.data.repository.MainRepository
import com.example.o2hroomdemo.ui.main.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class MyApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        //Common used
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }

        bind() from singleton { MainRepository(instance()) }
        bind() from provider { MainViewModelFactory(instance()) }

    }


    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MainRepository(database.taskDao()!!) }

    override fun onCreate() {
        super.onCreate()
    }

}