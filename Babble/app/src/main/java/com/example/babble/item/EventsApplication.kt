package com.example.babble.item

import android.app.Application
import com.example.babble.data.AppDatabase
import com.example.babble.data.EventRepository

class EventsApplication: Application() {
    val database by lazy {AppDatabase.getDatabase(this)}
    val repository by lazy {EventRepository(database.eventDao())}
}