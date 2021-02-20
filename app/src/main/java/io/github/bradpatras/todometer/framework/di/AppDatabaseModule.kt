package io.github.bradpatras.todometer.framework.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.github.bradpatras.core.data.TaskRepository
import io.github.bradpatras.todometer.framework.data.AppDatabase
import io.github.bradpatras.todometer.framework.data.RoomTaskDataSource
import io.github.bradpatras.todometer.framework.data.TaskDao
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppDatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@Named("applicationContext") context: Context): AppDatabase {
        return AppDatabase.create(context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Singleton
    @Provides
    fun provideTaskRepository(roomDataSource: RoomTaskDataSource): TaskRepository {
        return TaskRepository(roomDataSource)
    }
}