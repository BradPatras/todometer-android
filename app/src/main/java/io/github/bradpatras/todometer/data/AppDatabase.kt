package io.github.bradpatras.todometer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], exportSchema = false, version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private const val NAME = "todometerDatabase"

        private var instance: AppDatabase? = null

        fun create(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): AppDatabase =
            (instance ?: create(context)).also { instance = it }
    }
}