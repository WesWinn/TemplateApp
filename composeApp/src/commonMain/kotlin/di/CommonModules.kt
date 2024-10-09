package di

import data.database.AppDatabase
import data.database.getRoomDatabase
import data.repository.TodoListRepository
import data.repository.DetailsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import viewmodel.TodoListViewModel
import viewmodel.DetailsViewModel


val appModule = module {
    factory { TodoListRepository(get(), get()) } // Provide Repository with HttpClient and TodoDao
    factory { DetailsRepository(get(), get()) } // Provide Repository with HttpClient and TodoDao
    viewModel { TodoListViewModel(get()) } // Provide the ViewModel with the Repository
    viewModel { (todoId: Int) -> DetailsViewModel(get(), todoId) }  // Using parameters to provide todoId
}

val networkModule = module {
    // Provide the HttpClient configured with ContentNegotiation for JSON serialization
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true // APIs are open-close, don't fail on unknown keys
                })
            }
        }
    }
}

val databaseModule = module {
    single { getRoomDatabase(get()) } // Use platform-specific builder from getDatabaseBuilder()
    single { get<AppDatabase>().todoDao() }
}

val commonModules = listOf(appModule, networkModule, databaseModule)