package de.ziegler.torben.eventqueueref.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson
import de.ziegler.torben.eventqueueref.data.local.AnalyticsDatabase
import de.ziegler.torben.eventqueueref.domain.model.AnalyticsEvent
import de.ziegler.torben.eventqueueref.data.local.AnalyticsEventEntity
import de.ziegler.torben.eventqueueref.repository.AnalyticsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EventQueueViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AnalyticsDatabase.getDatabase(application).analyticsEventDao()
    private val repository = AnalyticsRepository(application)

    init {
        repository.scheduleEventSending()
        // repository.triggerImmediateWork()
    }

    val eventsFlow: StateFlow<List<AnalyticsEvent>> =
        dao.getAllEvents()
            .map { it.map { entity -> entity.toAnalyticsEvent() } }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addEvent(event: AnalyticsEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(event.toEntity())
        }
    }

    private fun AnalyticsEvent.toEntity(): AnalyticsEventEntity {
        return AnalyticsEventEntity(
            eventName = this.eventName,
            timestamp = this.timestamp,
            propertiesJson = Gson().toJson(this.properties)
        )
    }
}