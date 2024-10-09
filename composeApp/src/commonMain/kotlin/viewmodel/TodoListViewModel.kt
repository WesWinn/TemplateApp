package viewmodel

import androidx.lifecycle.ViewModel
import data.model.Todo
import data.repository.TodoListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoListRepository) : ViewModel() {

    // Coroutine scope
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // Observes the list of todos from the DB
    val todos: StateFlow<List<Todo>> = repository.getTodosFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    init {
        fetchTodos()  // Fetch the todos when the ViewModel is created
    }

    // Fetch the list of todos from the API and upsert into the DB
    fun fetchTodos() {
        viewModelScope.launch {
            repository.fetchAndUpdateTodos()
        }
    }

    // Clean up the coroutine scope to prevent leaks
    override fun onCleared() {
        viewModelScope.cancel()
    }
}
