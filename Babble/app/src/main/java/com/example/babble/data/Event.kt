package com.example.babble.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events_table")
data class Event (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val location: String,
    val date: String,
    val organizingAuthority: String,
    val photoUri: String? = null
)