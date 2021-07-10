package com.example.o2hroomdemo.Database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.o2hroomdemo.Dao.User_roomDao
import com.example.o2hroomdemo.Model.UserDetails_RoomTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [UserDetails_RoomTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): User_roomDao?


    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null

        /*fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance as AppDatabase
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "MyUserDetails.db"
                ).fallbackToDestructiveMigration()          // TODO: migration
                    .build()
                INSTANCE = instance
                return instance
            }
        }*/

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE as AppDatabase? ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, "userdetails.db")
                        .createFromAsset("databases/userdetails.db")
                        .addCallback(databaseCallback)
                        .allowMainThreadQueries()
                        .build()
                    /*Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,"userdetails.db",
                        .createFromAsset("databases/userdetails.db")
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()*/
                INSTANCE = instance
                // return instance
                instance
            }
        }


        public val databaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("RoomDatabaseModule", "onCreate")
                CoroutineScope(Dispatchers.IO).launch {
                }
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        //populateDatabase(database.taskDao())
                    }
                }
            }

        }

        suspend fun populateDatabase(wordDao: User_roomDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
           // wordDao.deleteAll()

           /* var word = UserDetails_RoomTable("Hello")
            wordDao.insert(word)
            word = UserDetails_RoomTable("World!")
            wordDao.insert(word)*/
        }
    }
}