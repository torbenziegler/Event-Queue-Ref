package de.ziegler.torben.eventqueueref.repository

import SendEventsWorker
import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import de.ziegler.torben.eventqueueref.constants.AppConstants.REPEAT_INTERVAL
import de.ziegler.torben.eventqueueref.constants.AppConstants.WORK_NAME

class AnalyticsRepository(context: Context) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleEventSending() {
        val workRequest =
            PeriodicWorkRequestBuilder<SendEventsWorker>(
                REPEAT_INTERVAL,
                java.util.concurrent.TimeUnit.MINUTES
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun triggerImmediateWork() {
        val workRequest = OneTimeWorkRequestBuilder<SendEventsWorker>().build()
        workManager.enqueue(workRequest)
    }
}
