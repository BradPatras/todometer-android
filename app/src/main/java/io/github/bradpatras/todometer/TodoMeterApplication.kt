package io.github.bradpatras.todometer

import android.app.Application

class TodoMeterApplication: Application() {
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