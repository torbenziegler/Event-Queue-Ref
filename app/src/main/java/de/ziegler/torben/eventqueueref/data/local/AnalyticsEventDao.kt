package de.ziegler.torben.eventqueueref.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalyticsEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: AnalyticsEventEntity)

    @Query("SELECT * FROM analytics_events")
    fun getAllEvents(): Flow<List<AnalyticsEventEntity>>

    @Query("DELETE FROM analytics_events WHERE id IN (:ids)")
    suspend fun deleteEvents(ids: List<Long>)
}
