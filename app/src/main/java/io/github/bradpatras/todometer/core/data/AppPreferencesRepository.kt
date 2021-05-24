package io.github.bradpatras.todometer.core.data

class AppPreferencesRepository(private val dataSource: AppPreferencesDataSource) {
    fun getAppPreferencesFlow() = dataSource.getPreferencesFlow()
}