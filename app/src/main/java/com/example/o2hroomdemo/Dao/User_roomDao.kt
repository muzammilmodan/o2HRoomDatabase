package com.example.o2hroomdemo.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.o2hroomdemo.Model.UserDetails_RoomTable
import org.jetbrains.annotations.NotNull


@Dao
interface User_roomDao {
    @Query("SELECT * FROM userdetails")
    fun getAllData(): List<UserDetails_RoomTable>

    @NotNull
    @Query("INSERT INTO userdetails (profile_id,profile) VALUES (:profile_id,:profile)")
    fun insert(profile_id: String, profile: String)

    @Delete
    fun delete(task: UserDetails_RoomTable?)

    @Update
    fun update(task: UserDetails_RoomTable?)
}