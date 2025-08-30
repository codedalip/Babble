package com.example.babble.data

import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {
    val allEvents: Flow<List<Event>> = eventDao.getAllEvents()

    fun getEventById(id: Int): Flow<Event>{
        return eventDao.getEventById(id)
    }

    suspend fun insert(event: Event){
        eventDao.insertEvent(event)
    }
    suspend fun update(event: Event){
        eventDao.updateEvent(event)
    }
    suspend fun delete(event: Event){
        eventDao.deleteEvent(event)
    }
}