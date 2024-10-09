package viewmodel

import androidx.lifecycle.ViewModel
import data.model.Todo
import data.repository.DetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: DetailsRepository,
    private val todoId: Int
) : ViewModel() {

    // Coroutine scope for the ViewModel
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // Observes the item from the database
    val todo: StateFlow<Todo?> = repository.getTodoById(todoId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    init {
        fetchTodoFromApi()  // Fetch and update from the API when the ViewModel is initialized
    }

    // Fetch the item from the API and update the DB
    private fun fetchTodoFromApi() {
        viewModelScope.launch {
            repository.fetchAndUpdateTodo(todoId)
        }
    }

    // Clean up the coroutine scope to prevent leaks
    override fun onCleared() {
        viewModelScope.cancel()
    }
}
