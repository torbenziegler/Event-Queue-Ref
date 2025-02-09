import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import de.ziegler.torben.eventqueueref.data.local.AnalyticsDatabase
import de.ziegler.torben.eventqueueref.data.local.AnalyticsEventEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.delay

class SendEventsWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val dao = AnalyticsDatabase.getDatabase(applicationContext).analyticsEventDao()
        val events = dao.getAllEvents().first()

        if (events.isNotEmpty()) {
            val success = sendToServer(events)
            if (success) {
                dao.deleteEvents(events.map { it.id })
            } else {
                Log.e("SendEventsWorker", "Failed to send events")
                return Result.retry()
            }
        } else {
            Log.d("SendEventsWorker", "No events to send")
        }

        return Result.success()
    }

    private suspend fun sendToServer(events: List<AnalyticsEventEntity>): Boolean {
        delay(2000) // Simulate network delay
        Log.d("SendEventsWorker", "Sent ${events.size} events to server")
        return true
    }
}
