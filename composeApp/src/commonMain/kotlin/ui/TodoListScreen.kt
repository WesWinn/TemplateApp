package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.model.Todo
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import viewmodel.TodoListViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel = koinViewModel(),
    onTodoClick: (Int) -> Unit
) {
    ScaffoldWithTopBar(
        title = "Todo List",
        showBackButton = false
    ) { padding ->
        val todos by viewModel.todos.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(todos) { todo ->
                TodoItem(todo = todo, onClick = { onTodoClick(todo.id) })
            }
        }
    }
}


@Composable
fun TodoItem(todo: Todo, onClick: () -> Unit) {
    // A Row to hold the content in a horizontally aligned layout
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)  // Proper list item padding
    ) {
        Column(
            modifier = Modifier
                .weight(1f)  // Title and status should take up available space
        ) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),  // Title with hierarchy
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))  // Smaller spacing between title and status
            Text(
                text = if (todo.completed) "Completed" else "Incomplete",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)  // Lower contrast for secondary text
            )
        }
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Status",
            tint = if (todo.completed) MaterialTheme.colors.primary else Color.Gray,  // Completed status icon
            modifier = Modifier.size(24.dp)
        )
    }
}
