package io.github.bradpatras.todometer

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.github.bradpatras.todometer.data.AppDatabase
import io.github.bradpatras.todometer.data.TaskDao
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppDatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@Named("applicationContext") context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }
}