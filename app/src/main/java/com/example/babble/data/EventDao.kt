package com.example.babble.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM events_table ORDER BY id DESC")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events_table WHERE id = :id")
    fun getEventById(id: Int): Flow<Event>

}