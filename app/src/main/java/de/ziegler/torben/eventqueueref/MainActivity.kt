package de.ziegler.torben.eventqueueref

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.ziegler.torben.eventqueueref.domain.model.AnalyticsEvent
import de.ziegler.torben.eventqueueref.ui.viewmodel.EventQueueViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(viewModel = EventQueueViewModel(application))
        }
    }
}

@Composable
fun MyApp(viewModel: EventQueueViewModel) {
    val events by viewModel.eventsFlow.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            val event = AnalyticsEvent("button_clicked", properties = mapOf("screen" to "home"))
            viewModel.addEvent(event)
        }) {
            Text("Add Event")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Queued Events:", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(events) { event ->
                Text("${event.eventName} at ${event.timestamp}")
            }
        }
    }
}
