package io.github.bradpatras.todometer

import android.content.Context
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, AppDatabaseModule::class])
interface SingletonComponent {
    @Named("applicationContext")
    fun applicationContext(): Context

    fun database(): AppDatabase
}