package data.repository

import data.database.TodoDao
import data.model.Todo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

class TodoListRepository(
    private val client: HttpClient,
    private val todoDao: TodoDao
) {
    suspend fun fetchAndUpdateTodos() {
        try {
            val fetchedTodos: List<Todo> = client.get("https://jsonplaceholder.typicode.com/todos").body()
            todoDao.insertAll(fetchedTodos)  // Upsert
        } catch (e: Exception) {
            // Handle the error, maybe log or show an error state
        }
    }

    // Observe the list of todos from the DB
    fun getTodosFlow(): Flow<List<Todo>> {
        return todoDao.getAllTodos()
    }
}