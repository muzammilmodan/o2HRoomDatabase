package com.example.o2hroomdemo.Model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Parcelize
@Entity(tableName = "userdetails")
data class UserDetails_RoomTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @NotNull
    @ColumnInfo(name = "profile_id")
    val profile_id: String,
    @NotNull
    @ColumnInfo(name = "profile")
    val profile: String
) : Parcelable