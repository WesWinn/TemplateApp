package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import viewmodel.DetailsViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DetailsScreen(todoId: Int, onBack: () -> Unit) {
    val viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(todoId) })
    val todo by viewModel.todo.collectAsState()

    ScaffoldWithTopBar(
        title = "Todo Details",
        showBackButton = true,
        onBackClick = onBack
    ) { padding ->
        todo?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // Title Section
                Text(
                    text = it.title,
                    style = MaterialTheme.typography.h4.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                    color = MaterialTheme.colors.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Status Section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Icon(
                        imageVector = if (it.completed) Icons.Default.CheckCircle else Icons.Default.Clear,
                        contentDescription = "Status",
                        tint = if (it.completed) MaterialTheme.colors.primary else Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (it.completed) "Completed" else "Incomplete",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                }

                Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f), thickness = 1.dp)

                Spacer(modifier = Modifier.height(12.dp))

                // Description Section
                Text(
                    text = "Description:",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "This is a detailed view of the selected todo item. You can expand this section to show more information as needed.",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            }
        } ?: run {
            // Show a loading state while the todo is being fetched
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
