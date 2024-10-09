package data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [Todo::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        // .addMigrations(MIGRATIONS) // Add this when you need to upgrade db.
        .fallbackToDestructiveMigrationOnDowngrade(true) // TODO: Update for prod
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

/*
// Sample Migration code for future use
val MIGRATIONS: Array<Migration> = arrayOf(
    // Example: Migration from version 1 to version 2
    object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Migration logic here
        }
    }
)
 */