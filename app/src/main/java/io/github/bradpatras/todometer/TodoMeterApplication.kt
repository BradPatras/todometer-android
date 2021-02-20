package io.github.bradpatras.todometer

import android.app.Application
import io.github.bradpatras.todometer.framework.di.AppDatabaseModule
import io.github.bradpatras.todometer.framework.di.ApplicationComponent
import io.github.bradpatras.todometer.framework.di.ContextModule
import io.github.bradpatras.todometer.framework.di.DaggerApplicationComponent

class TodoMeterApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(applicationContext))
            .appDatabaseModule(AppDatabaseModule())
            .build()
    }

    companion object {
        lateinit var instance: TodoMeterApplication
    }
}