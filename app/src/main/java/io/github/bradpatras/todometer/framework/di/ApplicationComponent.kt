package io.github.bradpatras.todometer.framework.di

import android.content.Context
import dagger.Component
import io.github.bradpatras.todometer.framework.data.AppDatabase
import io.github.bradpatras.todometer.presentation.tasklist.TaskListActivity
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, AppDatabaseModule::class])
interface ApplicationComponent {
    fun inject(taskListActivity: TaskListActivity)

    @Named("applicationContext")
    fun applicationContext(): Context

    fun database(): AppDatabase
}