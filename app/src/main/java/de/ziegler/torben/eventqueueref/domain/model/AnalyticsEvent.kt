package de.ziegler.torben.eventqueueref.domain.model

data class AnalyticsEvent(
    val eventName: String,
    val timestamp: Long = System.currentTimeMillis(),
    val properties: Map<String, Any> = emptyMap()
)
