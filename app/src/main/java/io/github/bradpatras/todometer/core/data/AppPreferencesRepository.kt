package io.github.bradpatras.todometer.core.data

import io.github.bradpatras.todometer.core.domain.AppPreferences

class AppPreferencesRepository(private val dataSource: AppPreferencesDataSource) {
    fun getAppPreferencesFlow() = dataSource.getPreferencesFlow()

    suspend fun setAppPreferences(appPreferences: AppPreferences) {
        dataSource.setPreferences(appPreferences)
    }
}