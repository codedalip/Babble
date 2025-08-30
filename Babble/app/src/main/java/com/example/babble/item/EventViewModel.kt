package com.example.babble.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.babble.data.Event
import com.example.babble.data.EventRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository): ViewModel() {
    val allEvents: StateFlow<List<Event>> = repository.allEvents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
    fun getEventById(id: Int) = repository.getEventById(id)

    fun addEvent(event: Event){
        viewModelScope.launch {
            repository.insert(event)
        }
    }
    fun updateEvent(event: Event){
        viewModelScope.launch {
            repository.update(event)
        }
    }
    fun deleteEvent(event: Event){
        viewModelScope.launch {
            repository.delete(event)
        }
    }
}

class EventViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return EventViewModel(repository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}