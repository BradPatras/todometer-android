package io.github.bradpatras.todometer

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ContextModule(private val appContext: Context) {
    @Provides
    @Named("applicationContext")
    fun appContext(): Context = appContext
}