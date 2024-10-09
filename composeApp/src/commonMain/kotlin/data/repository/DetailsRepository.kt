package data.repository

import data.database.TodoDao
import data.model.Todo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

class DetailsRepository(
    private val client: HttpClient,
    private val todoDao: TodoDao
) {
    fun getTodoById(todoId: Int): Flow<Todo?> {
        return todoDao.getTodoById(todoId)
    }

    suspend fun fetchAndUpdateTodo(todoId: Int) {
        try {
            val fetchedTodo: Todo = client.get("https://jsonplaceholder.typicode.com/todos/$todoId").body()
            todoDao.insert(fetchedTodo) // Upsert
        } catch (e: Exception) {
            // Handle error (e.g., logging or showing an error state)
        }
    }
}
