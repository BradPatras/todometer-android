package io.github.bradpatras.todometer

import android.content.Context
import dagger.Component
import io.github.bradpatras.todometer.data.AppDatabase
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, AppDatabaseModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)

    @Named("applicationContext")
    fun applicationContext(): Context

    fun database(): AppDatabase
}