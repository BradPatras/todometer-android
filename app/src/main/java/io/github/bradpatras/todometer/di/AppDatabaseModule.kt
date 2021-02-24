package io.github.bradpatras.todometer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.bradpatras.todometer.core.data.TaskRepository
import io.github.bradpatras.todometer.data.AppDatabase
import io.github.bradpatras.todometer.data.RoomTaskDataSource
import io.github.bradpatras.todometer.data.TaskDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
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