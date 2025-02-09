package de.ziegler.torben.eventqueueref.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.ziegler.torben.eventqueueref.constants.AppConstants.DATABASE_NAME

@Database(entities = [AnalyticsEventEntity::class], version = 1)
abstract class AnalyticsDatabase : RoomDatabase() {
    abstract fun analyticsEventDao(): AnalyticsEventDao

    companion object {
        @Volatile
        private var INSTANCE: AnalyticsDatabase? = null

        fun getDatabase(context: Context): AnalyticsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnalyticsDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
