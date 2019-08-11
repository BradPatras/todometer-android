package io.github.bradpatras.todometer

import android.app.Application

class TodoMeterApplication: Application() {
    lateinit var singletonComponent: SingletonComponent

    override fun onCreate() {
        super.onCreate()

        singletonComponent = DaggerSingletonComponent
            .builder()
            .contextModule(ContextModule(applicationContext))
            .appDatabaseModule(AppDatabaseModule())
            .build()
    }
}