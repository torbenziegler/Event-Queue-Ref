package de.ziegler.torben.eventqueueref.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import de.ziegler.torben.eventqueueref.constants.AppConstants.ANALYTICS_EVENT_TABLE_NAME
import de.ziegler.torben.eventqueueref.domain.model.AnalyticsEvent

@Entity(tableName = ANALYTICS_EVENT_TABLE_NAME)
data class AnalyticsEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val eventName: String,
    val timestamp: Long,
    val propertiesJson: String
) {
    fun toAnalyticsEvent(): AnalyticsEvent {
        val properties: Map<String, Any> =
            Gson().fromJson(propertiesJson, Map::class.java) as Map<String, Any>
        return AnalyticsEvent(eventName, timestamp, properties)
    }
}
