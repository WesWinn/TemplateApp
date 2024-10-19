package kmp.template.app

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import data.database.AppDatabase

fun getDatabaseBuilder(app: Application): RoomDatabase.Builder<AppDatabase> {
    val dbFile = app.getDatabasePath("template.db")
    return Room.databaseBuilder<AppDatabase>(
        context = app,
        name = dbFile.absolutePath
    )
}